(ns tosca-lens
  (:gen-class
   :methods [^:static [translate [String] String]])
  (:require [amazonica.aws.ec2 :as ec2]
            [amazonica.aws.sqs :as sqs]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-yaml.core :as yaml]))


(def creds {:access-key "AKIAJ66CWNVZBD5BPCVA"
            :secret-key "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"
            :endpoint   "us-east-1"})


(def instances (ec2/describe-instances creds))  

(defn data-for-instance [instance-id]
  (first (:reservations (ec2/describe-instances creds {:instance-ids [instance-id]}))))

(defn tags-for-instance [instance-id]
  (:tags (first (:instances (data-for-instance instance-id)))))

(defn tosca-tags [instance-id]
  (let [tags (tags-for-instance instance-id)
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" instance-id)
                  (nodei/add-property "tags" tags))]
    (yaml/generate-string  (merge {:tosca_definitions_version "tosca_simple_yaml_1_0"}
                                  nodei
                                  node))))

#_(defn -lambda [input]
  
  )
