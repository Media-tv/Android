package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 2/20/2018.
 */

public class MoviesResponse {

    @SerializedName("results")
    private List<Movie> Movie = null;


    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    public List<Movie> getResults() {
        return Movie;
    }

    public void setResults(List<Movie> results) {
        this.Movie = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}