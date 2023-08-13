package com.learn.tomcat.exp01.pyrmont;

import java.io.OutputStream;

public class Response {
    private static final int BUFFER_SIZE = 1024;
    private Request request;

    private OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {

    }
}
