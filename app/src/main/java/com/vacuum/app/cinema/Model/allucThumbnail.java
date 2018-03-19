package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 3/5/2018.
 */

public class allucThumbnail {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ThumbnailResult result;
    @SerializedName("resultcount")
    @Expose
    private Integer resultcount;
    @SerializedName("fetchedtoday")
    @Expose
    private Integer fetchedtoday;

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

    public ThumbnailResult getResult() {
        return result;
    }

    public void setResult(ThumbnailResult result) {
        this.result = result;
    }

    public Integer getResultcount() {
        return resultcount;
    }

    public void setResultcount(Integer resultcount) {
        this.resultcount = resultcount;
    }

    public Integer getFetchedtoday() {
        return fetchedtoday;
    }

    public void setFetchedtoday(Integer fetchedtoday) {
        this.fetchedtoday = fetchedtoday;
    }


}
