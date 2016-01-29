(defproject electric-it/tosca-lens "1.0.0"
  :description "Given an audit name and params examine the attributes."
  :license "Apache Version 2.0"
  :url "https://github.com/electric-it/tosca-lens"
  :scm {:name "git"
        :url "https://github.com/electric-it/tosca-lens"}  
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [clj-yaml "0.4.0"]
                 [electric-it/clj-tosca "1.0.1"]
                 [amazonica "0.3.43"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}})
