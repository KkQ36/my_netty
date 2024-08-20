package com.ryan.aio.server;

import com.ryan.aio.ChannelInitializer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author kq
 * 2024-08-20 09:34
 **/
public class AioServerChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(AsynchronousSocketChannel channel) {
        channel.read(ByteBuffer.allocate(1024), 10, TimeUnit.SECONDS, null,
                new AioServerHandler(channel, Charset.forName("GBK")));
    }

}
