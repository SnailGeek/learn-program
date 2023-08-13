package com.lagou.edu.server1;

/**
 * http协议工具类，主要是提供响应类信息
 */
public class HttpProtocolUtil {

    /**
     * 为响应码200提供请求信息
     *
     * @return
     */
    public static String getHttpHeader200(long contentLength) {
        return "HTTP/1.1 200 ok \n" +
                "Content-type: text/html \n" +
                "Content-length: " + contentLength + " \n" +
                "\r\n";
    }

    /**
     * 为响应码404提供请求信息
     *
     * @return
     */
    public static String getHttpHeader404() {
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-type: text/html \n" +
                "Content-length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;
    }
}
