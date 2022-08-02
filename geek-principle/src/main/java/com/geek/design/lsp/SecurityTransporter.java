package com.geek.design.lsp;

import java.net.http.HttpClient;

/**
 * @program: SecurityTransporter
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 19:18
 **/
public class SecurityTransporter extends Transporter {
    private String appId;
    private String appToken;


    public SecurityTransporter(HttpClient httpClient, String appId, String appToken) {
        super(httpClient);
        this.appId = appId;
        this.appToken = appToken;
    }

    @Override
    public Response senRequest(Request request) {
        return super.senRequest(request);
    }
}
