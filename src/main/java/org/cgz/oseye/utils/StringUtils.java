package org.cgz.oseye.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	/**
	 * 中英文数字混合字符串长度(中文算2个)
	 * @param value
	 * @return
	 */
	public static int getStrLength(String value) {
		int length = 0;
		try {
			length = new String(value.getBytes("GBK"), "ISO8859_1").length();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	/**
	 * 校验url是否合法
	 * @param url
	 * @return
	 */
	public static boolean checkUrl(String url) {
		StringBuffer urlReg = new StringBuffer();
		urlReg.append("^((https|http|ftp|rtsp|mms)://)(([0-9a-z_!~*'().&=+$%-]+: )?")
			  .append("[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)")
			  .append("*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
		Pattern p = Pattern.compile(urlReg.toString(),Pattern.CASE_INSENSITIVE );
		Matcher matcher = p.matcher(url);
		return matcher.find();
	}
	
	/**
     * html转txt
     * @param inputString
     * @param count
     * @return
     */
    public static String filterHtmlToText(String input) {
        String htmlStr = input; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "[<>].*"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;// replaceAll(" ", "");
            textStr = textStr.replaceAll("&hellip;&hellip;", "……");
            textStr = textStr.replaceAll("&nbsp;", "").trim();
            textStr = textStr.replaceAll("\"", "");
            textStr = textStr.replaceAll("\r", "");
            textStr.replaceAll("\n", "");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return textStr;
    }
}
