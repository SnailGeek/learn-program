package com.snail.learn.service;

import com.snail.learn.pojo.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserService extends Remote {
    User getUserById(int id) throws RemoteException;
}
