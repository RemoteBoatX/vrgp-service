package com.remoteboatx.vrgpservice.adapter.message.handler;

import com.remoteboatx.vrgpservice.vrgp.message.VrgpSingleMessage;

@FunctionalInterface
public interface VrgpMessageHandler<T extends VrgpSingleMessage> {

    void handle(T message);

}
