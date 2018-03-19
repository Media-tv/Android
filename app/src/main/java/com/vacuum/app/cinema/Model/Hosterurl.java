package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 3/4/2018.
 */

public class Hosterurl {
    @SerializedName("filedataid")
    @Expose
    private String filedataid;
    @SerializedName("part")
    @Expose
    private Integer part;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("filedata")
    private Boolean filedata;

    public String getFiledataid() {
        return filedataid;
    }

    public void setFiledataid(String filedataid) {
        this.filedataid = filedataid;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getFiledata() {
        return filedata;
    }

    public void setFiledata(Boolean filedata) {
        this.filedata = filedata;
    }


}
