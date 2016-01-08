package com.zoomulus.flow;

import com.zoomulus.flow.message.Message;

/**
 * A Processor is used by a Node to process Messages.  It:
 *   - Performs processing on a received Message in handleMessage.
 *   - Performs processing on a response Message in handleResponse.
 * Processors only handle message data; the Flow handles moving
 * messages and responses through itself.
 */
public interface Processor
{
    void node(final Node node);
    Node node();
    void handleMessage(final Message message);
    void handleResponse(final Message response);
}
