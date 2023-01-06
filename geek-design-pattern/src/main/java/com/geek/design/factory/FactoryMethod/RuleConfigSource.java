package com.geek.design.factory.FactoryMethod;

import com.geek.design.factory.SimpleFactory.IRuleConfigParser;
import com.geek.design.factory.SimpleFactory.InvalidRuleConfigException;
import com.geek.design.factory.SimpleFactory.RuleConfig;

/**
 * @program: RuleConfigSource
 * @description:
 * @author: wangf-q
 * @date: 2022-12-30 17:16
 **/
public class RuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) throws InvalidRuleConfigException {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParser parser = RuleConfigParserFactoryMap.getFactory(ruleConfigFileExtension).createParser();
        if (parser == null) {
            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
        }
        String configText = "";
        RuleConfig ruleConfig = parser.parser(configText);
        return ruleConfig;
    }


    private String getFileExtension(String filePath) {
        return "json";
    }
}