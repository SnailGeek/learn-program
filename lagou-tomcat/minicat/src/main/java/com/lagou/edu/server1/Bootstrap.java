package com.lagou.edu.server1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Minicat的主类
 */
public class Bootstrap {
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Minicat启动需要初始化展开的一些操作
     */
    public void start() throws IOException {
        // 完成Minicat 1.0版本（浏览器请求http://localhost:8080 , 返回一个固定的字符串道页面）
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=============>>>Minicat start on port: " + port);
        /*while (true) {
            final Socket socket = serverSocket.accept();
            // 有了socket，接受请求
            final OutputStream output = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200("Hello Minicat!".getBytes().length) + data;
            output.write(responseText.getBytes());
            socket.close();
        }*/

        /**
         * 完成Minicat2.0版本
         * 封装Request和Response对象，返回Html静态资源文件
         *
         */
        while (true) {
            Socket socket = serverSocket.accept();
            final InputStream input = socket.getInputStream();

            // 封装Request对象和Response对象

            final Request request = new Request(input);
            final Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());

            socket.close();
        }
    }


    /**
     * 程序启动入口
     *
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 启动Minicat　
            bootstrap.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
