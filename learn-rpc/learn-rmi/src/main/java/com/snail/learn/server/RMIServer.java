package com.snail.learn.server;

import com.snail.learn.service.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            //1. 注册Registry实例，绑定端口
            Registry registry = LocateRegistry.createRegistry(9998);

            UserServiceImpl userService = new UserServiceImpl();
            //2. 将远程对象注册道RMI服务器上即服务注册表上
            registry.rebind("userService", userService);
            System.out.println("RMI Server is running...");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
