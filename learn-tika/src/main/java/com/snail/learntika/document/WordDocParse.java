package com.snail.learntika.document;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

public class WordDocParse {
    public static String readWord(InputStream is, boolean doc) {
        String buffer = "";
        try {
            if (doc) {
                //根据文件后缀进判断分支，.doc和.docx使用的方法不一样
                BodyContentHandler handler = new BodyContentHandler(1024 * 1024 * 10);//设置文档大小，避免文件太大tika报错，默认大小就是1024*1024*10
                Metadata metadata = new Metadata();
                ParseContext pContext = new ParseContext();
                Parser msOfficeParser = new OfficeParser();
                msOfficeParser.parse(is, handler, metadata, pContext);
                buffer = handler.toString();
                is.close();
            } else {
                Tika tika = new Tika();
                Reader parse = tika.parse(is);
                buffer = tika.parseToString(is);
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RRException("读取文件失败，请用Microsoft Word另存为之后再上传");
        }

        return buffer;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("F:\\temp\\文档结构化测试\\WBS文档测试.docx");
        InputStream inputStream = new FileInputStream(file);
        readWord(inputStream, false);
    }
}
