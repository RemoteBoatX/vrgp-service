package com.remoteboatx.vrgpservice.vrgp.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model of the VRGP vessel message that includes mostly static information about the vessel.
 */
public class VesselInformation implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private String mmsi; //Maritime Mobile Service Identifier[MMSI]

    private String call; //radio call sign
    private String name; //vessel name
    private Double loa; //overall length of the vessel, in meters, rounded to one decimal
    private Double breadth; //maximum overall width of the vessel, in meters, rounded to one decimal
    private Double height; //overall height of the vessel, in meters, rounded to one decimal.
    private Double draft;
    @JsonProperty("from_above")
    private String fromAbove; //SVG drawing <path> of the outline of the vessel

    @JsonProperty(required = true)
    private Streams streams; //available streams


    public String getMmsi() {
        return mmsi;
    }

    public String getCall() {
        return call;
    }

    public String getName() {
        return name;
    }

    public Double getLoa() {
        return loa;
    }

    public Double getBreadth() {
        return breadth;
    }

    public Double getHeight() {
        return height;
    }

    public Double getDraft() {
        return draft;
    }

    public String getFromAbove() {
        return fromAbove;
    }

    /**
     * Returns the available streams of the vessel defined in the vessel message.
     */
    public Streams getStreams() {
        return streams;
    }
}
