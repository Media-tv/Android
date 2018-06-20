package com.vacuum.app.plex.Model;

public class Link {
    String url;
    String id;
    String title;
    String year;
    String Season_number;
    String episodes_number;

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

    public String getSeason_number() {
        return Season_number;
    }

    public void setSeason_number(String season_number) {
        Season_number = season_number;
    }

    public String getEpisodes_number() {
        return episodes_number;
    }

    public void setEpisodes_number(String episodes_number) {
        this.episodes_number = episodes_number;
    }
}
