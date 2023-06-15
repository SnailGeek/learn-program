package com.snail.learntika.document;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StructuredTextExtractor {
    public static void main(String[] args) {
        File file = new File("F:\\temp\\文档结构化测试\\WBS文档测试.docx");

        // 创建 Tika 解析器
        AutoDetectParser parser = new AutoDetectParser();

        // 创建内容处理器
        BodyContentHandler contentHandler = new BodyContentHandler();

        // 创建元数据对象
        Metadata metadata = new Metadata();

        // 创建解析上下文
        ParseContext context = new ParseContext();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            // 使用 Tika 解析器解析文档
            parser.parse(inputStream, contentHandler, metadata, context);
        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
        }

        // 获取提取的结构化文本
        String structuredText = contentHandler.toString();
        System.out.println("Structured Text: " + structuredText);
    }
}
