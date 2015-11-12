(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [amazonica.aws.ec2 :as ec2]
            [clojure.tools.logging :as log]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-tosca.template :as template]
            [tosca-lens.util :as util]))

(defn data-for-instance
  "Call describe instances and get the data for instance-id."
  [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn tags-for-instance
  "Get the tags for the instance from data-for-instance."
  [instance-id]
  (let [data (data-for-instance instance-id)
        tags (:tags (first (:instances data)))]
    tags))

(defn tosca-tags
  "Build a tosca document with the tags and instance id."
  [instance-id format]
  (let [tags (tags-for-instance instance-id)
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" instance-id)
                  (nodei/add-property "tags" tags))]
    (-> (template/build nodei node)
        (template/publish format))))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [data (util/as-clj-map input)
        instance-id (:instance-id data)
        format (get-in data [:format] "json")
        document (tosca-tags instance-id format)]
    document))
