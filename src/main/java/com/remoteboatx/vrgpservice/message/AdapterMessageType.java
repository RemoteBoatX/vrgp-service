package com.remoteboatx.vrgpservice.message;

import com.remoteboatx.vrgpservice.message.adapter.ConnectMessageHandler;
import com.remoteboatx.vrgpservice.message.handlers.VrgpMessageHandler;

public enum AdapterMessageType {

    CONNECT("connect", new ConnectMessageHandler());


    private final String messageKey;

    private final VrgpMessageHandler messageHandler;

    AdapterMessageType(String messageKey, VrgpMessageHandler messageHandler) {
        this.messageKey = messageKey;
        this.messageHandler = messageHandler;
    }

    /**
     * Checks if a given message key is a message type.
     *
     * @param messageKey the message key from the JSON message.
     * @return boolean of whether the message type was found
     */
    public static boolean contains(String messageKey){
        for (AdapterMessageType messageType : values()) {
            if (messageType.messageKey.equals(messageKey)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Finds the correct message type for a given message key.
     *
     * @param messageKey the message key from the JSON message.
     */
    public static AdapterMessageType getByMessageKey(String messageKey) {
        for (AdapterMessageType messageType : values()) {
            if (messageType.messageKey.equals(messageKey)) {
                return messageType;
            }
        }

        throw new UnsupportedOperationException(String.format("The message \"%s\" is not " +
                "supported by the Vessel in VRGP", messageKey));
    }

    /**
     * Returns the message handler for this message type.
     */
    public VrgpMessageHandler getMessageHandler() {
        return messageHandler;
    }
}
