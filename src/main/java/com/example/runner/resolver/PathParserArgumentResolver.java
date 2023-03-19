package com.example.runner.resolver;

import com.example.runner.annotion.PathParser;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.invoker.AbstractNamedValueArgumentResolver;
import org.springframework.web.service.invoker.HttpRequestValues;

import java.lang.annotation.Annotation;

/**
 * request 路径请求参数转换器
 * 针对文件夹参数进行自定义格式化
 */
public class PathParserArgumentResolver extends AbstractNamedValueArgumentResolver {

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        Annotation[] ans = parameter.getParameterAnnotations();
        PathVariable ant = null;
        PathParser ann = null;

        if (ans == null || ans.length <= 0) return null;
        for (Annotation annotation : ans){
            if (annotation instanceof PathVariable){
                ant = (PathVariable) annotation;
                return new NamedValueInfo(ant.name(), ant.required(), (String)null, "path variable", false);
            }
            if (annotation instanceof PathParser){
                ann = (PathParser) annotation;
                return new NamedValueInfo(null, ann.required(), (String)null, "path variable", false);
            }

        }
        return null;
    }

    @Override
    protected void addRequestValue(String name, Object value, MethodParameter parameter, HttpRequestValues.Builder requestValues) {

        requestValues.setUriVariable(name, parsePath((String) value));
    }

    /**
     * 处理路径参数，单级或多级目录参数处理。统一格式要求：/xxx或/xxx/xxx，或空字符串
     * @param path
     * @return
     */
    private String parsePath(String path){
        // 默认路径为空字符串
        if (!StringUtils.hasText(path)) return "";
        StringBuilder pathBuilder = new StringBuilder(path);
        String start = pathBuilder.substring(0, 1);
        String end = pathBuilder.substring(pathBuilder.length() - 1, pathBuilder.length());
        if (!"/".equals(start)) pathBuilder.insert(0,"/");
        if ("/".equals(end)) pathBuilder.delete(pathBuilder.length() -1,pathBuilder.length());

        return pathBuilder.toString().replaceAll("/", "/job/");
    }

}
