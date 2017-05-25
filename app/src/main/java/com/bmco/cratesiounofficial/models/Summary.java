
package com.bmco.cratesiounofficial.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "just_updated",
    "most_downloaded",
    "new_crates",
    "num_crates",
    "num_downloads",
    "popular_categories",
    "popular_keywords"
})
public class Summary {



    @JsonProperty("just_updated")
    public List<JustUpdated> justUpdated = new ArrayList<JustUpdated>();
    @JsonProperty("most_downloaded")
    public List<MostDownloaded> mostDownloaded = new ArrayList<MostDownloaded>();
    @JsonProperty("new_crates")
    public List<NewCrate> newCrates = new ArrayList<NewCrate>();
    @JsonProperty("num_crates")
    public int numCrates;
    @JsonProperty("num_downloads")
    public int numDownloads;
    @JsonProperty("popular_categories")
    public List<PopularCategory> popularCategories = new ArrayList<PopularCategory>();
    @JsonProperty("popular_keywords")
    public List<PopularKeyword> popularKeywords = new ArrayList<PopularKeyword>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Summary withJustUpdated(List<JustUpdated> justUpdated) {
        this.justUpdated = justUpdated;
        return this;
    }

    public Summary withMostDownloaded(List<MostDownloaded> mostDownloaded) {
        this.mostDownloaded = mostDownloaded;
        return this;
    }

    public Summary withNewCrates(List<NewCrate> newCrates) {
        this.newCrates = newCrates;
        return this;
    }

    public Summary withNumCrates(int numCrates) {
        this.numCrates = numCrates;
        return this;
    }

    public Summary withNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
        return this;
    }

    public Summary withPopularCategories(List<PopularCategory> popularCategories) {
        this.popularCategories = popularCategories;
        return this;
    }

    public Summary withPopularKeywords(List<PopularKeyword> popularKeywords) {
        this.popularKeywords = popularKeywords;
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

    public Summary withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
