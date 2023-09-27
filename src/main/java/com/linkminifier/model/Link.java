package com.linkminifier.model;

import org.springframework.data.annotation.Id;

public class Link {

    public Link(String url, int visits) {
        this.url = url;
        this.visits = visits;
    }

    @Id
    private String id;
    private String url;

    private String minifiedUrl;

    private int visits;

    private void setVisits(int visits) {
        this.visits = visits;
    }

    public void addOneVisit (){
        this.setVisits(this.visits = this.visits + 1);
    }

    public void setMinifiedUrl(String minifiedUrl) {
        this.minifiedUrl = minifiedUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getVisits() {
        return visits;
    }
}
