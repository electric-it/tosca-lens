(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [clojure.tools.logging :as log]
            [tosca-lens.util :as util]
            [tosca-lens.tags :as tags]
            [tosca-lens.s3 :as t3]))

(defn audit-tosca
  "Given the the event-name and instance-id and bucket-name get the tosca for current values."
  [audit-params]
  (case (:event-name audit-params) 
    "CreateTags"   (tags/tosca audit-params)
    "DeleteTags"   (tags/tosca audit-params)
    "CreateBucket" (t3/tosca audit-params)
    "unknown event"))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [audit-params (util/as-clj-map input)
        document (audit-tosca audit-params)]
       (log/info (str "loading audit for " (:event-name audit-params)))
       document))
