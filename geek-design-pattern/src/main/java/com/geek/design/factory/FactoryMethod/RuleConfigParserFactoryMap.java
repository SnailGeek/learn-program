package com.geek.design.factory.FactoryMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: RuleConfigParserFactoryMap
 * @description:
 * @author: wangf-q
 * @date: 2023-01-06 13:07
 **/
public class RuleConfigParserFactoryMap {
    private static Map<String, IRuleConfigParserFactory> cachedFactories = new HashMap<>();

    static {
        cachedFactories.put("xml", new XmlRuleConfigParserFactory());
    }

    public static IRuleConfigParserFactory getFactory(String format) {
        if (format == null || format.isEmpty()) {
            return null;
        }
        return cachedFactories.get(format.toLowerCase());
    }
}
