package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 3/23/2018.
 */

public class Openload {

    @SerializedName("ticket")
    @Expose
    private String ticket;
    @SerializedName("captcha_url")
    @Expose
    private String captchaUrl;
    @SerializedName("captcha_w")
    @Expose
    private Integer captchaW;
    @SerializedName("captcha_h")
    @Expose
    private Integer captchaH;
    @SerializedName("wait_time")
    @Expose
    private Integer waitTime;
    @SerializedName("valid_until")
    @Expose
    private String validUntil;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("sha1")
    @Expose
    private String sha1;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("upload_at")
    @Expose
    private String uploadAt;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("token")
    @Expose
    private String token;

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

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(String uploadAt) {
        this.uploadAt = uploadAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCaptchaUrl() {
        return captchaUrl;
    }

    public void setCaptchaUrl(String captchaUrl) {
        this.captchaUrl = captchaUrl;
    }

    public Integer getCaptchaW() {
        return captchaW;
    }

    public void setCaptchaW(Integer captchaW) {
        this.captchaW = captchaW;
    }

    public Integer getCaptchaH() {
        return captchaH;
    }

    public void setCaptchaH(Integer captchaH) {
        this.captchaH = captchaH;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }


}
