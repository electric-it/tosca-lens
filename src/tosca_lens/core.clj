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
  [instance-data audit-params]
  (case (:audit-name audit-params) 
    "tags" (tags/tosca instance-data audit-params)
    "unknown event"))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [audit-params (util/as-clj-map input)
        document (-> (instance-data (:instance-id audit-params))
                     (audit-tosca audit-params))]
       (log/info (str "loading audit for " (:audit-name audit-params)))
       document))
