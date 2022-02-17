package com.remoteboatx.vrgpservice.adapter.message.handler;

import com.remoteboatx.vrgpservice.adapter.message.AdapterSingleMessage;

@FunctionalInterface
public interface AdapterMessageHandler<T extends AdapterSingleMessage> {

    void handleMessage(T message);
}
