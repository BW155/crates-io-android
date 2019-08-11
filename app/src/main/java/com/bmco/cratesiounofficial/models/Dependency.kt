package com.bmco.cratesiounofficial.models

import java.io.Serializable
import java.util.ArrayList
import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("crate_id", "default_features", "downloads", "features", "id", "kind", "optional", "req", "target", "version_id")
class Dependency : Serializable {

    @JsonProperty("crate_id")
    @get:JsonProperty("crate_id")
    @set:JsonProperty("crate_id")
    var crateId: String? = null
    @JsonProperty("default_features")
    @get:JsonProperty("default_features")
    @set:JsonProperty("default_features")
    var isDefaultFeatures: Boolean = false
    @JsonProperty("downloads")
    @get:JsonProperty("downloads")
    @set:JsonProperty("downloads")
    var downloads: Int = 0
    @JsonProperty("features")
    @get:JsonProperty("features")
    @set:JsonProperty("features")
    var features: List<Any> = ArrayList()
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int = 0
    @JsonProperty("kind")
    @get:JsonProperty("kind")
    @set:JsonProperty("kind")
    var kind: String? = null
    @JsonProperty("optional")
    @get:JsonProperty("optional")
    @set:JsonProperty("optional")
    var isOptional: Boolean = false
    @JsonProperty("req")
    @get:JsonProperty("req")
    @set:JsonProperty("req")
    var req: String? = null
    @JsonProperty("target")
    @get:JsonProperty("target")
    @set:JsonProperty("target")
    var target: Any? = null
    @JsonProperty("version_id")
    @get:JsonProperty("version_id")
    @set:JsonProperty("version_id")
    var versionId: Int = 0
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }

}
