package com.geek.design.isp.oop;

import java.util.Map;

/**
 * @program: RedisConfig
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 20:19
 **/
public class RedisConfig implements Updater, Viewer {
    private ConfigSource configSource;

    public RedisConfig(ConfigSource configSource) {
        this.configSource = configSource;
    }

    @Override
    public void update() {

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
