package com.zab.io.bio;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true){
            //客户端连接服务端，客户端肯定要知道服务器ip和端口
            Socket s = new Socket("127.0.0.1",9999);
            String str = scanner.nextLine();
            //同服务端，获取输出流，向服务端写数据
            OutputStream outputStream = s.getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.flush();
            //也可以获取输入流，获取服务端响应的数据
            InputStream inputStream = s.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            System.out.println(new String(bytes,0,len));
        }

    }
}
