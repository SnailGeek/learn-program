package com.geek.design.isp.oop;

/**
 * @program: KafkaConfig
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 20:19
 **/
public class KafkaConfig implements Updater {
    private ConfigSource configSource;

    public KafkaConfig(ConfigSource configSource) {
        this.configSource = configSource;
    }

    @Override
    public void update() {

    }
}
