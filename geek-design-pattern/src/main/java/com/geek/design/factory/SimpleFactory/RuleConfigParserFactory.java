package com.geek.design.factory.SimpleFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: RuleConfigParserFactory
 * @description:
 * @author: wangf-q
 * @date: 2023-01-06 11:08
 **/
public class RuleConfigParserFactory {
    private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

    static {
        cachedParsers.put("json", new JsonRuleConfigParser());
        cachedParsers.put("xml", new XmlRuleConfigParser());
        cachedParsers.put("yaml", new YamlRuleConfigParser());
        cachedParsers.put("properties", new PropertiesRuleConfigParser());
    }

    public static IRuleConfigParser createParser(String configFormat) {
        if (configFormat == null || configFormat.isEmpty()) {
            return null;
        }
        final IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
        return parser;
    }
}


//    public static IRuleConfigParser createParser(String ruleConfigFileExtension) {
//        IRuleConfigParser parser = null;
//        if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new JsonRuleConfigParser();
//        } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new XmlRuleConfigParser();
//        } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new YamlRuleConfigParser();
//        } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new PropertiesRuleConfigParser();
//        }
//        return parser;
//    }
