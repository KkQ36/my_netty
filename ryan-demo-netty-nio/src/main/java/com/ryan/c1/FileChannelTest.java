package com.ryan.c1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author kq
 * 2024-09-25 20:44
 **/
public class FileChannelTest {

    public static void main(String[] args) {
        try (
                FileInputStream from = new FileInputStream("ryan-demo-netty-nio/src/main/resources/data.txt");
                FileOutputStream to = new FileOutputStream("ryan-demo-netty-nio/src/main/resources/to.txt")
        ) {
            FileChannel fromChannel = from.getChannel();
            FileChannel toChannel = to.getChannel();
            // 底层应用零拷贝进行优化
            long size = fromChannel.size();
            int count = (int) (size / 1024) + 1;
            for (long left = size; left > 0; ) {
                left -= fromChannel.transferTo((size - left), count, toChannel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
