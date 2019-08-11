/// Generated using `http://www.jsonschema2pojo.org/`
package com.bmco.cratesiounofficial.models

import java.util.ArrayList
import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("just_updated", "most_downloaded", "new_crates", "num_crates", "num_downloads", "popular_categories", "popular_keywords")
class Summary {

    @JsonProperty("just_updated")
    @get:JsonProperty("just_updated")
    @set:JsonProperty("just_updated")
    var justUpdated: List<Crate> = ArrayList()
    @JsonProperty("most_downloaded")
    @get:JsonProperty("most_downloaded")
    @set:JsonProperty("most_downloaded")
    var mostDownloaded: List<Crate> = ArrayList()
    @JsonProperty("new_crates")
    @get:JsonProperty("new_crates")
    @set:JsonProperty("new_crates")
    var newCrates: List<Crate> = ArrayList()
    @JsonProperty("num_crates")
    @get:JsonProperty("num_crates")
    @set:JsonProperty("num_crates")
    var numCrates: Int = 0
    @JsonProperty("num_downloads")
    @get:JsonProperty("num_downloads")
    @set:JsonProperty("num_downloads")
    var numDownloads: Int = 0
    @JsonProperty("popular_categories")
    @get:JsonProperty("popular_categories")
    @set:JsonProperty("popular_categories")
    var popularCategories: List<PopularCategory> = ArrayList()
    @JsonProperty("popular_keywords")
    @get:JsonProperty("popular_keywords")
    @set:JsonProperty("popular_keywords")
    var popularKeywords: List<PopularKeyword> = ArrayList()
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
