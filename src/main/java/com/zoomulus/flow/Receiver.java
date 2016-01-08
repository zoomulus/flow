package com.zoomulus.flow;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.zoomulus.flow.message.Message;

public abstract class Receiver
{
    @Setter(AccessLevel.PACKAGE)
    @Accessors(fluent=true)
    Source source;

    protected void onMessageReceived(final Message message)
    {
        source.processMessage(message);
    }
}
