import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Types;
import java.util.Collections;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.SLASH;

public class CodeGenerator {
    private final String USER_DIR = System.getProperty("user.dir");
    private final String JAVA_DIR = USER_DIR + "/src/main/java";
    private final String DAO_XML_DIR = USER_DIR + "/src/main/resources";
    private static String author, tableNames, packageName;

    @BeforeAll
    public static void config() {
        author = "wangf-q";
        tableNames = "best_practice";
    }

    @Test
    public void generator() {
        String packageName = "com.infra.construction";
        FastAutoGenerator.create("jdbc:mysql://10.0.105.42:31178/bim_plan_main?useSSL=false&autoReconnect=true&characterEncoding=utf8",
                        "root", "123456")
                .globalConfig(builder ->
                        builder.author(author) // 设置作者
                                .outputDir(JAVA_DIR) // 指定输出目录
                )
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
                        builder.parent(packageName) // 设置父包名
                                .mapper("dao")
                                .entity("domain")
                                .pathInfo(Collections.singletonMap(OutputFile.xml, DAO_XML_DIR + SLASH + packageName.replace(".", SLASH) + SLASH + "dao")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude(tableNames) // 设置需要生成的表名
                                .serviceBuilder().convertServiceFileName(entityName -> entityName + ConstVal.SERVICE)
                                .entityBuilder().enableLombok().disableSerialVersionUID()
                                .controllerBuilder().enableRestStyle()
                )
                .execute();
    }
}
