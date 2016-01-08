package com.zoomulus.flow;

import com.zoomulus.flow.message.Message;

public interface Sender
{
    void onMessageRecevied(final Message message);
    void onReplyReceived(final Message response);
}
