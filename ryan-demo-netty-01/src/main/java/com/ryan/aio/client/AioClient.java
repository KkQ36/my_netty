package com.ryan.aio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

/**
 * @author kq
 * 2024-08-20 09:33
 **/
public class AioClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        Future<Void> future = socketChannel.connect(new InetSocketAddress("127.0.0.1", 7397));
        System.out.println("aio client start done.");
        future.get();
        socketChannel.read(ByteBuffer.allocate(1024), null,
                new AioClientHandler(socketChannel, Charset.forName("GBK")));
        Thread.sleep(100000);
    }

}
