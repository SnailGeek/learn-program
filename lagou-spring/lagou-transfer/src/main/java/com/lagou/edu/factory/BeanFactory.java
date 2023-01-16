package com.lagou.edu.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.SAXParser;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    private static Map<String, Object> map = new HashMap<>();

    static {
        final InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        final Document document;
        try {
            document = saxReader.read(resourceAsStream);
            final Element rootElement = document.getRootElement();
            final List<Element> beanList = rootElement.selectNodes("//bean");
            for (Element element : beanList) {
                final String id = element.attributeValue("id");
                final String clazz = element.attributeValue("class");
                final Class<?> aClass = Class.forName(clazz);
                final Object o = aClass.newInstance();
                map.put(id, o);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public static Object getBean(String id) {
        return map.get(id);
    }

}
