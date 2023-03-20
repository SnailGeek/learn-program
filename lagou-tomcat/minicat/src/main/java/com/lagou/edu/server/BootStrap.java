package com.lagou.edu.server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Minicat主类
 */
public class BootStrap {

    /**
     * 定义socket监听端口
     */
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * MiniCat启动需要初始化展开的一些操作
     */
    public void start() throws IOException, InterruptedException {
        // 完成minicat1.0， 浏览器请求http://localhost:8080, 返回一个固定的字符串到页面"Hello world"
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("------->>>>minicat start on port: " + port);
/*        while (true) {
            final Socket socket = serverSocket.accept();
            final OutputStream outputStream = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }*/
        /*
         * MiniCat2.0版本
         * 封装request和response对象，返回html静态资源文件
         */
        while (true) {
            Thread.sleep(10000);
            final Socket sock = serverSocket.accept();
            final InputStream inputStream = sock.getInputStream();
            // 封装Request
            final Request request = new Request(inputStream);
            final Response response = new Response(sock.getOutputStream());
            response.outputHtml(request.getUrl());
            sock.close();
        }

    }

    /**
     * miniCat 的程序启动入口
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
