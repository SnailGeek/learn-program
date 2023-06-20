package com.snail.learntika.cutom;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.OfficeParserConfig;
import org.apache.tika.parser.microsoft.ooxml.OOXMLExtractor;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.microsoft.ooxml.XWPFWordExtractorDecorator;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.tika.sax.TeeContentHandler;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomParser extends OOXMLParser {
    @Override
    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return Collections.unmodifiableSet(new HashSet<>(Collections.singletonList(
                MediaType.application("vnd.openxmlformats-officedocument.wordprocessingml.document"))));
    }

    @Override
    public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context) throws IOException, SAXException, TikaException {
        System.out.println(".........testtest...........");

        this.configure(context);

        //only fetch wordExtractor
        OOXMLExtractor extractor = wordExtractor();//
        if (null != extractor) {
            extractor.parse();
        } else {
            super.parse(stream, handler, metadata, context);
        }

    }

    private OOXMLExtractor wordExtractor(Metadata metadata, ParseContext context) {
        OOXMLExtractor extractor = null;

        // Have the appropriate OOXML text extractor picked
        POIXMLTextExtractor poiExtractor = null;
        // This has already been set by OOXMLParser's call to configure()
        // We can rely on this being non-null.
        OfficeParserConfig config = context.get(OfficeParserConfig.class);


        POIXMLDocument document = poiExtractor.getDocument();
        if (document instanceof XWPFDocument) {
            extractor = new XWPFWordExtractorDecorator(metadata, context, (XWPFWordExtractor) poiExtractor);
        }
        return extractor;
    }

    public static void main(String[] args) throws TransformerConfigurationException, TikaException, IOException, SAXException {
        parse();
    }

    private static void parse() throws TikaException, IOException, SAXException, TransformerConfigurationException {
        TikaConfig tikaConfig = new TikaConfig(new ClassPathResource("tika-config.xml").getInputStream());
        AutoDetectParser parser = new AutoDetectParser(tikaConfig);
        StringWriter htmlBuffer = new StringWriter();

        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
        handler.setResult(new StreamResult(htmlBuffer));

        // 创建内容处理器，将结果转换为 HTML
        ContentHandler contentHandler = new TeeContentHandler(new ContentHandlerDecorator(handler));

        // 创建元数据对象
        Metadata metadata = new Metadata();

        // 创建解析上下文
        ParseContext context = new ParseContext();
//        File file = new File("F:\\temp\\文档结构化测试\\WBS文档测试.docx");
        File file = new File("F:\\temp\\文档结构化测试\\图片可以用文档测试.doc");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            // 使用 Tika 解析器解析文档并转换为 HTML
            parser.parse(inputStream, contentHandler, metadata, context);
        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
        }

        // 获取转换后的 HTML
        String html = htmlBuffer.toString();
        System.out.println("HTML: " + html);
    }

}
