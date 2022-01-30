package com.remoteboatx.vrgpservice;


import org.springframework.web.socket.WebSocketSession;


public class VrgpState {

    private static final VrgpState INSTANCE = new VrgpState();
    private Vessel vessel;
    private WebSocketSession adapterSession;


    public VrgpState() {
    }

    public WebSocketSession getAdapterSession() {
        return adapterSession;
    }

    public void setAdapterSession(WebSocketSession adapterSession) {
        this.adapterSession = adapterSession;
    }

    public void setVessel(Vessel info){
        this.vessel = info;
    }
    public Vessel getVessel() {
        return vessel;
    }

    public static VrgpState getInstance(){

        return INSTANCE;
    }
}
