package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Openload2 {


    @SerializedName("ticket")
    @Expose
    private String ticket;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("url")
    @Expose
    private String url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }


    @SerializedName("captcha_url")
    @Expose
    private String captchaUrl;
    private boolean captchaUrlboolen;

    public String getCaptchaUrl() {
        return captchaUrl;
    }

    public void setCaptchaUrl(String captchaUrl) {
        this.captchaUrl = captchaUrl;
    }


    public boolean getcaptchaUrlboolen() {
        return captchaUrlboolen;
    }

    public void setcaptchaUrlboolen(boolean captchaUrl) {
        this.captchaUrlboolen = captchaUrlboolen;
    }
}
