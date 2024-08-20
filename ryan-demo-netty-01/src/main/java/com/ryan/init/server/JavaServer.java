package com.ryan.init.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author kq
 * 2024-08-20 11:27
 * 使用 Java 实现服务端接口
 **/
public class JavaServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)){
            // 等待客户端连接
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            OutputStream outputStream = accept.getOutputStream();
            // 将流转换成 Scanner 和 PrintWriter
            Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Hello! Enter Bye to exit.");
            boolean done = false;
            while (!done && in.hasNext()) {
                String line = in.nextLine();
                out.println("Echo: " + line);
                if ("Bye".equalsIgnoreCase(line)) done = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
