package com.geek.design.isp.oop;

/**
 * @program: Application
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 20:43
 **/
public class Application {
    static ConfigSource configSource = new ZookeeperConfigSource();
    public static final RedisConfig redisConfig = new RedisConfig(configSource);
    public static final KafkaConfig kafkaConfig = new KafkaConfig(configSource);
    public static final MysqlConfig mysqlConfig = new MysqlConfig(configSource);

    public static void main(String[] args) {
        ScheduledUpdater redisConfigUpdater =
                new ScheduledUpdater(redisConfig, 300, 300);
        redisConfigUpdater.run();

        ScheduledUpdater kafkaConfigUpdater =
                new ScheduledUpdater(kafkaConfig, 60, 60);
        redisConfigUpdater.run();

        SimpleHttpServer simpleHttpServer = new SimpleHttpServer("127.0.0.1", 2389);
        simpleHttpServer.addViewers("/config", redisConfig);
        simpleHttpServer.addViewers("/config", mysqlConfig);
        simpleHttpServer.run();
    }
}
