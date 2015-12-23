(ns tosca-lens.tags
  (:require [amazonica.aws.ec2 :as ec2]
            [clojure.tools.logging :as log]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-tosca.template :as template]
            [tosca-lens.util :as util]))

(defn get-data
  "Get the tags for the instance from data-for-instance."
  [data]
  (:tags (first (:instances data))))

(defn tosca
  "Build a tosca document with the tags and instance id."
  [data audit-params]
  (let [tags (get-data data)
        format (get-in audit-params [:format] "json")
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" (:instance-id audit-params))
                  (nodei/add-property "eventID" (:event-id audit-params))
                  (nodei/add-property "tags" tags))]
    (-> (template/build nodei node)
        (template/publish format))))
