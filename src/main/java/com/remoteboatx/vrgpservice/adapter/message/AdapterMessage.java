package com.remoteboatx.vrgpservice.adapter.message;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AdapterMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ConnectMessage connect;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ByeMessage bye;

    public ConnectMessage getConnect() {
        return connect;
    }

    public ByeMessage getBye() {
        return bye;
    }
}
