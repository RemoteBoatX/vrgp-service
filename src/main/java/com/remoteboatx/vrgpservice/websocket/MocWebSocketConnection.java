package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.remoteboatx.vrgpservice.util.JsonUtil;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.vrgp.message.handler.ByeMessageHandler;
import com.remoteboatx.vrgpservice.vrgp.message.handler.LatencyMessageHandler;
import com.remoteboatx.vrgpservice.vrgp.message.handler.VrgpSingleMessageHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MocWebSocketConnection extends TextWebSocketHandler implements MocWebSocket {

    private final List<VrgpSingleMessageHandler<?>> singleMessageHandlers = new ArrayList<>() {{
        add(new LatencyMessageHandler());
        add(new ByeMessageHandler());
    }};



    private WebSocketSession session;

    public MocWebSocketConnection(String url) {
        try {
            session = new StandardWebSocketClient().doHandshake(this, new WebSocketHttpHeaders(), URI.create(url))
                    .get(3, TimeUnit.MINUTES);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            //TODO handle if connection cannot be established
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("moc disconnected");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("moc connected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(message.getPayload());

        if(payload.has("request")){
            JsonNode request = payload.get("request");
            System.out.println(request);

            ArrayList<JsonNode> nodes = new ArrayList<>();



            request.fieldNames().forEachRemaining(key -> {
                ObjectNode parentNode = mapper.createObjectNode();
                nodes.add(parentNode.set(key, request.get(key)));
            });

            System.out.println(nodes.size());
            registerRequestObserver(nodes);

        }else{
            for (VrgpSingleMessageHandler<?> singleMessageHandler : singleMessageHandlers) {
                singleMessageHandler.handleMessage(VrgpMessage.fromJson(message.getPayload()), this);
            }
        }


    }

    @Override
    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            // TODO: Handle exception.
            e.printStackTrace();
        }
    }

    private void registerRequestObserver(List<JsonNode> values){
        for (JsonNode value: values) {

            System.out.println(value);
            if(value.has("conning")){
                System.out.println("conning request received");
                AdapterWebSocketMessageHandler.getInstance()
                        .registerRequestConningMessageHandler(data -> {
                            sendMessage(JsonUtil.toJsonString(data));
                        }, value.textValue());
            }

        }
    }

    @Override
    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            // TODO: Handle exception.
            e.printStackTrace();
        }
    }
}