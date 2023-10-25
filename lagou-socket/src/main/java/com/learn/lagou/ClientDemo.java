package com.learn.lagou;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientDemo {
    public static void main(String[] args) throws IOException {
        while (true) {
            Socket socket = new Socket("127.0.0.1", 9889);
            OutputStream os = socket.getOutputStream();
            System.out.println("输入数据：");
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            os.write(msg.getBytes());

            // 从连接中取出输入流并接受回话
            InputStream s = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = s.read(bytes);
            System.out.println("服务端返回：" + new String(bytes, 0, read).trim());
            socket.close();
        }
    }
}
