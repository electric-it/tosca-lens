(ns tosca-lens.tags
  (:require [amazonica.aws.ec2 :as ec2]
            [clojure.tools.logging :as log]
            [amazonica.aws.ec2 :as ec2]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-tosca.template :as template]
            ))


(defn get-instance-data
  "Call describe instances and get the data for instance-id."
  [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn get-tags
  "Get the tags for the instance from data-for-instance."
  [data]
  (:tags (first (:instances data))))

(defn tosca
  "Build a tosca document with the tags and instance id."
  [audit-params]
  (let [data (get-instance-data (:instance-id audit-params))
        tags (get-tags data)
        format (get-in audit-params [:format] "json")
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" (:instance-id audit-params))
                  (nodei/add-property "eventID" (:event-id audit-params))
                  (nodei/add-property "tags" tags))]
    (-> (template/build nodei node)
        (template/publish format))))
