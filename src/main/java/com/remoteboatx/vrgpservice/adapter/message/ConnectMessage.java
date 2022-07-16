package com.remoteboatx.vrgpservice.adapter.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectMessage implements AdapterSingleMessage {

    @JsonProperty(required = true)
    private String url;

    public String getUrl() {
        return url;
    }
}
