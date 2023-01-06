package com.geek.design.factory.FactoryMethod;

import com.geek.design.factory.SimpleFactory.IRuleConfigParser;
import com.geek.design.factory.SimpleFactory.XmlRuleConfigParser;

/**
 * @program: XmlRuleConfigParserFactory
 * @description:
 * @author: wangf-q
 * @date: 2023-01-06 11:21
 **/
public class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new XmlRuleConfigParser();
    }
}
