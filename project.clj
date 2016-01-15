(defproject electric-it/tosca-lens "1.0.0"
  :description "Given a machine id and attribute examine the state."
  :license "Apache Version 2.0"
  :url "https://github.com/electric-it/tosca-lens"
  :scm {:name "git"
        :url "https://github.com/electric-it/tosca-lens"}  
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [org.clojure/core.async "0.2.371"]
                 [io.nervous/cljs-lambda "0.1.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [com.amazonaws/aws-lambda-java-core "1.0.0"]
                 [clj-yaml "0.4.0"]
                 [electric-it/clj-tosca "1.0.1"]
                 [com.amazonaws/aws-java-sdk "1.10.26"]
                 [amazonica "0.3.43"]]
  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-npm "0.5.0"]
            [io.nervous/lein-cljs-lambda "0.2.4"]]
  :node-dependancies [[source-map-support "0.2.8"]]
  :source-paths ["src"]
  :cljs-lambda
    {:defaults {:role "arn:aws:iam::485336146512:role/bot-platform-lambda"}
     :functions
       [{:name "test-lambda"
         :invoke tosca-lens.lambda/test-lambda}]}

  :cljsbuild
  {:builds [{:id "tosca-lens-lambda"
             :source-paths ["src"]
             :compiler {:output-to "out/tosca-lens.js"
                        :output-dir "out"
                        :target :nodejs
                        :optimizations :none
                        :source-map true}}]}
      
    
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}
             #_:uberjar #_{:aot :all}})
