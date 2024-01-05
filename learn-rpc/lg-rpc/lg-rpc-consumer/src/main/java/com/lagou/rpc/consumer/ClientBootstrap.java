package com.lagou.rpc.consumer;

import com.lagou.rpc.api.IUserService;
import com.lagou.rpc.consumer.proxy.RpcProxyClient;
import com.lagou.rpc.pojo.User;

/**
 * @author wangf-q
 */
public class ClientBootstrap {
    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcProxyClient.createProxy(IUserService.class);
        User user = userService.getById(2);
        System.out.println(user);
    }
}
