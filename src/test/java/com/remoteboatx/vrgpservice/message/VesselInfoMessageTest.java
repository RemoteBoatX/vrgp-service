package com.remoteboatx.vrgpservice.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.Vessel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class VesselInfoMessageTest {

    @Test
    void serializeVesselInfoMessageTest(){

        final VesselInfoMessage m = new VesselInfoMessage(new Vessel("999111225"));

        String expected = "{\"vessel\":{\"mmsi\":\"999111225\"}}";
        try {
            String json = new ObjectMapper().writeValueAsString(m);
            System.out.println(json);
            assertEquals(expected, json);

        } catch (JsonProcessingException e) {
            fail(e);
        }

    }

    @Test
    void deserializeVesselInfoMessageTest(){
        final String json = "{\"vessel\":{\n" +
                "  \"name\": \"AboaMare Spirit\",\n" +
                "  \"mmsi\": \"999111222\",\n" +
                "  \"call\": \"ABOASP\"," +
                "  \"loa\": 50.0,\n" +
                "  \"breadth\": 10.0,\n" +
                "  \"height\": 20.0,\n" +
                "  \"from_above\": \"M 5 0 L 10 7 L 10 48 L 5 50 L 0 48 L 0 7 Z\"\n}}";

        try {
            VesselInfoMessage m = new ObjectMapper()
                    .readerFor(VesselInfoMessage.class)
                    .readValue(json);

            assertEquals("AboaMare Spirit", m.getVessel().getName());
            assertEquals("999111222", m.getVessel().getMmsi());
            assertEquals("ABOASP", m.getVessel().getCall());

        } catch (JsonProcessingException e) {
            fail(e);
        }

    }



}