package com.ryan.c1;

import java.nio.ByteBuffer;

/**
 * @author kq
 * 2024-09-25 20:29
 * ByteBuffer 解决黏包半包问题
 **/
public class ByteBufferExam {

    private static StringBuilder forStr = new StringBuilder();

    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);

        source.put("HelloWorld\nI'm Ryan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        // 切换到读模式
        source.flip();
        while (source.hasRemaining()) {
            byte b = source.get();
            if (b == '\n') {
                System.out.println(forStr);
                forStr = new StringBuilder();
            } else {
                forStr.append((char) b);
            }
        }
        source.clear();
    }

}
