package com.lagou.rpc.provider.handler;

import com.alibaba.fastjson.JSON;
import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.provider.anno.RpcService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端业务处理类
 * 1. 将@RpcService注解的bean缓存
 * 2. 接收客户端的请求
 * 3. 根据传递过来的beanName，从缓存中查找对应的bean
 * 4. 解析请求中的方法名称，参数类型 参数信息
 * 5. 反射调用bean中的方法
 * 6. 给客户端进行响应
 */
@Component
@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {
    private final Map SERVICE_INSTANCE_MAP = new ConcurrentHashMap();

    /**
     * 将标有@RpcService注解的bean缓存起来
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!serviceMap.isEmpty()) {
            Set<Map.Entry<String, Object>> entries = serviceMap.entrySet();
            for (Map.Entry<String, Object> item : entries) {
                Object serviceBean = item.getValue();
                if (serviceBean.getClass().getInterfaces().length == 0) {
                    throw new RuntimeException("服务必须实现接口");
                }
                // 默认取第一个接口作为缓存bean的名称
                String name = serviceBean.getClass().getInterfaces()[0].getName();
                SERVICE_INSTANCE_MAP.put(name, serviceBean);
            }
        }
    }

    /**
     * 通道读取就绪事件
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //1. 接收客户端请求---将msg转换成RpcRequest对象
        RpcRequest rpcRequest = JSON.parseObject(s, RpcRequest.class);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());

        // 2. 业务处理
        try {
            rpcResponse.setResult(handler(rpcRequest));
        } catch (Exception e) {
            rpcResponse.setError(e.getMessage());
            e.printStackTrace();
        }

        // 6. 给客户端响应
        channelHandlerContext.writeAndFlush(JSON.toJSONString(rpcResponse));
    }

    /**
     * 业务处理逻辑
     *
     * @return
     */
    public Object handler(RpcRequest rpcRequest) throws InvocationTargetException {
        // 3. 根据传递过来的beanName，从缓存中查找对应的bean
        Object serviceBean = SERVICE_INSTANCE_MAP.get(rpcRequest.getClassName());
        if (serviceBean == null) {
            throw new RuntimeException("根据beanName找不到服务，beanName" + rpcRequest.getClassName());
        }

        // 4. 解析请求中的方法名称，参数类型 参数信息
        Class<?> servviceBeanClass = serviceBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        // 5. 反射调用bean中的方法，CGLIB动态代理
        FastClass fastClass = FastClass.create(servviceBeanClass);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean, parameters);
    }
}
