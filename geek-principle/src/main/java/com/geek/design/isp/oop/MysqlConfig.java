package com.geek.design.isp.oop;

import java.util.Map;

/**
 * @program: MysqlConfig
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 20:40
 **/
public class MysqlConfig implements Viewer {
    private ConfigSource configSource;

    public MysqlConfig(ConfigSource configSource) {
        this.configSource = configSource;
    }

    @Override
    public String outputInPlainText() {
        return null;
    }

    @Override
    public Map<String, String> output() {
        return null;
    }
}
