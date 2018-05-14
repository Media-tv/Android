package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorMovies {

    @SerializedName("cast")
    @Expose
    private List<Movie> cast = null;

    public List<Movie> getCast() {
        return cast;
    }

    public void setCast(List<Movie> cast) {
        this.cast = cast;
    }

}
