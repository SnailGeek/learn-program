package com.lagou.edu.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 该对象提供核心方法，输出html
 */
public class Response {
    private OutputStream outputStream;

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    /**
     * 根据url，获取静态资源的绝对路径，进一步根据绝对路径读取该静态资源文件最终通过输出流输出
     *
     * @param path
     */
    public void outputHtml(String path) throws IOException {
        // 获取静态资源文件的绝对路径
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);
        // 输入静态资源
        File file = new File(absoluteResourcePath);
        if (file.exists() && !file.isDirectory()) {
            // 读取静态资源文件，输出静态资源
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            // 输出404
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }
}
