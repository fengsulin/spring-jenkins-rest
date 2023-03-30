package com.example.ci.common;

import com.example.ci.constants.CredentialType;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author: FSL
 * @date: 2023/3/30
 * @description: TODO
 */
public class RequestUtils {
    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 读取请求体
     * @param request
     * @return
     */
    public static String getRequestJson(HttpServletRequest request){
        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("request请求体获取异常：{}",e);
        }finally {
            if (streamReader != null){
                try {
                    streamReader.close();
                } catch (IOException e) {
                    log.error("request请求体读取流关闭异常：{}",e);
                }
            }
        }
        return "";
    }

    public static String transCredentialJson(HttpServletRequest request,int code,int opType){
        String jsonStr = "";
        switch (code){
            case 1:
                jsonStr = CredentialType.USERNAME_PWD.apply(getRequestJson(request),opType);
                break;
            case 2:
                jsonStr = CredentialType.SECRET_TEXT.apply(getRequestJson(request),opType);
                break;
            case 4:
            case 3:
                break;
            default:
                throw new RuntimeException("不存在该凭证类型：" + code);
        }
        return jsonStr;
    }
}
