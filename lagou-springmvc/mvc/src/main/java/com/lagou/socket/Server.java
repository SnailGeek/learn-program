package com.lagou.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        final Socket socket = server.accept();
        final BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final String line = is.readLine();
        System.out.println("received from client: " + line);
        // printWriter 用于发送数据
        final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("received data: " + line);
        printWriter.flush();

        printWriter.close();
        is.close();
        socket.close();
        server.close();
    }
}
