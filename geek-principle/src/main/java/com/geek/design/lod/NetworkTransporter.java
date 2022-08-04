package com.geek.design.lod;

/**
 * 负责底层网络通信，根据请求获取数据
 **/
public class NetworkTransporter {

    /**
     * 作为底层网络通信，应该据有通用性，不能直接依赖htmlRequest
     */
    public Byte[] send(HtmlRequest htmlRequest) {
        return new Byte[100];
    }
}
