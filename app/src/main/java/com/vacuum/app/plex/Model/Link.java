package com.vacuum.app.plex.Model;

public class Link {
    String url;
    String id;
    String title;
    String year;


    String name_tv_series,episode_name;
    int id_tvseries_tmdb,season_number,episode_id_tmdb,episode_number;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId_tvseries_tmdb() {
        return id_tvseries_tmdb;
    }

    public void setId_tvseries_tmdb(int id_tvseries_tmdb) {
        this.id_tvseries_tmdb = id_tvseries_tmdb;
    }

    public String getName_tv_series() {
        return name_tv_series;
    }

    public void setName_tv_series(String name_tv_series) {
        this.name_tv_series = name_tv_series;
    }

    public String getEpisode_name() {
        return episode_name;
    }

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getEpisode_id_tmdb() {
        return episode_id_tmdb;
    }

    public void setEpisode_id_tmdb(int episode_id_tmdb) {
        this.episode_id_tmdb = episode_id_tmdb;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }
}
