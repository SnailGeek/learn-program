package com.geek.design.lod;

import com.geek.design.lsp.Transporter;

/**
 * 通过url获取网页
 **/
public class HtmlDownloader {
    private NetworkTransporter transporter;

    public Html downloadHtml(String url) {
        final Byte[] rawHtml = transporter.send(new HtmlRequest(url));
        return new Html(rawHtml);
    }
}
