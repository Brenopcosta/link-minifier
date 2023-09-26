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
        this.setVisits(this.visits++);
    }

    public void setMinifiedUrl(String minifiedUrl) {
        this.minifiedUrl = minifiedUrl;
    }
}
