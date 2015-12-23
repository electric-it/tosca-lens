(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [clojure.tools.logging :as log]
            [amazonica.aws.ec2 :as ec2]
            [tosca-lens.util :as util]
            [tosca-lens.tags :as tags]))

(defn instance-data
  "Call describe instances and get the data for instance-id."
  [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn audit-tosca
  "Given the the audit name and instance get the tosca for current values."
  [instance-data audit-params format]
  (case (:audit-name audit-params) 
    "tags" (tags/tosca instance-data audit-params format)
    "unknown event"))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [audit-params (util/as-clj-map input)
        instance-id (:instance-id data)
        format (get-in audit-params [:format] "json")
        document (-> (instance-data instance-id)
                     (audit-tosca audit-param format))]
       (log/info (str "loading audit for " audit-name))
    document))
