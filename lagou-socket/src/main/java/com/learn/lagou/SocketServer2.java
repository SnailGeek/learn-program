package com.learn.lagou;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServer2 {
    static {
        BasicConfigurator.configure();
    }

    private static final Log LOGGER = LogFactory.getLog(SocketServer2.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(83);
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            while (true) {
                Socket socket = serverSocket.accept();
                SocketServerThread socketServerThread = new SocketServerThread(socket);
                executor.submit(socketServerThread);
//                new Thread(socketServerThread).start();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

}
