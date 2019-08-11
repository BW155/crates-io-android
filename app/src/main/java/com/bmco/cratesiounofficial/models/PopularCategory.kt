package com.bmco.cratesiounofficial.models

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("category", "crates_cnt", "created_at", "description", "id", "slug")
class PopularCategory {

    @JsonProperty("category")
    @get:JsonProperty("category")
    @set:JsonProperty("category")
    var category: String? = null
    @JsonProperty("crates_cnt")
    @get:JsonProperty("crates_cnt")
    @set:JsonProperty("crates_cnt")
    var cratesCnt: Int = 0
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("description")
    @get:JsonProperty("description")
    @set:JsonProperty("description")
    var description: String? = null
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("slug")
    @get:JsonProperty("slug")
    @set:JsonProperty("slug")
    var slug: String? = null
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
