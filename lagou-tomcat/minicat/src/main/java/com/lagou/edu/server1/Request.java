package com.lagou.edu.server1;


import java.io.IOException;
import java.io.InputStream;

/**
 * 根据InputStream输入流封装
 *
 * @author zero
 */
public class Request {
    private String method;
    private String url;
    private InputStream inputStream;

    public Request() {
    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        // 从输入流获取请求信息
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String inputStr = new String(bytes);
        // 获取第一行请求头信息
        final String firstLineStr = inputStr.split("\\n")[0];
        final String[] strings = firstLineStr.split(" ");
        method = strings[0];
        url = strings[1];

        System.out.println("========>>>method：" + method);
        System.out.println("========>>>url：" + url);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
