
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
public class Links_ {

    @JsonProperty("owners")
    public String owners;
    @JsonProperty("reverse_dependencies")
    public String reverseDependencies;
    @JsonProperty("version_downloads")
    public String versionDownloads;
    @JsonProperty("versions")
    public String versions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Links_ withOwners(String owners) {
        this.owners = owners;
        return this;
    }

    public Links_ withReverseDependencies(String reverseDependencies) {
        this.reverseDependencies = reverseDependencies;
        return this;
    }

    public Links_ withVersionDownloads(String versionDownloads) {
        this.versionDownloads = versionDownloads;
        return this;
    }

    public Links_ withVersions(String versions) {
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

    public Links_ withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
