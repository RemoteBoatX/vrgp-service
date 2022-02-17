package com.remoteboatx.vrgpservice.vrgp.message.handler;

import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpSingleMessage;
import com.remoteboatx.vrgpservice.websocket.MocWebSocket;
import org.springframework.lang.NonNull;

import java.util.function.Function;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
 *
 * @param <T> the specific VRGP message type this handler handles.
 */
public interface VrgpSingleMessageHandler<T extends VrgpSingleMessage> {

    /**
     * Handles a VRGP message of a specific type.
     *
     * @param message the message content.
     */
    @NonNull
    void handleMessage(@NonNull T message, MocWebSocket mocWebSocket);

    /**
     * Handles a specific message key of a VRGP message.
     *
     * @param vrgpMessage the message.
     */
    @NonNull
    default void handleMessage(VrgpMessage vrgpMessage, MocWebSocket mocWebSocket) {
        final T singleMessage = getSingleMessage().apply(vrgpMessage);
        if (singleMessage == null) {
            return;
        }
        handleMessage(singleMessage, mocWebSocket);
    }

    /**
     * Returns a function that extracts the single VRGP message from a {@link VrgpMessage}.
     * <br><br>
     * <b>Caution</b>: This is supposed to simply return a getter of VrgpMessage. The actual VrgpMessage that can be
     * accessed in the returned function should not be used for anything else.
     */
    Function<VrgpMessage, T> getSingleMessage();
}
