package com.vacuum.app.plex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Home on 3/23/2018.
 */

public class OpenloadResult {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("result")
    @Expose
    private Openload openload;



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Openload getOpenload() {
        return openload;
    }

    public void setOpenload(Openload openload) {
        this.openload = openload;
    }
}
