package com.geek.design.isp.oop;

import java.util.Map;

public interface Config {
    void update();

    String outputInPlainText();

    Map<String, String> output();
}
