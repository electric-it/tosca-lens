# Overview
TOSCALens is an API for viewing heterogeneous IT assets in a TOSCA-compliant format. The API will return TOSCA-compliant metadata that describes many different types of assets. A pluggable driver model allows for additional providers to be introduced.

#  Lamba Function

Run as a lambda with a sample json of
```
{ "instance-id" : "xxxxxx"
  "creds" : {
    "access-key" : "xxxxxx",
    "access-secret" : "xxxxxx",
    "endpoint": "region id"
  }
}
```

Returns
```
"tosca_definitions_version: tosca_simple_yaml_1_0\nnode_instance:\n  properties:\n    instanceId: i-3b9ce217\n    tags:\n    - {value: compute-centos, key: Name}\n    - {value: ogs, key: app}\n    - {value: wes-ssa-csa, key: dept}\nnode_types:\n  ServerNode:\n    type: tosca.nodes.Compute\n    properties: {}\n    attributes: {}\n    requirements: {}\n    capabilities: {}\n    interfaces: {}\n    artifacts: {}\n    metadatas: {}\n"
```


# API
The API provides the following calls:
* List Providers
  * Returns a list of the providers supported by this TOSCALens
    * provider-id
    * provider-shortname
    * provider-description
* Describe Compute Instance
  * Takes an instance ID and returns a TOSCA-compliant description of that instance

# Drivers
A driver is a plug-in that allows TOSCALens to return asset descriptions for a particular provider.

**AWS Driver**

Supports: Compute Instances

**VMWare Driver**

Supports: Compute Instances

# Comparison to OpenStack Congress
https://github.com/openstack/congress
https://github.com/openstack/congress/blob/master/congress/datasources/vCenter_driver.py

The Congress project from OpenStack is conceptually very similar to the whole of the effort around ReaperBot. Though, the design and implementation is much different. OpenStack Congress is a policy enforcement engine that will work against multiple cloud providers.

**Key Differences:**
* Congress is a complete project, with a TOSCA style view of APIs and policy engine
 * ReaperBot is an orchestrated collection of standalone services. These services can be easily used outside of the context of ReaperBot.
* Congress utilizes a custom static language to represent policy logic
 * ReaperBot utilizes lambda functions to allow the logic for each policy to be expressed in one of several different languages
* Congress is written in Python
 * ReaperBot is not
