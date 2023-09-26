package com.linkminifier.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkDTO {
    @JsonCreator
    public LinkDTO(@JsonProperty("url") String url) {
        this.url = url;
    }

    String url;

    public String getUrl() {
        return url;
    }
}
