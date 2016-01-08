package com.zoomulus.flow;

import java.util.Optional;

import com.zoomulus.flow.message.Message;

public interface ReplyingNode
{
    String name();
    boolean hasReplyTarget();
    Optional<String> replyTarget();
    void processResponse(final Message response);
}
