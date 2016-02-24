(ns tosca-lens.compute
  (:require [amazonica.aws.ec2 :as ec2]
            [clojure.tools.logging :as log]
            [clj-tosca.tosca :refer :all]))

(defn get-instance-data
  "Call describe instances and get the data for instance-id."
  [instance-id]
  (let [instance-data (ec2/describe-instances {:instance-ids [instance-id]})]
    (first (:reservations instance-data))))

(defn get-groups
  "Get the security groups for the instance from data-for-instance."
  [data]
  (:security-groups (first (:instances data))))

(defn get-tags
  "Get the tags for the instance from data-for-instance."
  [data]
  (:tags (first (:instances data))))

(defn tosca-tags
  "Build a tosca document with the tags and instance id."
  [audit-params]
  (let [data (get-instance-data (:instance-id audit-params))
        tags (get-tags data)
        format (get audit-params :format "json")
        node (node-build)
        instance (-> (instance-build)
                     (instance-add-property "instanceId" (:instance-id audit-params))
                     (instance-add-property "eventID" (:event-id audit-params))
                     (instance-add-property "tags" tags))]
    (-> (template-build instance node)
        (template-publish format))))

(defn tosca-security-group
  "Build a tosca document with the security groups and instance id."
  [audit-params]
  (let [data (get-instance-data (:instance-id audit-params))
        groups (get-groups data)
        format (get audit-params :format "json")
        node (node-build)
        instance (-> (instance-build)
                     (instance-add-property "instanceId" (:instance-id audit-params))
                     (instance-add-property "eventID" (:event-id audit-params))
                     (instance-add-property "securityGroups" groups))]
    (-> (template-build instance node)
        (template-publish format))))
