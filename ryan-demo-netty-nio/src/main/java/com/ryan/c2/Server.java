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
 * 2024-09-27 10:58
 * 演示 NIO 阻塞模式的 Server
 **/
public class Server {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8080));
            List<SocketChannel> channels = new ArrayList<>();
            while (true) {
                System.out.println("waiting");
                SocketChannel accept = ssc.accept(); // 阻塞，等待连接
                System.out.println("connected");
                channels.add(accept);
                for (SocketChannel channel : channels) {
                    channel.read(buffer); // 阻塞，等待读取数据
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
