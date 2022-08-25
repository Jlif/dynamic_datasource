package com.example.dynamic_datasource.codeGenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;

import java.util.Collections;

public class MybatisPlusGenerator {

    public static void main(String[] args) {
        String parentProjectDir = System.getProperty("user.dir");

        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/common", "root", "123456"))
                .globalConfig(builder -> {
                    builder.author("jiangchen") // 设置作者
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir(parentProjectDir + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.dynamic_datasource") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .entity("entity") //设置entity包名
                            .mapper("dao")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, parentProjectDir + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(Lists.newArrayList("biz")); // 设置需要生成的表名
                    builder.entityBuilder().enableLombok().enableFileOverride();
                    builder.mapperBuilder().enableFileOverride();
                    builder.serviceBuilder().formatServiceFileName("%sService").enableFileOverride(); // 设置生成的service接口文件名称的规则
                    builder.controllerBuilder().enableFileOverride();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
