package com.lagou.edu.server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread {
    private Socket socket;
    private Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket sock, Map<String, HttpServlet> servletMap) {
        this.socket = sock;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = socket.getInputStream();
            // 封装Request
            final Request request = new Request(inputStream);
            final Response response = new Response(socket.getOutputStream());

            final HttpServlet httpServlet = servletMap.get(request.getUrl());
            if (httpServlet == null) {
                response.outputHtml(request.getUrl());
            } else {
                httpServlet.service(request, response);
            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
