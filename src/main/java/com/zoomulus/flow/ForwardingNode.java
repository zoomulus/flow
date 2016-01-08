package com.zoomulus.flow;

import java.util.List;

public interface ForwardingNode extends ReceivingNode
{
    String name();
    List<String> sendTargets();
}
