package com.zoomulus.flow;

import lombok.Data;
import lombok.experimental.Accessors;

import com.zoomulus.flow.message.Message;

@Data
@Accessors(fluent=true)
public class TestMessageBufferExaminer implements Processor
{
    @Accessors(fluent=true, chain=false)
    private Node node;

    private String buffer;

    @Override
    public void handleMessage(Message message)
    {
        if (message instanceof TestMessage)
        {
            buffer = ((TestMessage) message).contents();
        }
        node().respond(message);
    }

    @Override
    public void handleResponse(Message response)
    {
    }
}