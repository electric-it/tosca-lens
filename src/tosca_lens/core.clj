(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [clojure.tools.logging :as log]
            [amazonica.aws.ec2 :as ec2]
            [tosca-lens.util :as util]
            [tosca-lens.tags :as tags]))

(defn data-for-instance
  "Call describe instances and get the data for instance-id."
  [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn audit-tosca
  "Given the the audit name and instance get the tosca for current values."
  [data audit-name format]
  (case audit-name
    "tags" (tags/tosca data format)
    "unknown event"))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [data (util/as-clj-map input)
        instance-id (:instance-id data)
        audit-name (:audit-name data)
        format (get-in data [:format] "json")
        document (-> (data-for-instance instance-id)
                     (audit-tosca data format))]
       (log/info (str "loading audit for " audit-name))
    document))
