package com.lagou.rpc.consumer.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.consumer.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 1. 封装Request对象
 * 2. 创建RpcClient对象
 * 3. 发送消息
 * 4. 返回结果
 */
public class RpcProxyClient {
    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 1. 封装Request对象
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setParameters(args);
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setMethodName(method.getName());
                        // 2. 创建RpcClient对象
                        RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);
                        rpcClient.initClient();
                        try {
                            // 3. 发送消息
                            Object responseMsg = rpcClient.send(JSON.toJSONString(rpcRequest));
                            RpcResponse rpcResponse = JSONObject.parseObject(responseMsg.toString(), RpcResponse.class);
                            if (rpcResponse.getError() != null) {
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            // 4. 返回结果
                            Object result = rpcResponse.getResult();
                            return JSON.parseObject(result.toString(), method.getReturnType());
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            rpcClient.close();
                        }
                    }
                });
    }
}
