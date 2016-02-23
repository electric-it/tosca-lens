(ns tosca-lens.storage
  (:require [amazonica.aws.s3 :as s3]
            [clojure.tools.logging :as log]
            [clj-tosca.tosca :refer :all]))

(defn get-metadata [bucket-name]
  (s3/get-bucket-acl bucket-name))

(defn get-data [bucket-name]
  (-> (s3/get-bucket-tagging-configuration {:bucket-name bucket-name})
    :tag-sets
    first
    :tags))

(defn tosca
  "Build a tosca document to describe a storage instance given audit-params."
  [audit-params]
  (let [tags (get-data (:bucket-name audit-params))
        format (get-in audit-params [:format] "json")
        node (node-build "tosca.nodes.S3Storage")
        instance (-> (instance-build)
                     (instance-add-property "tags" tags))]
    (-> (template-build instance node)
        (template-publish format))))
