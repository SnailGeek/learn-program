package com.lagou.edu.server;

/**
 * 主要提供响应头信息
 */
public class HttpProtocolUtil {
    /**
     * 为响应码200提供请求信息
     *
     * @return
     */
    public static String getHttpHeader200(long contentLength) {
        return "HTTP/1.1 200 0K \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + contentLength + "\n" +
                "\r\n";
    }

    public static String getHttpHeader404() {
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + "\n" +
                "\r\n";
    }
}
