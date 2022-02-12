package com.remoteboatx.vrgpservice.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VesselTest {


    @Test
    void serializeVesselTest(){

        final Vessel v = new Vessel("999111222");
        v.setName("AboaMare Spirit");
        v.setBreadth(50.0);


        String expected = "{\"mmsi\":\"999111222\",\"name\":\"AboaMare Spirit\",\"breadth\":50.0}";
        try {
            String json = new ObjectMapper().writeValueAsString(v);
            System.out.println(json);
            assertEquals(expected, json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Test
    void deserializeVesselTest(){

        final String json = "{\n" +
                "  \"name\": \"AboaMare Spirit\",\n" +
                "  \"mmsi\": \"999111222\",\n" +
                "  \"call\": \"ABOASP\"," +
                "  \"loa\": 50.0,\n" +
                "  \"breadth\": 10.0,\n" +
                "  \"height\": 20.0,\n" +
                "  \"from_above\": \"M 5 0 L 10 7 L 10 48 L 5 50 L 0 48 L 0 7 Z\"\n}";

        try {
            Vessel v = new ObjectMapper()
                    .readerFor(Vessel.class)
                    .readValue(json);

            assertEquals("AboaMare Spirit", v.getName() );
            assertEquals("999111222", v.getMmsi());
            assertEquals("ABOASP", v.getCall());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}