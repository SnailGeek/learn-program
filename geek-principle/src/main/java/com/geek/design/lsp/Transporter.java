package com.geek.design.lsp;

import java.net.http.HttpClient;

/**
 * @program: Transporter
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 19:17
 **/
public class Transporter {
    private HttpClient httpClient;

    public Transporter(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response senRequest(Request request) {
        return new Response();
    }
}
