package com.bmco.cratesiounofficial.models

import java.io.Serializable
import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("owners", "reverse_dependencies", "version_downloads", "versions")
class Links : Serializable {

    @JsonProperty("owners")
    @get:JsonProperty("owners")
    @set:JsonProperty("owners")
    var owners: String? = null
    @JsonProperty("reverse_dependencies")
    @get:JsonProperty("reverse_dependencies")
    @set:JsonProperty("reverse_dependencies")
    var reverseDependencies: String? = null
    @JsonProperty("version_downloads")
    @get:JsonProperty("version_downloads")
    @set:JsonProperty("version_downloads")
    var versionDownloads: String? = null
    @JsonProperty("versions")
    @get:JsonProperty("versions")
    @set:JsonProperty("versions")
    var versions: String? = null
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
