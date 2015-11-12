# Overview
TOSCALens is an API for viewing heterogeneous IT assets in a TOSCA-compliant format. The API will return TOSCA-compliant metadata that describes many different types of assets. A pluggable driver model allows for additional providers to be introduced.

#  Lamba Function

This first version of tosca-lens takes in the following json and returns a list of the tags on the instance.

Run as a lambda with a sample json of

```json
{
  "instance-id" : "xxxxxx",
  "format" : "yaml"
}
```

format is an optional parameter, the default is json if you dont specify. 

The lambda function Returns the format as an escaped string

json escaped string
```
"{"tosca_definitions_version":"tosca_simple_yaml_1_0","node_instance":{"properties":{"instanceId":"i-3b9c1e97","tags":[{"value":"ngs-compute-centos002","key":"Name"},{"value":"ngs","key":"app"},{"value":"grc-sw-ssa-csa","key":"dept"}]}},"node_types":{"ServerNode":{"type":"tosca.nodes.Compute","properties":{},"attributes":{},"requirements":{},"capabilities":{},"interfaces":{},"artifacts":{},"metadatas":{}}}}"
```

yaml escaped string
```
"tosca_definitions_version: tosca_simple_yaml_1_0 node_instance: properties: instanceId: i-3b9c1e97 tags: - {value: ngs-compute-centos002, key: Name} - {value: ngs, key: app} - {value: grc-sw-ssa-csa, key: dept} node_types: ServerNode: type: tosca.nodes.Compute properties: {} attributes: {} requirements: {} capabilities: {} interfaces: {} artifacts: {} metadatas: {} "
```

edn escaped string
```
"{:tosca_definitions_version "tosca_simple_yaml_1_0", :node_instance {:properties {:instanceId "i-3b9c1e97", :tags [{:value "ngs-compute-centos002", :key "Name"} {:value "ngs", :key "app"} {:value "grc-sw-ssa-csa", :key "dept"}]}}, :node_types {:ServerNode {:type "tosca.nodes.Compute", :properties {}, :attributes {}, :requirements {}, :capabilities {}, :interfaces {}, :artifacts {}, :metadatas {}}}}"


