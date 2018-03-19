package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 3/4/2018.
 */

public class allucResults {


    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private List<Alluc> alluc ;
    @SerializedName("resultcount")
    private Integer resultcount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Alluc> getAlluc() {
        return alluc;
    }

    public void setAlluc(List<Alluc> alluc) {
        this.alluc = alluc;
    }

    public Integer getResultcount() {
        return resultcount;
    }

    public void setResultcount(Integer resultcount) {
        this.resultcount = resultcount;
    }

}
