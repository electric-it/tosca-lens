# Overview

TOSCALens is an API for viewing heterogeneous IT assets in a TOSCA-compliant format. The API will return TOSCA-compliant metadata that describes many different types of assets. 

This first version of tosca-lens takes in json and returns a list of the tags on the instance in [TOSCA](https://www.oasis-open.org/committees/tc_home.php) format.

# Output from describe-instances (partial output)

```edn
{:reservations [{:instances [{:monitoring {:state "disabled"}, :tags [{:value "ngs-compute-centos002", :key "Name"} {:value "ngs", :key "app"} {:value "grc-sw-ssa-csa", :key "dept"}],:public-dns-name "", :private-ip-address "10.190.5.22", :placement {:group-name "", :availability-zone "us-east-1a", :tenancy "default"}, :client-token "WiXXXXXXXXXXXX", :launch-time #object[org.joda.time.DateTime 0x2c6ba248 "2015-07-24T14:02:33.000-05:00"], :block-device-mappings [{:ebs {:volume-id "vol-76523794e8", :status "attached", :attach-time #object[org.joda.time.DateTime 0x6de92b19 "2015-07-21T08:58:00.000-05:00"], :delete-on-termination false}, :device-name "/dev/sda"}]}], :group-names [], :groups [], :owner-id "48113436146542", :reservation-id "r-06ec05fa"}]}
```

# Lamba Function

## Lambda Setup

1. Create a lamba function with a runtime of `java 8`, no need for a blueprint.
2. Set the role of the lambda to one that can read-only your ec2 instances (to run describe-instances).
3. Set the handler value to be `tosca_lens.core::lambda`.
4. Compile to a jar with `lein compile`. 
5. Upload to the Lambda code tab. You may have better luck uploading your jar file to S3 and using the url option. 


Test with the following:

```json
{
  "instance-id" : "an-instance-id",
  "format"      : "yaml",
  "audit-name"  : "tags",
  "event-id"    :  "7ea91w2c-a9b7-44e5-856f-39de8certe20dc"
}
```

Audit formats currently supported:

* tags - get all the tags currently on the instance

The format is an optional parameter, the default is json. The result is returns as a string.

Examples (spacing added for ease in reading)

json escaped string

```text
"{"tosca_definitions_version":"tosca_simple_yaml_1_0",
   "node_instance":
     {"properties":
       {"instanceId":"i-3b9c1e97",
        "tags":[{"value":"compute-centos","key":"Name"},
                {"value":"ngs","key":"app"},
                {"value":"ssa-csa","key":"dept"}]}},
   "node_types":
     {"ServerNode":
       {"type":"tosca.nodes.Compute",
        "properties":{},
        "attributes":{},
        "requirements":{},
        "capabilities":{},
        "interfaces":{},
        "artifacts":{},
        "metadatas":{}}}}"
```

yaml escaped string

```text
"tosca_definitions_version: tosca_simple_yaml_1_0
  node_instance:
    properties: instanceId: i-3b9c1e97
                tags: - {value: compute-centos, key: Name}
                      - {value: ngs, key: app}
                      - {value: ssa-csa, key: dept}
    node_types:
      ServerNode:
        type: tosca.nodes.Compute
        properties: {}
        attributes: {}
        requirements: {}
        capabilities: {}
        interfaces: {}
        artifacts: {}
        metadatas: {}"
```

edn escaped string

```text
"{:tosca_definitions_version "tosca_simple_yaml_1_0",
  :node_instance {
    :properties {:instanceId "i-3b9c1e97",
                 :tags [{:value "compute-centos", :key "Name"}
                        {:value "ngs", :key "app"}
                        {:value "ssa-csa", :key "dept"}]}},
  :node_types {
    :ServerNode {
      :type "tosca.nodes.Compute",
      :properties {},
      :attributes {},
      :requirements {},
      :capabilities {},
      :interfaces {},
      :artifacts {},
      :metadatas {}}}}"
```

## License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

