package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.adapter.message.AdapterMessage;
import com.remoteboatx.vrgpservice.adapter.message.ByeMessage;
import com.remoteboatx.vrgpservice.adapter.message.ConnectMessage;
import com.remoteboatx.vrgpservice.adapter.message.RequestMessageObserver;
import com.remoteboatx.vrgpservice.adapter.message.handler.AdapterMessageHandler;
import com.remoteboatx.vrgpservice.util.JsonUtil;
import com.remoteboatx.vrgpservice.vrgp.message.RequestMessage;
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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

            if(vesselInformation != null){
                VrgpMessage vesselInfoMessage = new VrgpMessage().withVessel(vesselInformation);
                mocs.connectToMoc(mocUrl);
                mocs.sendMessageToMoc(mocUrl, vesselInfoMessage);
            }
            else{
                //re-request the vessel info
                registerRequestVesselInfoObserver(data -> {
                    vesselInformation = data;
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
        if(adapterMessage.getVessel() != null){
            notifyRequestVesselInfoObserver(adapterMessage.getVessel());
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
            AdapterMessage request = new AdapterMessage().withRequest(new RequestMessage()
                    .withVessel());
            System.out.println(request.toJson());
            session.sendMessage(new TextMessage(request.toJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyRequestVesselInfoObserver(VesselInformation vesselInformation){
        requestVesselInfoMessageObserver.update(vesselInformation);
    }


    public void registerRequestConningMessageHandler( RequestMessageObserver<Conning> observer, RequestMessage requestMessage) {
        requestConningMessageObservers.add(observer);

        if(requestConningMessageObservers.size() == 1){

            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
                try {
                    session.sendMessage(new TextMessage(JsonUtil.toJsonString(requestMessage))); //forwards the request to the adapter
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // send request immediately and then every five seconds.
            }, 0, 5, TimeUnit.SECONDS); //TODO not sure how often it should send the conning
        }

    }

    private void notifyRequestConningMessageHandlers(Conning conning) {
        requestConningMessageObservers.forEach(observer -> observer.update(conning));
    }

}
