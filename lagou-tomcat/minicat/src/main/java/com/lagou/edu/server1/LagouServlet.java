package com.lagou.edu.server1;

import java.io.IOException;

public class LagouServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            String content = "<h1>LagouServlet get</h1>";
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            String content = "<h1>LagouServlet post</h1>";
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
