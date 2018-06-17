package com.vacuum.app.plex.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 3/1/2018.
 */

public class Credits {

    @SerializedName("id")
    private Integer id;
    @SerializedName("cast")
    private List<Cast> cast = null;
    @SerializedName("crew")
    private List<Crew> crew = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

}
