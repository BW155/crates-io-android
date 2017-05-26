
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
    "owners",
    "reverse_dependencies",
    "version_downloads",
    "versions"
})
public class Links {

    @JsonProperty("owners")
    private String owners;
    @JsonProperty("reverse_dependencies")
    private String reverseDependencies;
    @JsonProperty("version_downloads")
    private String versionDownloads;
    @JsonProperty("versions")
    private String versions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("owners")
    public String getOwners() {
        return owners;
    }

    @JsonProperty("owners")
    public void setOwners(String owners) {
        this.owners = owners;
    }

    @JsonProperty("reverse_dependencies")
    public String getReverseDependencies() {
        return reverseDependencies;
    }

    @JsonProperty("reverse_dependencies")
    public void setReverseDependencies(String reverseDependencies) {
        this.reverseDependencies = reverseDependencies;
    }

    @JsonProperty("version_downloads")
    public String getVersionDownloads() {
        return versionDownloads;
    }

    @JsonProperty("version_downloads")
    public void setVersionDownloads(String versionDownloads) {
        this.versionDownloads = versionDownloads;
    }

    @JsonProperty("versions")
    public String getVersions() {
        return versions;
    }

    @JsonProperty("versions")
    public void setVersions(String versions) {
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

}
