package org.cgz.oseye.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 * 以JSON数据格式响应客户端请求
 * @author Administrator
 */
public class JsonUtils {

    public static void returnJson(HttpServletResponse response,String result) {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        try {
            PrintWriter out = response.getWriter();
            out.write(result);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解决使用ajaxform提交表单后客户端无法解析json的问题
     */
    public static void returnJsonAsHtml(HttpServletResponse response,String result) {
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        try {
            PrintWriter out = response.getWriter();
            out.write(result);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
