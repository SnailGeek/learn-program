package com.snail.learn;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://10.0.105.42:31178/bim_plan_main?useSSL=false&autoReconnect=true&characterEncoding=utf8",
                        "root", "123456")
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .outputDir(System.getProperty("user.dir") + "\\learn-mybatis\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            // 自定义类型转换
                            if (typeCode == Types.SMALLINT) {
                                return DbColumnType.INTEGER;
                            } else if (typeCode == Types.BIGINT) {
                                return DbColumnType.BIG_INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.infra.construction") // 设置父包名
                                .mapper("dao")
                                .entity("domain")
                                .pathInfo(Collections.singletonMap(OutputFile.xml,
                                        System.getProperty("user.dir") + "/learn-mybatis/src/main/resources/mapper")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("operation_file") // 设置需要生成的表名
                                .serviceBuilder().convertServiceFileName(entityName -> entityName + ConstVal.SERVICE)
                                .entityBuilder().enableLombok().disableSerialVersionUID()
                )
                .execute();
    }
}
