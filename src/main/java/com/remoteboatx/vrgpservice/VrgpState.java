package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.models.Vessel;
import com.remoteboatx.vrgpservice.websocket.VrgpWebsocketMessageHandler;

public class VrgpState {

    private static VrgpState instance = null;

    private VrgpWebsocketMessageHandler vrgpWebsocketMessageHandler;
    private String adapterSessionId;
    private Vessel vessel;

    private VrgpState() {
        vrgpWebsocketMessageHandler = new VrgpWebsocketMessageHandler();
    }

    public static VrgpState getInstance() {
        if(instance == null)
            instance = new VrgpState();
        return instance;
    }

    public void setAdapterSessionId(String adapterSessionId) {
        this.adapterSessionId = adapterSessionId;
    }

    public String getAdapterSessionId() {
        return adapterSessionId;
    }

    public VrgpWebsocketMessageHandler getVrgpWebsocketMessageHandler() {
        return vrgpWebsocketMessageHandler;
    }


    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public Vessel getVessel() {
        return vessel;
    }
}
