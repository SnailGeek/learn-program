package com.snail.learn;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class TestGenCode {

    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://10.0.105.42:31178/bim_plan_main?useSSL=false&autoReconnect=true&characterEncoding=utf8",
                        "root", "123456")
                .globalConfig(builder ->
                        builder.author("snail") // 设置作者
                                .enableSwagger() // 开启 swagger 模式
                )
                .packageConfig(builder ->
                                builder.parent("tech.pdai.springboot.mysql8.mybatisplus.anno") // 设置父包名
                                        .moduleName("gencode")
                        // 设置父包模块名
                )
                .strategyConfig(builder ->
                        builder.addInclude("operation_file")
                                .serviceBuilder().formatServiceFileName("%sService")
                )
                .execute();
    }
}
