package com.ryan.c2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kq
 * 2024-09-27 11:15
 **/
public class NoBlockingServer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(8080));
            List<SocketChannel> channels = new ArrayList<>();
            while (true) {
                SocketChannel accept = ssc.accept(); // 此时就变成了非阻塞的模式
                if (accept != null) {
                    accept.configureBlocking(false);
                    channels.add(accept);
                }
                for (SocketChannel channel : channels) {
                    channel.read(buffer); // 此时就变成了非阻塞的模式
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.print((char) buffer.get());
                    }
                    buffer.clear();
                    System.out.println("\n------");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
