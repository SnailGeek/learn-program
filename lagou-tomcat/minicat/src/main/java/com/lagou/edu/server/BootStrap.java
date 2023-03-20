package com.lagou.edu.server;


import java.io.IOException;
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
    public void start() throws IOException {
        // 完成minicat1.0， 浏览器请求http://localhost:8080, 返回一个固定的字符串到页面"Hello world"
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("------->>>>minicat start on port: " + port);
        while (true) {
            final Socket socket = serverSocket.accept();
            final OutputStream outputStream = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }

    }

    /**
     * miniCat 的程序启动入口
     *
     * @param args
     */
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
