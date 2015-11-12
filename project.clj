(defproject tosca-lens "0.1.0-SNAPSHOT"
  :description "Given a machine id and attribute examine the state."
  :license "Apache Version 2.0"
  :url "https://github.com/electric-it/tosca-lens"
  :scm {:name "git"
        :url "https://github.com/electric-it/tosca-lens"}  
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [com.amazonaws/aws-lambda-java-core "1.0.0"]
                 [clj-yaml "0.4.0"]
                 [electric-it/clj-tosca "1.0.0"]
                 [amazonica "0.3.34"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all}})
