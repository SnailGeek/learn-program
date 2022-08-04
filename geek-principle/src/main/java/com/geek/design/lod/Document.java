package com.geek.design.lod;

/**
 * 网页文档，后续的网页内容抽取、分词、索引都是以此处理对象
 **/
public class Document {
    private Html html;
    private String url;

    public Document(String url) {
        this.url = url;
        HtmlDownloader downloader = new HtmlDownloader();
        this.html = downloader.downloadHtml(url);
    }
}
