package com.salmon.chatService.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author Salmon
 * @since 2024-06-04
 */
public class SocketServer {
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(1024);
            System.out.println("服务启动,等待客户端连接");
            Socket socket = server.accept();
            String ip = socket.getInetAddress().getHostAddress();
            System.out.println("有客户端连接ip:" + ip + "端口:" + socket.getPort());
            new Thread(() -> {
                while (true) {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String readData = bufferedReader.readLine();
                        System.out.println("收到客户端消息->" + readData);

                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter printWriter = new PrintWriter(outputStream);
                        printWriter.println("我是服务端，已收到消息：" + readData);
                        printWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
