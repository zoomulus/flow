package com.zoomulus.flow;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.zoomulus.flow.message.Message;

@Getter
@Accessors(fluent=true)
public class TestSender implements Sender
{
    private Message response;
    private Message message;

    @Override
    public void onReplyReceived(Message response)
    {
        this.response = response;
    }

    @Override
    public void onMessageRecevied(Message message)
    {
        this.message = message;
    }
}
