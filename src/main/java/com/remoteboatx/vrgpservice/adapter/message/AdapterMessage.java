package com.remoteboatx.vrgpservice.adapter.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remoteboatx.vrgpservice.vrgp.message.VesselInformation;
import com.remoteboatx.vrgpservice.vrgp.message.stream.Conning;

public class AdapterMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ConnectMessage connect;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ByeMessage bye;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vessel")
    private VesselInformation vessel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conning conning;

    public ConnectMessage getConnect() {
        return connect;
    }

    public ByeMessage getBye() {
        return bye;
    }

    public VesselInformation getVesselInformation() {
        return vessel;
    }

    public Conning getConning() {
        return conning;
    }
}
