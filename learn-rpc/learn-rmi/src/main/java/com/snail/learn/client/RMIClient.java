package com.snail.learn.client;

import com.snail.learn.pojo.User;
import com.snail.learn.service.IUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        // 1. 获取registry实例
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9998);
        //2. 通过registry实例查找远程对象
        IUserService userService = (IUserService) registry.lookup("userService");
        User user = userService.getUserById(1);
        System.out.println(user.getId() + "-------------" + user.getName());
    }
}
