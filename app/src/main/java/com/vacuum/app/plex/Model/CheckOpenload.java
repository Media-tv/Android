package com.vacuum.app.plex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOpenload {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("remoteurl")
    @Expose
    private String remoteurl;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bytes_loaded")
    @Expose
    private Object bytesLoaded;
    @SerializedName("bytes_total")
    @Expose
    private Object bytesTotal;
    @SerializedName("folderid")
    @Expose
    private String folderid;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("extid")
    @Expose
    private String extid;
    @SerializedName("url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteurl() {
        return remoteurl;
    }

    public void setRemoteurl(String remoteurl) {
        this.remoteurl = remoteurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getBytesLoaded() {
        return bytesLoaded;
    }

    public void setBytesLoaded(Object bytesLoaded) {
        this.bytesLoaded = bytesLoaded;
    }

    public Object getBytesTotal() {
        return bytesTotal;
    }

    public void setBytesTotal(Object bytesTotal) {
        this.bytesTotal = bytesTotal;
    }

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
