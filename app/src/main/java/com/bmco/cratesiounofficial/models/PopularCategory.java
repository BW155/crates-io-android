
package com.bmco.cratesiounofficial.models;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "category",
    "crates_cnt",
    "created_at",
    "description",
    "id",
    "slug"
})
public class PopularCategory {

    @JsonProperty("category")
    public String category;
    @JsonProperty("crates_cnt")
    public int cratesCnt;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("description")
    public String description;
    @JsonProperty("id")
    public String id;
    @JsonProperty("slug")
    public String slug;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public PopularCategory withCategory(String category) {
        this.category = category;
        return this;
    }

    public PopularCategory withCratesCnt(int cratesCnt) {
        this.cratesCnt = cratesCnt;
        return this;
    }

    public PopularCategory withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PopularCategory withDescription(String description) {
        this.description = description;
        return this;
    }

    public PopularCategory withId(String id) {
        this.id = id;
        return this;
    }

    public PopularCategory withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public PopularCategory withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
