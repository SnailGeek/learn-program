package com.lagou.rpc.provider;

import com.lagou.rpc.provider.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootstrapApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ServerBootstrapApplication.class, args);
    }

    @Autowired
    RpcServer rpcServer;

    @Override
    public void run(String... args) {
        new Thread(() -> rpcServer.startServer("127.0.0.1", 8899)).start();
    }
}
