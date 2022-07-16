package com.remoteboatx.vrgpservice.vrgp.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

public class RequestMessage implements VrgpSingleMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode conning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean vessel;

    public JsonNode getConning() {
        return conning;
    }

    public Boolean getVessel() {
        return vessel;
    }

    public RequestMessage withConning(JsonNode conningRequest){
        conning = conningRequest;
        return this;
    }

    public RequestMessage withVessel(){
        vessel = true;
        return this;
    }

}
