package com.vacuum.app.cinema.Model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Converter;

/**
 * Created by Home on 3/23/2018.
 */

public class Openload {


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


    @SerializedName("id")
    @Expose
    private String id;





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

    public String getCaptchaUrl() {
        return captchaUrl;
    }

    public void setCaptchaUrl(String captchaUrl) {
        this.captchaUrl = captchaUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
