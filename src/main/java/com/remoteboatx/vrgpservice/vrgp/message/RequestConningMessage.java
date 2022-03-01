package com.remoteboatx.vrgpservice.vrgp.message;

import java.util.List;

public class RequestConningMessage implements VrgpSingleMessage{

    private Boolean priority;
    private List<String> format;

    public Boolean getPriority() {
        return priority;
    }

    public List<String> getFormat() {
        return format;
    }
}
