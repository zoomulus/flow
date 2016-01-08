package com.zoomulus.flow.message;

import java.nio.ByteBuffer;

public interface Message
{
    ByteBuffer buffer();
    String contentType();
}
