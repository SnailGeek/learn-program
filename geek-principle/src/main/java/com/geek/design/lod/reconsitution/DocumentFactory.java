package com.geek.design.lod.reconsitution;

import com.geek.design.lod.Html;

/**
 * @program: DocumentFactory
 * @description:
 * @author: wangf-q
 * @date: 2022-08-03 17:59
 **/
public class DocumentFactory {
    private HtmlDownloader downloader;

    public DocumentFactory(HtmlDownloader htmlDownloader) {
        this.downloader = htmlDownloader;
    }

    public Document createDocument(String url) {
        final Html html = downloader.downloadHtml(url);
        return new Document(url, html);
    }
}
