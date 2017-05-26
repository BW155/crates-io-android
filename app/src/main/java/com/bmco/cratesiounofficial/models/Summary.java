/// Generated using `http://www.jsonschema2pojo.org/`
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
    private List<JustUpdated> justUpdated = new ArrayList<JustUpdated>();
    @JsonProperty("most_downloaded")
    private List<MostDownloaded> mostDownloaded = new ArrayList<MostDownloaded>();
    @JsonProperty("new_crates")
    private List<NewCrate> newCrates = new ArrayList<NewCrate>();
    @JsonProperty("num_crates")
    private int numCrates;
    @JsonProperty("num_downloads")
    private int numDownloads;
    @JsonProperty("popular_categories")
    private List<PopularCategory> popularCategories = new ArrayList<PopularCategory>();
    @JsonProperty("popular_keywords")
    private List<PopularKeyword> popularKeywords = new ArrayList<PopularKeyword>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("just_updated")
    public List<JustUpdated> getJustUpdated() {
        return justUpdated;
    }

    @JsonProperty("just_updated")
    public void setJustUpdated(List<JustUpdated> justUpdated) {
        this.justUpdated = justUpdated;
    }

    @JsonProperty("most_downloaded")
    public List<MostDownloaded> getMostDownloaded() {
        return mostDownloaded;
    }

    @JsonProperty("most_downloaded")
    public void setMostDownloaded(List<MostDownloaded> mostDownloaded) {
        this.mostDownloaded = mostDownloaded;
    }

    @JsonProperty("new_crates")
    public List<NewCrate> getNewCrates() {
        return newCrates;
    }

    @JsonProperty("new_crates")
    public void setNewCrates(List<NewCrate> newCrates) {
        this.newCrates = newCrates;
    }

    @JsonProperty("num_crates")
    public int getNumCrates() {
        return numCrates;
    }

    @JsonProperty("num_crates")
    public void setNumCrates(int numCrates) {
        this.numCrates = numCrates;
    }

    @JsonProperty("num_downloads")
    public int getNumDownloads() {
        return numDownloads;
    }

    @JsonProperty("num_downloads")
    public void setNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
    }

    @JsonProperty("popular_categories")
    public List<PopularCategory> getPopularCategories() {
        return popularCategories;
    }

    @JsonProperty("popular_categories")
    public void setPopularCategories(List<PopularCategory> popularCategories) {
        this.popularCategories = popularCategories;
    }

    @JsonProperty("popular_keywords")
    public List<PopularKeyword> getPopularKeywords() {
        return popularKeywords;
    }

    @JsonProperty("popular_keywords")
    public void setPopularKeywords(List<PopularKeyword> popularKeywords) {
        this.popularKeywords = popularKeywords;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
