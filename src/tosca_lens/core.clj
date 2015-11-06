(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] clojure.lang.PersistentArrayMap]])
  (:require [amazonica.aws.ec2 :as ec2]
            [amazonica.aws.sqs :as sqs]
            [clojure.tools.logging :as log]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-yaml.core :as yaml]
            [clojure.data.json :as json]
            [tosca-lens.util :as util]))

(defn data-for-instance [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn tags-for-instance [instance-id]
  (let [data (data-for-instance instance-id)
        tags (:tags (first (:instances data)))]
    tags))

(defn tosca-tags [instance-id]
  (let [tags (tags-for-instance instance-id)
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" instance-id)
                  (nodei/add-property "tags" tags))]
    (merge {:tosca_definitions_version "tosca_simple_yaml_1_0"}
           nodei
           node)))

(defn -lambda [input]
  (let [data (util/as-clj-map input)
        instance-id (:instance-id data)
        document (tosca-tags instance-id)]
    (log/info (str "getting tags for: " instance-id " with format " format))
    (clojure.walk/stringify-keys document)))
