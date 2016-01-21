(ns tosca-lens.audit
  (:require [tosca-lens.tags :as tags]
            [tosca-lens.s3 :as s3]))

(defn audit-tosca
  "Given the the event-name and instance-id and bucket-name get the tosca for current values."
  [audit-params]
  (case (:event-name audit-params)
    "CreateTags"   (tags/tosca audit-params)
    "DeleteTags"   (tags/tosca audit-params)
    "CreateBucket" (s3/tosca audit-params)
    "unknown event"))
