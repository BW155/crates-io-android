package com.bmco.cratesiounofficial.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "crate", "num", "dl_path", "readme_path", "updated_at", "created_at", "downloads", "features", "yanked", "license", "links")
class Version: Serializable {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("crate")
    @get:JsonProperty("crate")
    @set:JsonProperty("crate")
    var crate: String? = null
    @JsonProperty("num")
    @get:JsonProperty("num")
    @set:JsonProperty("num")
    var num: String? = null
    @JsonProperty("dl_path")
    @get:JsonProperty("dl_path")
    @set:JsonProperty("dl_path")
    var dlPath: String? = null
    @JsonProperty("readme_path")
    @get:JsonProperty("readme_path")
    @set:JsonProperty("readme_path")
    var readmePath: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("downloads")
    @get:JsonProperty("downloads")
    @set:JsonProperty("downloads")
    var downloads: Int? = null
    @JsonProperty("features")
    @get:JsonProperty("features")
    @set:JsonProperty("features")
    var features: Any? = null
    @JsonProperty("yanked")
    @get:JsonProperty("yanked")
    @set:JsonProperty("yanked")
    var yanked: Boolean? = null
    @JsonProperty("license")
    @get:JsonProperty("license")
    @set:JsonProperty("license")
    var license: String? = null
    @JsonProperty("links")
    @get:JsonProperty("links")
    @set:JsonProperty("links")
    var links: Links? = null
    @JsonIgnore
    var readme: String? = null
    @JsonProperty("crate_size")
    @get:JsonProperty("crate_size")
    @set:JsonProperty("crate_size")
    var crate_size: Int? = null
    @JsonProperty("published_by")
    var published_by: User? = null
}
