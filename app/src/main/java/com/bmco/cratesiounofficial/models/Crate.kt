package com.bmco.cratesiounofficial.models

import com.bmco.cratesiounofficial.CrateNotifier
import com.bmco.cratesiounofficial.Networking
import com.bmco.cratesiounofficial.interfaces.OnDependencyDownloadListener
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

import java.io.Serializable
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("badges", "categories", "created_at", "description", "documentation", "downloads", "exact_match", "homepage", "id", "keywords", "license", "links", "max_version", "name", "repository", "updated_at", "versions")
class Crate : Serializable {

    @JsonProperty("badges")
    @get:JsonProperty("badges")
    @set:JsonProperty("badges")
    var badges: Any? = null
    @JsonProperty("categories")
    @get:JsonProperty("categories")
    @set:JsonProperty("categories")
    var categories: Any? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("description")
    @get:JsonProperty("description")
    @set:JsonProperty("description")
    var description: String? = null
    @JsonProperty("documentation")
    @get:JsonProperty("documentation")
    @set:JsonProperty("documentation")
    var documentation: String? = null
    @JsonProperty("downloads")
    @get:JsonProperty("downloads")
    @set:JsonProperty("downloads")
    var downloads: Int = 0
    @JsonProperty("exact_match")
    @get:JsonProperty("exact_match")
    @set:JsonProperty("exact_match")
    var isExactMatch: Boolean = false
    @JsonProperty("homepage")
    @get:JsonProperty("homepage")
    @set:JsonProperty("homepage")
    var homepage: String? = null
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("keywords")
    @get:JsonProperty("keywords")
    @set:JsonProperty("keywords")
    var keywords: Any? = null
    @JsonProperty("license")
    @get:JsonProperty("license")
    @set:JsonProperty("license")
    var license: String? = null
    @JsonProperty("links")
    @get:JsonProperty("links")
    @set:JsonProperty("links")
    var links: Links? = null
    @JsonProperty("max_version")
    @get:JsonProperty("max_version")
    @set:JsonProperty("max_version")
    var maxVersion: String? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null
    @JsonProperty("repository")
    @get:JsonProperty("repository")
    @set:JsonProperty("repository")
    var repository: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
    @JsonProperty("versions")
    @get:JsonProperty("versions")
    @set:JsonProperty("versions")
    var versions: List<Version>? = null
    @JsonProperty("recent_downloads")
    @get:JsonProperty("recent_downloads")
    @set:JsonProperty("recent_downloads")
    var recentDownloads: Int? = null

    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()
    var versionList: List<Version>? = null

    private var dependencies: List<Dependency>? = null

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }

    fun getDependencies(listener: OnDependencyDownloadListener?) {
        if (this.dependencies == null) {
            Networking.getDependenciesForCrate(this.id!!, this.maxVersion!!, { dependencies ->
                this@Crate.dependencies = dependencies
                listener?.onDependenciesReady(dependencies)
            }, {error ->
                /// todo: error message
            })
        } else {
            listener!!.onDependenciesReady(this.dependencies!!)
        }
    }

}
