package com.ryan.c2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author kq
 * 2024-09-27 11:04
 **/
public class Client {

    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()){
            sc.connect(new InetSocketAddress("localhost", 8080));
            sc.write(StandardCharsets.UTF_8.encode("你好，我是俄罗斯娜娜"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
