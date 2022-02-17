package com.remoteboatx.vrgpservice.adapter.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ByeMessage implements AdapterSingleMessage {

    @JsonProperty(required = true)
    private String url;

    public String getUrl() {
        return url;
    }
}
