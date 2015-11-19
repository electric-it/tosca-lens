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
  [data format]
  (let [tags (get-data data)
        instance-id (:instance-id data)
        node (node/build)
        nodei (-> (nodei/build)
                  (nodei/add-property "instanceId" instance-id)
                  (nodei/add-property "tags" tags))]
    (-> (template/build nodei node)
        (template/publish format))))
