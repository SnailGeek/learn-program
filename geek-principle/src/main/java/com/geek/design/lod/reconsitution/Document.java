package com.geek.design.lod.reconsitution;

import com.geek.design.lod.Html;

public class Document {
    private Html html;
    private String url;

    public Document(String url, Html html) {
        this.url = url;
        this.html = html;
    }
}