(ns tosca-lens.s3
  (:require [amazonica.aws.s3 :as s3]
            [clojure.tools.logging :as log]
            [clj-tosca.node :as node]
            [clj-tosca.node-instance :as nodei]
            [clj-tosca.template :as template]))

(defn get-metadata [bucket-name]
  (s3/get-bucket-acl bucket-name))

(defn get-data [bucket-name]
  (-> (s3/get-bucket-tagging-configuration {:bucket-name bucket-name})
    :tag-sets
    first
    :tags))

(defn tosca
  "Build a tosca document to describe an s3 resource"
  [audit-params]
  (let [tags (get-data (:bucket-name audit-params))
        meta (get-metadata (:bucket-name audit-params))
        format (get-in audit-params [:format] "json")
        node (node/build "tosca.nodes.S3Storage")
        nodei (-> (nodei/build)
                  (nodei/add-property "tags" tags)
                  (nodei/add-property "ownerName" (:display-name (:owner meta)))
                  (nodei/add-property "ownerID" (:id (:owner meta))))]
    (-> (template/build nodei node)
        (template/publish format))))