(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all]]
   [amazonica.aws.ec2 :as ec2]
   [amazonica.aws.s3 :as s3]
   [clj-tosca.tosca :refer :all]
   [tosca-lens.util :as util]
   [tosca-lens.compute :as compute]
   [tosca-lens.storage :as storage])
  (:import [com.amazonaws.services.s3 AmazonS3Client]))

(def sample-id "i-3b9ds9a7")

(def creds {:access-key ""
            :secret-key ""
            :endpoint "us-east-1"})


(def s3-client
  (let [creds (com.amazonaws.auth.BasicAWSCredentials.
                "access key"
                "secret key")] (AmazonS3Client. creds)))




(def system
  "A Var containing an object representing the application under
  development."
  nil)

(defn init
  "Creates and initializes the system under development in the Var
  #'system."
  []
  ;; TODO
  )

(defn start
  "Starts the system running, updates the Var #'system."
  []
  ;; TODO
  )

(defn stop
  "Stops the system if it is currently running, updates the Var
  #'system."
  []
  ;; TODO
  )

(defn go
  "Initializes and starts the system running."
  []
  (init)
  (start)
  :ready)

(defn reset
  "Stops the system, reloads modified source files, and restarts it."
  []
  (stop)
  (refresh :after `go))
