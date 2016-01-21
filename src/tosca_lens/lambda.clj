(ns tosca-lens.lambda
  (:gen-class
   :methods [^:static [audit [Object] String]])
  (:require [clojure.tools.logging :as log]
            [tosca-lens.util :as util]
            [tosca-lens.audit :as audit]))

(defn -audit
  "Lambda function called by AWS Lambda."
  [input]
  (let [audit-params (util/as-clj-map input)
        document (audit/audit-tosca audit-params)]
    (log/info (str "loading audit for " (:event-name audit-params)))
    document))
