package com.gmaslowski.dam.camel.domain.asset;

public class Asset {

    private String _id;
    private String _rev;
    private String name;
    private String ingestContentUrl;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getRevision() {
        return _rev;
    }

    public void setRevision(String revision) {
        this._rev = revision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngestContentUrl() {
        return ingestContentUrl;
    }

    public void setIngestContentUrl(String ingestContentUrl) {
        this.ingestContentUrl = ingestContentUrl;
    }

    public boolean ingestedWithContent() {
        return getIngestContentUrl() != null;
    }
}
