package com.zoomulus.flow;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.zoomulus.flow.message.Message;

/**
 * A Sink sends a response to some other source besides
 * a Node.  A Flow ends with a Sink.
 */
@Data
@Accessors(fluent=true)
public class Sink implements ReceivingNode
{
    private final String name = "sink";
    private final Sender sender;
    @Setter(AccessLevel.PACKAGE)
    private Flow flow;

    private Sink(final Sender replier)
    {
        this.sender = replier;
    }

    public void processResponse(final Message response)
    {
        sender.onReplyReceived(response);
    }

    @Override
    public void processMessage(Message message)
    {
        sender.onMessageRecevied(message);
    }

    public static SinkBuilder builder()
    {
        return new SinkBuilder();
    }

    public static class SinkBuilder
    {
        private Sender sender;

        public SinkBuilder sender(final Sender sender)
        {
            this.sender = sender;
            return this;
        }

        public Sink build()
        {
            return new Sink(sender);
        }
    }
}    