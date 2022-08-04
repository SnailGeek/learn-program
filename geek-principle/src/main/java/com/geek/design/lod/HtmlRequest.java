package com.geek.design.lod;

/**
 * @program: HtmlRequest
 * @description:
 * @author: wangf-q
 * @date: 2022-08-03 13:58
 **/
public class HtmlRequest {
    private String url;

    private String address;
    private String content;

    public HtmlRequest(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
