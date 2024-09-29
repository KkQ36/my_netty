package com.ryan.c2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kq
 * 2024-09-29 14:49
 **/
public class SelectorServer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 1. 创建 Selector
        try (ServerSocketChannel ssc = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            ssc.bind(new InetSocketAddress(8080));
            ssc.configureBlocking(false);

            // 2. 建立 Selector 与 Channel 的联系
            // 在将来事件发生后，通过 SelectionKey 可以知道事件是由哪个 Channel 触发的
            SelectionKey sscKey = ssc.register(selector, SelectionKey.OP_ACCEPT, null);
            System.out.println(sscKey);

            // 3. 指定 Selector 监听哪些事件
            while (true) {
                // 4. 通过 select 方法等待事件发生，没有事件发生的时候，线程会被阻塞
                // 当事件在循环中未被处理的时候，在后续的循环中，会再次处理这些事件
                selector.select();

                // 5. 处理事件，selectionKeys 中存储所有可用的事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    // 6. 区分事件类型进行处理
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ, byteBuffer);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.read(byteBuffer);
                        // 切换到读模式
                        byteBuffer.flip();
                        while (byteBuffer.hasRemaining()) {
                            System.out.print((char) byteBuffer.get());
                        }
                        // 清除并切换到写模式
                        byteBuffer.clear();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
