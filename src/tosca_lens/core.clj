(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [clojure.tools.logging :as log]
            [tosca-lens.util :as util]))

(defn data-for-instance
  "Call describe instances and get the data for instance-id."
  [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [data (util/as-clj-map input)
        instance-id (:instance-id data)
        format (get-in data [:format] "json")
        document (-> (data-for-instance instance-id)
                     (tags/tosca-tags format))]
    document))
