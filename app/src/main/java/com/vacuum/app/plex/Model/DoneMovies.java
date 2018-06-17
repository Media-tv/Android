package com.vacuum.app.plex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoneMovies {


    @SerializedName("Movie")
    @Expose
    private List<Movie> movie = null;

    public List<Movie> getMovie() {
        return movie;
    }

    public void setMovie(List<Movie> movie) {
        this.movie = movie;
    }

}