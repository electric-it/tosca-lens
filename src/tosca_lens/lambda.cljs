(ns tosca-lens.lambda
  (:require [cljs-lambda.util :refer [async-lambda-fn]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def ^:export test-lambda
  (async-lambda-fn
   (fn [{:keys [variety]} context]
     (go
       (js/Error (str "Sorry " variety " magic"))))))
