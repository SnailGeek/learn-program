package com.geek.design.lod.reconsitution;

import com.geek.design.lod.Html;
import com.geek.design.lod.HtmlRequest;

public class HtmlDownloader {
    private NetworkTransporter transporter;

    public Html downloadHtml(String url) {
        final HtmlRequest htmlRequest = new HtmlRequest(url);
        final Byte[] rawHtml = transporter.send(htmlRequest.getAddress(), htmlRequest.getContent().getBytes());
        return new Html(rawHtml);
    }
}