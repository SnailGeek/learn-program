//package com.snail.learntika.document;
//
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.parser.AutoDetectParser;
//import org.apache.tika.parser.ParseContext;
//import org.apache.tika.parser.microsoft.OfficeParserConfig;
//import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
//import org.apache.tika.sax.BodyContentHandler;
//import org.apache.tika.sax.ToHTMLContentHandler;
//import org.apache.tika.sax.ToXMLContentHandler;
//import org.apache.xml.serializer.ToXMLSAXHandler;
//import org.xml.sax.ContentHandler;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//
//public class WordParserExample {
//    public static void main(String[] args) throws Exception {
//        File file = new File("F:\\temp\\文档结构化测试\\WBS文档测试.docx");
//        InputStream inputStream = new FileInputStream(file);
//
//        // 创建 Tika 解析器
//        OOXMLParser parser = new OOXMLParser();
//
//        // 配置 Office 解析器以提取标题和表格
//        OfficeParserConfig config = new OfficeParserConfig();
//        config.setIncludeHeadersAndFooters(true);
//        config.setIncludeSlideNotes(true);
//        config.setUseSAXDocxExtractor(true);
//        config.setConcatenatePhoneticRuns(true);
//        config.setIncludeShapeBasedContent(true);
//        ParseContext context = new ParseContext();
//        context.set(OfficeParserConfig.class, config);
//
//        // 创建内容处理器
//        ContentHandler contentHandler = new BodyContentHandler(new ToXMLContentHandler());
//
//        // 提取元数据和内容
//        Metadata metadata = new Metadata();
//        parser.parse(inputStream, contentHandler, metadata, context);
//
//        // 从元数据中获取标题
//        String title = metadata.get("title");
//
//        // 从内容处理器中获取提取的内容
//        String content = contentHandler.toString();
//
//        // 打印标题和内容
//        System.out.println("Title: " + title);
//        System.out.println("Content: " + content);
//
////        // 提取章节信息
////        if (file.getName().endsWith(".doc")) {
////            HWPFDocument document = new HWPFDocument(inputStream);
////            Range range = document.getRange();
////            String[] paragraphs = range.text().split("\\r?\\n");
////            System.out.println("Chapters: ");
////            int chapterCount = 1;
////            for (String paragraph : paragraphs) {
////                if (paragraph.matches(".*(Chapter|Section)\\s+\\d+.*")) {
////                    System.out.println("Chapter " + chapterCount + ": " + paragraph);
////                    chapterCount++;
////                }
////            }
////        }
//
//
//        // 关闭输入流
//        inputStream.close();
//
//        // 关闭输入流
//        inputStream.close();
//    }
//}
