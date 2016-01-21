(ns tosca-lens.core
  (:gen-class
   :methods [^:static [lambda [Object] String]])
  (:require [clojure.tools.logging :as log]
            [tosca-lens.util :as util]
            [tosca-lens.audit :as audit]))

(defn -lambda
  "Lambda function called by AWS Lambda."
  [input]
  (let [audit-params (util/as-clj-map input)
        document (audit/audit-tosca audit-params)]
    (log/info (str "loading audit for " (:event-name audit-params)))
    document))
