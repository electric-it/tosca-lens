(ns tosca-lens.audit
  (:require [tosca-lens.compute :as compute]
            [tosca-lens.storage :as storage]
            [clojure.tools.logging :as log]))

(defn audit-tosca
  "Given the the event-name and instance-id and bucket-name get the tosca for current values."
  [audit-params]
  (log/info (str "incoming " (pr-str audit-params)))
  (case (:event-name audit-params)
    "CreateTags"          (compute/tosca-tags audit-params)
    "DeleteTags"          (compute/tosca-tags audit-params)
    "CreateSecurityGroup" (compute/tosca-security-group audit-params)
    "CreateBucket"        (storage/tosca audit-params)
    "unknown event"))
