package com.learn.lagou;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 1. 创建一个线程池，如果有客户端连接就创建一个线程，与之通信
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 2. 创建ServerSocket对象
        try (ServerSocket serverSocket = new ServerSocket(9889)) {
            System.out.println("服务器已启动");
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("客户端有连接");
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        handle(socket);
                    }
                });
            }
        }
    }
//    public static void handle(Socket socket) {
//        try {
//            System.out.println("线程ID:" + Thread.currentThread().getId()
//                    + " 线程名称:" + Thread.currentThread().getName());
//            //从连接中取出输入流来接收消息
//            InputStream is = socket.getInputStream();
//            byte[] b = new byte[1024];
//            int read = is.read(b);
//            System.out.println("客户端:" + new String(b, 0, read));
//            //连接中取出输出流并回话
//            OutputStream os = socket.getOutputStream();
//            os.write("没钱".getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                //关闭连接
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static void handle(Socket socket) {
        try {
            System.out.println("线程ID：" + Thread.currentThread().getId() + " 线程名称：" + Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println("客户端：" + new String(bytes, 0, read));
            // 连接取出输出流并回话
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            outputStream.write(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
