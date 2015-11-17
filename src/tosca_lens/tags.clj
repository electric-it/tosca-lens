(ns tosca-lens.tags
  (:require [amazonica.aws.ec2 :as ec2]
            [clojure.tools.logging :as log]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-tosca.template :as template]
            [tosca-lens.util :as util]))

(defn tags-for-instance
  "Get the tags for the instance from data-for-instance."
  [data]
  (:tags (first (:instances data))))

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
