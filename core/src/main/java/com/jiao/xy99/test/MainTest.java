package com.jiao.xy99.test;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class MainTest {

    public static void main(String[] args) throws Exception {

        //创建一个freemarker.template.Configuration实例，它是存储 FreeMarker 应用级设置的核心部分
        //指定版本号
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_21);
        //设置模板目录
        cfg.setDirectoryForTemplateLoading(new File("E:\\网站项目\\room\\room\\core\\src\\main\\webapp\\WEB-INF\\templates"));
        //设置默认编码格式
        cfg.setDefaultEncoding("UTF-8");

        //数据
        Map<String, Object> product = new HashMap<>();
        product.put("name", "Huwei P8");
        product.put("price", "3985.7");
        product.put("users", new String[]{"Tom","Jack","Rose"});

        //从设置的目录中获得模板
        Template temp = cfg.getTemplate("temp.ftl");

        //合并模板和数据模型
        Writer out = new OutputStreamWriter(System.out);
        temp.process(product, out);

        //关闭
        out.flush();
        out.close();
    }
}