package com.bmco.cratesiounofficial.models;

/**
 * Created by Bertus on 29-7-2017.
 */

public class Alert {
    private boolean version;
    private boolean downloads;
    private Crate crate;

    public Alert(boolean version, boolean downloads, Crate crate) {
        this.version = version;
        this.downloads = downloads;
        this.crate = crate;
    }

    public void setCrate(Crate crate) {
        this.crate = crate;
    }

    public Crate getCrate() {
        return crate;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isDownloads() {
        return downloads;
    }

    public void setDownloads(boolean downloads) {
        this.downloads = downloads;
    }
}
