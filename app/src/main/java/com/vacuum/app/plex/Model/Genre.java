package com.vacuum.app.plex.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 2/23/2018.
 */

public class Genre {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

