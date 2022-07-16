package com.remoteboatx.vrgpservice.adapter.message;

import com.remoteboatx.vrgpservice.vrgp.message.VrgpSingleMessage;

public interface RequestMessageObserver<T extends VrgpSingleMessage> {

    void update(T data);
}
