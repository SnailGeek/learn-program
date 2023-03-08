package com.lagou.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String msg = "client data";
        final Socket socket = new Socket("127.0.0.1", 8080);
        final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter.println(msg);
        printWriter.flush();

        final String line = bufferedReader.readLine();
        System.out.println("received from server: " + line);
        printWriter.close();
        bufferedReader.close();
        socket.close();
    }
}
