package com.zoomulus.flow;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.google.common.collect.Lists;
import com.zoomulus.flow.message.Message;

/**
 * A Node represents a step in a Flow where message
 * or response processing can occur.
 * A Node does not perform any message processing;
 * instead, that is done by a Processor that was supplied
 * to the Node at construction time.
 */
@Data
@Accessors(fluent=true)
public class Node implements ForwardingNode, ReplyingNode
{
    private final String name;
    private final Optional<Processor> processor;
    private final Optional<String> replyTarget;
    private final List<String> sendTargets;
    private final Optional<Predicate<Message>> relayCondition;

    Node(@NonNull final String name,
            @NonNull final Optional<Processor> processor,
            @NonNull final Optional<String> replyTarget,
            @NonNull final List<String> sendTargets,
            @NonNull final Optional<Predicate<Message>> relayCondition)
    {
        this.name = name;
        this.processor = processor;
        this.replyTarget = replyTarget;
        this.sendTargets = sendTargets;
        this.relayCondition = relayCondition;

        if (this.processor.isPresent())
        {
            this.processor.get().node(this);
        }
    }

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Flow flow;

    @Override
    public boolean hasReplyTarget()
    {
        return replyTarget.isPresent();
    }

    protected void relay(@NonNull final Message message)
    {
        flow().relay(message, name());
    }

    protected void respond(@NonNull final Message message)
    {
        flow().respond(message, name());
    }

    @Override
    public void processMessage(Message message)
    {
        if (processor.isPresent())
        {
            processor.get().handleMessage(message);
        }
    }

    @Override
    public void processResponse(Message response)
    {
        if (processor.isPresent())
        {
            processor.get().handleResponse(response);
        }        
    }

    public static NodeBuilder builder()
    {
        return new NodeBuilder();
    }

    public static class NodeBuilder
    {
        private String name;
        private Optional<Processor> processor = Optional.<Processor> empty();
        private Optional<String> replyTarget = Optional.<String> empty();
        private List<String> sendTargets = Lists.newArrayList();
        private Optional<Predicate<Message>> relayCondition = Optional.<Predicate<Message>> empty();

        public NodeBuilder name(@NonNull final String name)
        {
            this.name = name;
            return this;
        }

        public NodeBuilder processor(@NonNull final Processor processor)
        {
            this.processor = Optional.of(processor);
            return this;
        }

        public NodeBuilder repliesTo(@NonNull final String replyTarget)
        {
            this.replyTarget = Optional.of(replyTarget);
            return this;
        }

        public NodeBuilder sendsTo(@NonNull final String sendTarget)
        {
            sendTargets.add(sendTarget);
            return this;
        }

        public NodeBuilder sendsTo(@NonNull final List<String> sendTargets)
        {
            this.sendTargets.addAll(sendTargets);
            return this;
        }

        public NodeBuilder relayCondition(@NonNull final Predicate<Message> relayCondition)
        {
            this.relayCondition = Optional.of(relayCondition);
            return this;
        }

        public Node build()
        {
            return new Node(name, processor, replyTarget, sendTargets, relayCondition);
        }
    }
}
            
