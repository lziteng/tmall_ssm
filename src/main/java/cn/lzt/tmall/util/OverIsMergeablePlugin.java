package cn.lzt.tmall.util;

import org.mybatis.generator.api.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 启动逆向工程时，避免生成重复代码(采用覆盖原有代码的方式)。
 * @author lzt
 * @date 2019/11/26 17:17
 */
public class OverIsMergeablePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
