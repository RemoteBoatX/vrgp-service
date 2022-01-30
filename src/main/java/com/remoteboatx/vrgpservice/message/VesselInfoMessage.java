package com.remoteboatx.vrgpservice.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remoteboatx.vrgpservice.Vessel;


public class VesselInfoMessage {

    private final Vessel vessel;

    @JsonCreator
    public VesselInfoMessage(@JsonProperty("vessel") Vessel vessel) {
        this.vessel = vessel;
    }

    public Vessel getVessel() {
        return vessel;
    }
}
