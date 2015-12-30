(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [clojure.tools.logging :as log]
            [tosca-lens.util :as util]
            [tosca-lens.tags :as tags]
            [tosca-lens.s3 :as t3]))

(defn audit-tosca
  "Given the the audit name and instance-id or bucket-name get the tosca for current values."
  [audit-params]
  (case (:audit-name audit-params) 
    "tags"    (tags/tosca audit-params)
    "s3-tags" (t3/tosca audit-params)
    "unknown event"))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [audit-params (util/as-clj-map input)
        document (audit-tosca audit-params)]
       (log/info (str "loading audit for " (:audit-name audit-params)))
       document))
