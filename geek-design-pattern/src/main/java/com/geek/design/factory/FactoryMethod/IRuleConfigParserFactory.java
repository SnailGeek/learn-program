package com.geek.design.factory.FactoryMethod;

import com.geek.design.factory.SimpleFactory.IRuleConfigParser;

public interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}
