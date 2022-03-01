package com.remoteboatx.vrgpservice.vrgp.message;

/**
 * JSON model of the VRGP streams message that includes the available information streams and additional information
 * about the streams.
 */
public class Streams {

    // TODO: Refactor to actual datatypes.
    private Boolean conning;

    private Boolean camera;

    public Boolean getConning() {
        return conning;
    }

    public Boolean getCamera() {
        return camera;
    }
}
