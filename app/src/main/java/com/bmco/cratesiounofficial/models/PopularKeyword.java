
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
    "crates_cnt",
    "created_at",
    "id",
    "keyword"
})
public class PopularKeyword {

    @JsonProperty("crates_cnt")
    public int cratesCnt;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("id")
    public String id;
    @JsonProperty("keyword")
    public String keyword;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public PopularKeyword withCratesCnt(int cratesCnt) {
        this.cratesCnt = cratesCnt;
        return this;
    }

    public PopularKeyword withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PopularKeyword withId(String id) {
        this.id = id;
        return this;
    }

    public PopularKeyword withKeyword(String keyword) {
        this.keyword = keyword;
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

    public PopularKeyword withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
