# Overview
TOSCALens is an API for viewing heterogeneous IT assets in a TOSCA-compliant format. The API will return TOSCA-compliant metadata that describes many different types of assets. 

This first version of tosca-lens takes in json and returns a list of the tags on the instance in [TOSCA](https://www.oasis-open.org/committees/tc_home.php) format.

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
  "format" : "yaml"
}
```

The format is an optional parameter, the default is json. The result is returns as a string.

json escaped string

```text
"{"tosca_definitions_version":"tosca_simple_yaml_1_0","node_instance":{"properties":{"instanceId":"i-3b9c1e97","tags":[{"value":"compute-centos","key":"Name"},{"value":"ngs","key":"app"},{"value":"ssa-csa","key":"dept"}]}},"node_types":{"ServerNode":{"type":"tosca.nodes.Compute","properties":{},"attributes":{},"requirements":{},"capabilities":{},"interfaces":{},"artifacts":{},"metadatas":{}}}}"
```

yaml escaped string

```text
"tosca_definitions_version: tosca_simple_yaml_1_0 node_instance: properties: instanceId: i-3b9c1e97 tags: - {value: compute-centos, key: Name} - {value: ngs, key: app} - {value: ssa-csa, key: dept} node_types: ServerNode: type: tosca.nodes.Compute properties: {} attributes: {} requirements: {} capabilities: {} interfaces: {} artifacts: {} metadatas: {}"
```

edn escaped string

```text
"{:tosca_definitions_version "tosca_simple_yaml_1_0", :node_instance {:properties {:instanceId "i-3b9c1e97", :tags [{:value "compute-centos", :key "Name"} {:value "ngs", :key "app"} {:value "ssa-csa", :key "dept"}]}}, :node_types {:ServerNode {:type "tosca.nodes.Compute", :properties {}, :attributes {}, :requirements {}, :capabilities {}, :interfaces {}, :artifacts {}, :metadatas {}}}}"


