(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [amazonica.aws.ec2 :as ec2]
            [amazonica.aws.sqs :as sqs]
            [clojure.tools.logging :as log]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-yaml.core :as yaml]
            [tosca-lens.util :as util]))

(defn data-for-instance [instance-id creds]
  (let [instance-data (ec2/describe-instances creds)]
    (first (:reservations (instance-data {:instance-ids [instance-id]})))))

(defn tags-for-instance [instance-id creds]
  (:tags (first (:instances (data-for-instance instance-id)))))

(defn tosca-tags [instance-id creds]
  (let [tags (tags-for-instance instance-id creds)
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" instance-id)
                  (nodei/add-property "tags" tags))]
    (yaml/generate-string  (merge {:tosca_definitions_version "tosca_simple_yaml_1_0"}
                                  nodei
                                  node))))

(defn -lambda [input]
  (let [data (util/as-clj-map input)
        creds (:creds data)
        instance-id (:instance-id data)]
    (log/info "Getting instance")
    (log/info instance-id)
    (.toString (tosca-tags instance-id creds))))
