package com.remoteboatx.vrgpservice.models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Vessel model
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "vessel")
public class Vessel {

    private String mmsi; //Maritime Mobile Service Identifier[MMSI]
    private String call; //radio call sign
    private String name; //vessel name
    private Double loa; //overall length of the vessel, in meters, rounded to one decimal
    private Double breadth; //maximum overall width of the vessel, in meters, rounded to one decimal
    private Double height; //overall height of the vessel, in meters, rounded to one decimal.
    private Integer draft;
    private String fromAbove; //SVG drawing <path> of the outline of the vessel


    @JsonCreator
    public Vessel(@JsonProperty("mmsi") String mmsi) {
        this.mmsi = mmsi;
    }

    @JsonSetter("call")
    public void setCall(String call) {
        this.call = call;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("loa")
    public void setLoa(Double loa) {
        this.loa = loa;
    }

    @JsonSetter("breadth")
    public void setBreadth(Double breadth) {
        this.breadth = breadth;
    }

    @JsonSetter("height")
    public void setHeight(Double height) {
        this.height = height;
    }

    @JsonSetter("draft")
    public void setDraft(Integer draft) {
        this.draft = draft;
    }

    @JsonSetter("from_above")
    public void setFromAbove(String fromAbove) {
        this.fromAbove = fromAbove;
    }


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

    public Integer getDraft() {
        return draft;
    }

    public String getFromAbove() {
        return fromAbove;
    }

    //test harness
    public static void main(String[] args) {

        String jsonVessel = "{\n" +
                "  \"name\": \"AboaMare Spirit\",\n" +
                "  \"mmsi\": \"999111222\",\n" +
                //"  \"call\": \"ABOASP\"," +
                "  \"loa\": 50.0,\n" +
                "  \"breadth\": 10.0,\n" +
                "  \"height\": 20.0,\n" +
                "  \"from_above\": \"M 5 0 L 10 7 L 10 48 L 5 50 L 0 48 L 0 7 Z\"\n}";

        System.out.println(jsonVessel);

        try {
            Vessel v = new ObjectMapper()
                    .readerFor(Vessel.class)
                    .readValue(jsonVessel);

            System.out.println(v.getLoa());
            System.out.println(v.getBreadth());
            System.out.println(v.getFromAbove());


//            VesselInfoMessage infoMessage = new VesselInfoMessage(v);
//
//            try {
//                String json = new ObjectMapper().writeValueAsString(infoMessage);
//                System.out.println(json);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
