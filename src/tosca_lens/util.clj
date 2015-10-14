(ns tosca-lens.util
  (:require [clojure.data.json :as json]
            [clj-yaml.core :as yaml]))

(defn load-json [data]
  (json/read-str data :key-fn keyword))

(defn write-json [edn]
  (json/write-str edn))

(defn load-json-file[file]
  (load-json (slurp file)))

(defn write-yaml [edn]
  (yaml/generate-string edn))


;; converting java linkedhashmaps
(defprotocol ConvertibleToClojure
  (->clj [o]))

(extend-protocol ConvertibleToClojure
  java.util.Map
  (->clj [o] (let [entries (.entrySet o)]
               (reduce (fn [m [^String k v]]
                         (assoc m (keyword k) (->clj v)))
                       {} entries)))

  java.util.List
  (->clj [o] (vec (map ->clj o)))

  java.lang.Object
  (->clj [o] o)

  nil
  (->clj [_] nil))

(defn as-clj-map
  [m]
  (->clj m))
