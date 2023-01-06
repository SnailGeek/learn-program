package com.geek.design.factory.SimpleFactory;

/**
 * @program: RuleConfigSource
 * @description:
 * @author: wangf-q
 * @date: 2022-12-30 17:16
 **/
public class RuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) throws InvalidRuleConfigException {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParser parser = RuleConfigParserFactory.createParser(ruleConfigFileExtension);
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
