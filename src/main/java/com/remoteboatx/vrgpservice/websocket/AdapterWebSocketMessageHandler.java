package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.adapter.message.AdapterMessage;
import com.remoteboatx.vrgpservice.adapter.message.ByeMessage;
import com.remoteboatx.vrgpservice.adapter.message.ConnectMessage;
import com.remoteboatx.vrgpservice.adapter.message.RequestMessageObserver;
import com.remoteboatx.vrgpservice.adapter.message.handler.AdapterMessageHandler;
import com.remoteboatx.vrgpservice.util.JsonUtil;
import com.remoteboatx.vrgpservice.vrgp.message.VesselInformation;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.vrgp.message.stream.Conning;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdapterWebSocketMessageHandler extends TextWebSocketHandler {

    private static AdapterWebSocketMessageHandler instance;

    private final MocRepository mocs = new MocRepository();

    private final List<AdapterMessageHandler<ConnectMessage>> connectMessageHandlers = new ArrayList<>();

    private final List<AdapterMessageHandler<ByeMessage>> byeMessageHandlers = new ArrayList<>();

    private RequestMessageObserver<VesselInformation> requestVesselInfoMessageObserver;
    private VesselInformation vesselInformation;

    private List<RequestMessageObserver<Conning>> requestConningMessageObservers = new ArrayList<>();

    private WebSocketSession session;

    private AdapterWebSocketMessageHandler() {
        registerConnectMessageHandler(connectMessage -> {
            String mocUrl = connectMessage.getUrl();

            if(vesselInformation != null) {
                VrgpMessage vesselInfoMessage = new VrgpMessage().withVessel(vesselInformation);
                mocs.connectToMoc(mocUrl);
//                mocs.sendMessageToMoc(mocUrl, vesselInfoMessage); //not handled fully on moc side, causes errors
            }
            else{
                //re-request the vessel info
                registerRequestVesselInfoObserver(data -> {

                    vesselInformation = data;
                    System.out.println(JsonUtil.toJsonString(data));
                });
                //TODO handle, maybe send a error message to the adapter?
            }
        });

        registerByeMessageHandler(byeMessage -> mocs.sendMessageToMoc(byeMessage.getUrl(), new VrgpMessage().withBye()));

    }

    public static AdapterWebSocketMessageHandler getInstance() {
        if (instance == null) {
            instance = new AdapterWebSocketMessageHandler();
        }
        return instance;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        //request info
        registerRequestVesselInfoObserver(data -> {
            vesselInformation = data;
            System.out.println(JsonUtil.toJsonString(data));
        });

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        this.session = null;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // TODO: Implement.
        final AdapterMessage adapterMessage = JsonUtil.fromJson(message.getPayload(), AdapterMessage.class);
        if (adapterMessage.getConnect() != null) {
            notifyConnectMessageHandlers(adapterMessage.getConnect());
        }
        if (adapterMessage.getBye() != null) {
            notifyByeMessageHandlers(adapterMessage.getBye());
        }
        if(adapterMessage.getVesselInformation() != null){
            notifyRequestVesselInfoObserver(adapterMessage.getVesselInformation());
        }

        if(adapterMessage.getConning() != null){
            notifyRequestConningMessageHandlers(adapterMessage.getConning());
        }
    }

    // Connect

    public void registerConnectMessageHandler(AdapterMessageHandler<ConnectMessage> connectMessageHandler) {
        connectMessageHandlers.add(connectMessageHandler);
    }

    private void notifyConnectMessageHandlers(ConnectMessage connectMessage) {
        connectMessageHandlers.forEach(connectMessageHandler -> connectMessageHandler.handleMessage(connectMessage));
    }

    // Bye

    public void registerByeMessageHandler(AdapterMessageHandler<ByeMessage> byeMessageHandler) {
        byeMessageHandlers.add(byeMessageHandler);
    }

    private void notifyByeMessageHandlers(ByeMessage byeMessage) {
        byeMessageHandlers.forEach(byeMessageHandler -> byeMessageHandler.handleMessage(byeMessage));
    }


    //vessel info
    private void registerRequestVesselInfoObserver(RequestMessageObserver<VesselInformation> observer){
        requestVesselInfoMessageObserver = observer;
        try {
            //TODO create request format
            session.sendMessage(new TextMessage("{\"request\":\"vessel information\"}"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyRequestVesselInfoObserver(VesselInformation vesselInformation){
        requestVesselInfoMessageObserver.update(vesselInformation);
    }


    public void registerRequestConningMessageHandler( RequestMessageObserver<Conning> observer, String message) {
        requestConningMessageObservers.add(observer);

        try {
            session.sendMessage(new TextMessage(message)); //forwards the request to the adapter
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void notifyRequestConningMessageHandlers(Conning conning) {
        requestConningMessageObservers.forEach(observer -> observer.update(conning));
    }

}
