
package com.bmco.cratesiounofficial.models;

import java.io.Serializable;
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
    "crate_id",
    "default_features",
    "downloads",
    "features",
    "id",
    "kind",
    "optional",
    "req",
    "target",
    "version_id"
})
public class Dependency implements Serializable {

    @JsonProperty("crate_id")
    private String crateId;
    @JsonProperty("default_features")
    private boolean defaultFeatures;
    @JsonProperty("downloads")
    private int downloads;
    @JsonProperty("features")
    private List<Object> features = new ArrayList<Object>();
    @JsonProperty("id")
    private int id;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("optional")
    private boolean optional;
    @JsonProperty("req")
    private String req;
    @JsonProperty("target")
    private Object target;
    @JsonProperty("version_id")
    private int versionId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("crate_id")
    public String getCrateId() {
        return crateId;
    }

    @JsonProperty("crate_id")
    public void setCrateId(String crateId) {
        this.crateId = crateId;
    }

    @JsonProperty("default_features")
    public boolean isDefaultFeatures() {
        return defaultFeatures;
    }

    @JsonProperty("default_features")
    public void setDefaultFeatures(boolean defaultFeatures) {
        this.defaultFeatures = defaultFeatures;
    }

    @JsonProperty("downloads")
    public int getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @JsonProperty("features")
    public List<Object> getFeatures() {
        return features;
    }

    @JsonProperty("features")
    public void setFeatures(List<Object> features) {
        this.features = features;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("optional")
    public boolean isOptional() {
        return optional;
    }

    @JsonProperty("optional")
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @JsonProperty("req")
    public String getReq() {
        return req;
    }

    @JsonProperty("req")
    public void setReq(String req) {
        this.req = req;
    }

    @JsonProperty("target")
    public Object getTarget() {
        return target;
    }

    @JsonProperty("target")
    public void setTarget(Object target) {
        this.target = target;
    }

    @JsonProperty("version_id")
    public int getVersionId() {
        return versionId;
    }

    @JsonProperty("version_id")
    public void setVersionId(int versionId) {
        this.versionId = versionId;
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
