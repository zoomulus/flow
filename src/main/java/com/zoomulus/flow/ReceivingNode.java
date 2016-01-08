package com.zoomulus.flow;

import com.zoomulus.flow.message.Message;

public interface ReceivingNode
{
    void processMessage(final Message message);
}
