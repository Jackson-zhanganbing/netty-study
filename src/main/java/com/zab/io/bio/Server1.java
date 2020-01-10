package com.zab.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server1 {
    public static void main(String[] args) throws Exception {
        //服务端只需要指定端口，因为是客户端连接服务端，服务端不需要指定ip
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("127.0.0.1",9999));

        while (true) {
            //阻塞式接收
            Socket s = ss.accept();
            //接收的也是一个socket对象
            new Thread(() ->
                    handle(s)
            ).start();

        }
    }

    private static void handle(Socket s) {
        //socket获取输入流，获取客户端的数据
        InputStream inputStream = null;
        try {

            inputStream = s.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            len = inputStream.read(bytes);
            System.out.println(new String(bytes, 0, len));


            //获取该socket的输出流，响应客户端
            OutputStream outputStream = null;
            outputStream = s.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            outputStream.write(str.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {

        }
    }

}
