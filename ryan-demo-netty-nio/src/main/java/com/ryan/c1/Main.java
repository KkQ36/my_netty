package com.ryan.c1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author kq
 * 2024-09-25 19:12
 **/
public class Main {

    public static void main(String[] args) {
        try (FileInputStream inputStream =  new FileInputStream("ryan-demo-netty-nio/src/main/resources/data.txt")) {
            FileChannel channel = inputStream.getChannel();
            // 准备缓冲区，单位为字节，分配 1KB 空间
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int read = 0;
            while (read != -1) {
                // 从 Channel 中读取数据
                read = channel.read(buffer);
                // 将 Buffer 切换到读模式，打印 Buffer 的内容
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                // 切换到写模式，清空缓冲区
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scatteringRead();
        gatheringWrite();
    }

    private static void scatteringRead() {
        // 文件中的数据为：onetwothree，希望将其分别写入到三个缓冲区中
        try (FileInputStream inputStream =  new FileInputStream("ryan-demo-netty-nio/src/main/resources/scatteringRead.txt")) {
            FileChannel channel = inputStream.getChannel();
            // 准备缓冲区，单位为字节，分配 1KB 空间
            ByteBuffer[] buffers = new ByteBuffer[]{
                    ByteBuffer.allocate(3),
                    ByteBuffer.allocate(3),
                    ByteBuffer.allocate(5)
            };
            long read = channel.read(buffers);
            System.out.println(read);
            for (ByteBuffer buffer : buffers) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void gatheringWrite() {
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");

        try (FileOutputStream outputStream = new FileOutputStream("ryan-demo-netty-nio/src/main/resources/gatheringWrite.txt")) {
            FileChannel channel = outputStream.getChannel();
            // 准备缓冲区，单位为字节，分配 1KB 空间
            ByteBuffer[] buffers = new ByteBuffer[]{
                    buffer1,
                    buffer2,
                    buffer3
            };
            long write = channel.write(buffers);
            System.out.println(write);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
