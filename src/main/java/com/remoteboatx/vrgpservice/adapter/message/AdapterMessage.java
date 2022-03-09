package com.remoteboatx.vrgpservice.adapter.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.remoteboatx.vrgpservice.vrgp.message.RequestMessage;
import com.remoteboatx.vrgpservice.vrgp.message.Status;
import com.remoteboatx.vrgpservice.vrgp.message.VesselInformation;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.vrgp.message.stream.Conning;
import com.remoteboatx.vrgpservice.vrgp.message.util.JsonUtil;
import org.springframework.lang.NonNull;

public class AdapterMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ConnectMessage connect;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ByeMessage bye;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VesselInformation vessel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conning conning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RequestMessage request;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status emergency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status alarm;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status warning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status caution;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status info;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status debug;

    public Status getEmergency() {
        return emergency;
    }

    public Status getAlarm() {
        return alarm;
    }

    public Status getWarning() {
        return warning;
    }

    public Status getCaution() {
        return caution;
    }

    public Status getInfo() {
        return info;
    }

    public Status getDebug() {
        return debug;
    }

    public ConnectMessage getConnect() {
        return connect;
    }

    public ByeMessage getBye() {
        return bye;
    }

    public VesselInformation getVessel() {
        return vessel;
    }

    public Conning getConning() {
        return conning;
    }

    public RequestMessage getRequest() {
        return request;
    }

    public AdapterMessage withRequest(@NonNull RequestMessage requestMessage){
        request = requestMessage;
        return this;
    }

    /**
     * Deserializes a JSON string to a AdapterMessage object.
     */
    @NonNull
    public static VrgpMessage fromJson(@NonNull String json) {
        return JsonUtil.fromJson(json, VrgpMessage.class);
    }

    /**
     * Serializes this AdapterMessage to a JSON string.
     */
    @NonNull
    public String toJson() {
        return JsonUtil.toJsonString(this);
    }

}
