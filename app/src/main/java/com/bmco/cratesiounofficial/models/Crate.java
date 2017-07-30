
package com.bmco.cratesiounofficial.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bmco.cratesiounofficial.CrateNotifier;
import com.bmco.cratesiounofficial.CratesIONetworking;
import com.bmco.cratesiounofficial.OnDependencyDownloadListener;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.deser.impl.CreatorCollector;

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
public class Crate implements Serializable {

    @JsonProperty("badges")
    private Object badges;
    @JsonProperty("categories")
    private Object categories;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("description")
    private String description;
    @JsonProperty("documentation")
    private Object documentation;
    @JsonProperty("downloads")
    private int downloads;
    @JsonProperty("exact_match")
    private boolean exactMatch;
    @JsonProperty("homepage")
    private Object homepage;
    @JsonProperty("id")
    private String id;
    @JsonProperty("keywords")
    private Object keywords;
    @JsonProperty("license")
    private String license;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("max_version")
    private String maxVersion;
    @JsonProperty("name")
    private String name;
    @JsonProperty("repository")
    private String repository;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("versions")
    private Object versions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    private List<Dependency> dependencies;

    @JsonProperty("badges")
    public Object getBadges() {
        return badges;
    }

    @JsonProperty("badges")
    public void setBadges(Object badges) {
        this.badges = badges;
    }

    @JsonProperty("categories")
    public Object getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(Object categories) {
        this.categories = categories;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("documentation")
    public Object getDocumentation() {
        return documentation;
    }

    @JsonProperty("documentation")
    public void setDocumentation(Object documentation) {
        this.documentation = documentation;
    }

    @JsonProperty("downloads")
    public int getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @JsonProperty("exact_match")
    public boolean isExactMatch() {
        return exactMatch;
    }

    @JsonProperty("exact_match")
    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    @JsonProperty("homepage")
    public Object getHomepage() {
        return homepage;
    }

    @JsonProperty("homepage")
    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("keywords")
    public Object getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(Object keywords) {
        this.keywords = keywords;
    }

    @JsonProperty("license")
    public String getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(String license) {
        this.license = license;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("max_version")
    public String getMaxVersion() {
        return maxVersion;
    }

    @JsonProperty("max_version")
    public void setMaxVersion(String maxVersion) {
        this.maxVersion = maxVersion;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("repository")
    public String getRepository() {
        return repository;
    }

    @JsonProperty("repository")
    public void setRepository(String repository) {
        this.repository = repository;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("versions")
    public Object getVersions() {
        return versions;
    }

    @JsonProperty("versions")
    public void setVersions(Object versions) {
        this.versions = versions;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void getDependencies(final OnDependencyDownloadListener listener) {
        if (this.dependencies == null) {
            final String finalId = this.getId();
            final String finalVersion = this.getMaxVersion();
            Thread depThread = new Thread() {
                public void run() {
                    List<Dependency> dependencies = CratesIONetworking.getDependenciesForCrate(finalId, finalVersion);
                    Crate.this.dependencies = dependencies;
                    if (listener != null) {
                        listener.onDependenciesReady(dependencies);
                    }
                }
            };
            depThread.start();
        } else {
            listener.onDependenciesReady(this.dependencies);
        }
    }

    public boolean compareForAlert(Crate crate, CrateNotifier.AlertType type) {
        switch (type) {
            case DOWNLOADS:
                return crate.getDownloads() != this.downloads;
            case VERSION:
                return !crate.getMaxVersion().equals(this.maxVersion);
            default:
                return false;

        }
    }
}
