package com.salmon.chatService.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author Salmon
 * @since 2024-05-19
 */
public class CodeGenerator {

    // 数据库配置
    private static final String MYSQL_URL = "127.0.0.1:3306";
    private static final String DATABASE = "chat";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "123456";

    // 项目路径
    private static final String projectPath = System.getProperty("user.dir") + "/chat-service";

    // 代码根路径包名
    private static final String ROOT_PACKAGE_NAME = "com.salmon.chatService";

    // 模板路径（注意：controller、service这类自定义模板不要带ftl后缀）
    private static final String CONTROLLER_TEMPLATE_PATH = "/templates/TemplateController.java";
    private static final String ENTITY_TEMPLATE_PATH = "/templates/model/TemplateEntity.java";
    private static final String VO_TEMPLATE_PATH = "/templates/model/TemplateVO.java.ftl";
    private static final String ADD_REQ_TEMPLATE_PATH = "/templates/model/TemplateAddRequest.java.ftl";
    private static final String ENUM_TEMPLATE_PATH = "/templates/model/TemplateEnum.java.ftl";
    private static final String QUERY_REQ_TEMPLATE_PATH = "/templates/model/TemplateQueryRequest.java.ftl";
    private static final String UPDATE_REQ_TEMPLATE_PATH = "/templates/model/TemplateUpdateRequest.java.ftl";

    // 自定义模板参数
    private static final String PACKAGE_VO = ROOT_PACKAGE_NAME + ".model.vo";
    private static final String PACKAGE_DTO = ROOT_PACKAGE_NAME + ".model.dto";
    private static final String PACKAGE_ENUM = ROOT_PACKAGE_NAME + ".model.enums";

    // 自定义模板输出路径
    private static final String OUTPUT_VO_PATH = "model" + File.separator + "vo" + File.separator;
    private static final String OUTPUT_DTO_PATH = "model" + File.separator + "dto" + File.separator;
    private static final String OUTPUT_ENUMS_PATH = "model" + File.separator + "enums" + File.separator;

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        String help = "请输入" + tip + "：";
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.hasText(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://" + MYSQL_URL + "/" + DATABASE + "?useUnicode=true&useSSL=false&characterEncoding=utf8",
                        MYSQL_USERNAME,
                        MYSQL_PASSWORD
                ).globalConfig(builder -> {
                    builder.author("Salmon") // 设置作者 baomidou 默认值:作者
                            .enableSwagger() // 开启 swagger 模式 默认值:false
                            .disableOpenDir() //禁止打开输出目录 默认值:true
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .dateType(DateType.TIME_PACK) // 定义生成的实体类中日期类型 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录 /opt/baomidou/ 默认值: windows:D: // linux or mac : /tmp
                }).packageConfig(builder -> {
                    builder.parent(ROOT_PACKAGE_NAME) // 父包模块名 默认值:com.baomidou
                            .controller("controller") //Controller 包名 默认值:controller
                            .entity("model.po") // Entity 包名 默认值:entity
                            .service("service") // Service 包名 默认值:service
                            .mapper("mapper") // Mapper 包名 默认值:mapper
                            .moduleName("") // 设置父包模块q名 默认值:无
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")); // 设置 mapperXml 生成路径
                }).strategyConfig(builder -> {
                    builder.addInclude(scanner("表名，多个英文逗号分割").split(",")) // 设置需要生成的表名 可边长参数“user”, “user1”
                            .addTablePrefix("tb_") // 设置过滤表前缀
                            // service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("I%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .enableFileOverride()
                            // 实体类策略配置
                            .entityBuilder()
                            .idType(IdType.AUTO)//主键策略  数据库自增ID，可修改策略为雪花算法
                            .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置
                            .addTableFills(new Column("createTime", FieldFill.INSERT)) // 自动填充配置
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE)) // 自动填充配置
                            .addTableFills(new Column("updateTime", FieldFill.INSERT_UPDATE)) // 自动填充配置
                            .enableLombok() //开启lombok
                            .logicDeleteColumnName("isDelete") // 说明逻辑删除是哪个字段
                            .versionColumnName("version") // 乐观锁字段
                            .enableTableFieldAnnotation() // 属性加上注解说明
                            .enableFileOverride()
                            .javaTemplate(ENTITY_TEMPLATE_PATH)
                            // controller 策略配置
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle() // 开启RestController注解
                            .enableFileOverride()
                            .template(CONTROLLER_TEMPLATE_PATH)
                            // mapper策略配置
                            .mapperBuilder()
                            .formatMapperFileName("%sMapper")
                            .enableFileOverride()
                            .mapperAnnotation(Mapper.class) // @mapper注解开启
                            .formatXmlFileName("%sMapper");
                }).injectionConfig(consumer -> { // 自定义模板
                            // 自定义模板参数：在.ftl(或者是.vm)模板中，通过${packageVo}获取属性
                            Map<String, Object> customMap = new HashMap<>();
                            // 包路径
                            customMap.put("packageVo", PACKAGE_VO);
                            customMap.put("packageDto", PACKAGE_DTO);
                            customMap.put("packageEnum", PACKAGE_ENUM);
                            customMap.put("packageName", ROOT_PACKAGE_NAME);
                            consumer.customMap(customMap);
                            // 输出文件前预处理
                            consumer.beforeOutputFile((tableInfo, stringObjectMap) -> {
                                // 自定义生成类
                                Map<String, String> customFile = new HashMap<>();
                                String entityName = tableInfo.getEntityName();
                                // 获取对象名称
                                String entityObjName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
                                String enumFile = String.format((OUTPUT_ENUMS_PATH + "%s" + File.separator + "%s" + "Enum" + StringPool.DOT_JAVA), entityObjName, tableInfo.getEntityName());
                                String voFile = String.format((OUTPUT_VO_PATH + "%s" + File.separator + "%s" + "VO" + StringPool.DOT_JAVA), entityObjName, tableInfo.getEntityName());
                                String addReqFile = String.format((OUTPUT_DTO_PATH + "%s" + File.separator + "%s" + "AddRequest" + StringPool.DOT_JAVA), entityObjName, tableInfo.getEntityName());
                                String queryReqFile = String.format((OUTPUT_DTO_PATH + "%s" + File.separator + "%s" + "QueryRequest" + StringPool.DOT_JAVA), entityObjName, tableInfo.getEntityName());
                                String updateReqFile = String.format((OUTPUT_DTO_PATH + "%s" + File.separator + "%s" + "UpdateRequest" + StringPool.DOT_JAVA), entityObjName, tableInfo.getEntityName());
                                // 模板路径
                                customFile.put(enumFile, ENUM_TEMPLATE_PATH);
                                customFile.put(voFile, VO_TEMPLATE_PATH);
                                customFile.put(addReqFile, ADD_REQ_TEMPLATE_PATH);
                                customFile.put(queryReqFile, QUERY_REQ_TEMPLATE_PATH);
                                customFile.put(updateReqFile, UPDATE_REQ_TEMPLATE_PATH);
                                consumer.customFile(customFile);
                            });
                        }
                ).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
