
package com.bmco.cratesiounofficial.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "crate",
    "num",
    "dl_path",
    "readme_path",
    "updated_at",
    "created_at",
    "downloads",
    "features",
    "yanked",
    "license",
    "links"
})

public class Version {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("crate")
    private String crate;
    @JsonProperty("num")
    private String num;
    @JsonProperty("dl_path")
    private String dlPath;
    @JsonProperty("readme_path")
    private String readmePath;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("downloads")
    private Integer downloads;
    @JsonProperty("features")
    private Object features;
    @JsonProperty("yanked")
    private Boolean yanked;
    @JsonProperty("license")
    private String license;
    @JsonProperty("links")
    private Links links;
    @JsonIgnore
    private String readme;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("crate")
    public String getCrate() {
        return crate;
    }

    @JsonProperty("crate")
    public void setCrate(String crate) {
        this.crate = crate;
    }

    @JsonProperty("num")
    public String getNum() {
        return num;
    }

    @JsonProperty("num")
    public void setNum(String num) {
        this.num = num;
    }

    @JsonProperty("dl_path")
    public String getDlPath() {
        return dlPath;
    }

    @JsonProperty("dl_path")
    public void setDlPath(String dlPath) {
        this.dlPath = dlPath;
    }

    @JsonProperty("readme_path")
    public String getReadmePath() {
        return readmePath;
    }

    @JsonProperty("readme_path")
    public void setReadmePath(String readmePath) {
        this.readmePath = readmePath;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("downloads")
    public Integer getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @JsonProperty("features")
    public Object getFeatures() {
        return features;
    }

    @JsonProperty("features")
    public void setFeatures(Object features) {
        this.features = features;
    }

    @JsonProperty("yanked")
    public Boolean getYanked() {
        return yanked;
    }

    @JsonProperty("yanked")
    public void setYanked(Boolean yanked) {
        this.yanked = yanked;
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

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }
}
