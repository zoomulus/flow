package com.zoomulus.flow;

import com.zoomulus.flow.message.Message;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent=true)
public class TestMessageTypeExaminer implements Processor
{
    @Accessors(fluent=true, chain=false)
    private Node node;
    private int type;

    @Override
    public void handleMessage(Message message)
    {
        if (message instanceof TestMessage)
        {
            type = ((TestMessage) message).type();
        }
    }

    @Override
    public void handleResponse(Message response)
    {
    }
}
