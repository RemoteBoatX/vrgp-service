package com.remoteboatx.vrgpservice.adapter.message.handler;

import com.remoteboatx.vrgpservice.adapter.message.AdapterMessage;

public interface AdapterMessageHandler<T extends AdapterMessage> {

    void handleMessage(T message);
}
