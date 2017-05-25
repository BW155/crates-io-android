
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
    "badges",
    "categories",
    "created_at",
    "description",
    "documentation",
    "downloads",
    "exact_match",
    "homepage",
    "id",
    "keywords",
    "license",
    "links",
    "max_version",
    "name",
    "repository",
    "updated_at",
    "versions"
})
public class JustUpdated {

    @JsonProperty("badges")
    public Object badges;
    @JsonProperty("categories")
    public Object categories;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("description")
    public String description;
    @JsonProperty("documentation")
    public String documentation;
    @JsonProperty("downloads")
    public int downloads;
    @JsonProperty("exact_match")
    public boolean exactMatch;
    @JsonProperty("homepage")
    public Object homepage;
    @JsonProperty("id")
    public String id;
    @JsonProperty("keywords")
    public Object keywords;
    @JsonProperty("license")
    public String license;
    @JsonProperty("links")
    public Links links;
    @JsonProperty("max_version")
    public String maxVersion;
    @JsonProperty("name")
    public String name;
    @JsonProperty("repository")
    public String repository;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("versions")
    public Object versions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public JustUpdated withBadges(Object badges) {
        this.badges = badges;
        return this;
    }

    public JustUpdated withCategories(Object categories) {
        this.categories = categories;
        return this;
    }

    public JustUpdated withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public JustUpdated withDescription(String description) {
        this.description = description;
        return this;
    }

    public JustUpdated withDocumentation(String documentation) {
        this.documentation = documentation;
        return this;
    }

    public JustUpdated withDownloads(int downloads) {
        this.downloads = downloads;
        return this;
    }

    public JustUpdated withExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
        return this;
    }

    public JustUpdated withHomepage(Object homepage) {
        this.homepage = homepage;
        return this;
    }

    public JustUpdated withId(String id) {
        this.id = id;
        return this;
    }

    public JustUpdated withKeywords(Object keywords) {
        this.keywords = keywords;
        return this;
    }

    public JustUpdated withLicense(String license) {
        this.license = license;
        return this;
    }

    public JustUpdated withLinks(Links links) {
        this.links = links;
        return this;
    }

    public JustUpdated withMaxVersion(String maxVersion) {
        this.maxVersion = maxVersion;
        return this;
    }

    public JustUpdated withName(String name) {
        this.name = name;
        return this;
    }

    public JustUpdated withRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public JustUpdated withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public JustUpdated withVersions(Object versions) {
        this.versions = versions;
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

    public JustUpdated withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
