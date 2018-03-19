package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 3/5/2018.
 */

public  class ThumbnailResult {

    @SerializedName("thumbnail_base64")
    @Expose
    private String thumbnailBase64;

    public String getThumbnailBase64() {
        return thumbnailBase64;
    }

    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }

}
