package com.sh3h.datautil.data.entity;


public class DUFile extends DURequest {
    private String url;
    private String path;

    public DUFile(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
