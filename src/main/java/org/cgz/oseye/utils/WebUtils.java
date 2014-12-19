package org.cgz.oseye.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cgz.oseye.model.Users;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class WebUtils {
	
	/**保存cookie时的cookie的key**/
	public static final String COOKIENAME = "OSEYEUSERID";
	/**自定码**/
    public static final String WEBKEY = "^OSEYEWEBKEY^";
	/**cookie的有效期**/
    public static final long COOKIEMAXAGE = 60*60*24*7*2;
	

	/**
	 * 清除Cookie(一般在用户注销时)
	 * @param response
	 */
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(COOKIENAME, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 用户退出登录
	 */
	public static void logOut(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
	/**
     * session中取得登录用户
     * @param request
     * @return
     */
    public static Users getUsers(HttpServletRequest request,String sessionkey) {
        return (Users) request.getSession().getAttribute(sessionkey);
    }

    /**
     * 添加cookie
     * @param response
     * @param cookiename    cookie名称
     * @param cookievalue   cookie值
     * @param maxage        有效期(以秒为单位,假如存放两周,即14*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response,String cookiename,String cookievalue,int maxage) {
        Cookie cookie = new Cookie(cookiename,cookievalue);
        cookie.setPath("/");
        if(maxage >= 0) {
            cookie.setMaxAge(maxage);
        }
        //response.setHeader("Set-Cookie", "cookiename="+COOKIENAME+";Path=/;Domain="+SystemConstant.DOMAIN+";Max-Age="+COOKIEMAXAGE+";HTTPOnly");
        response.addCookie(cookie);
    }

    /**
     * 通过cookie的名称获取cookie
     * @param request
     * @param cookiename
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request,String cookiename) {
        Map<String,Cookie> cookieMap = getCookieMap(request);
        if(cookieMap.containsKey(cookiename)) {
            Cookie cookie = cookieMap.get(cookiename);
            return cookie;
        }
        return null;
    }
    
    /**
     * 获取所有的cookie 以cookiename->cookie形式放在map中
     * @param request
     * @return
     */
    private static Map<String,Cookie> getCookieMap(HttpServletRequest request) {
        Map<String,Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null != cookies) {
            for(Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
    
    /**
     * 获取用户的真实ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getHeader("Proxy-Client-IP");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getHeader("WL-Proxy-Client-IP");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getHeader("HTTP_CLIENT_IP");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getRemoteAddr();   
        }   
        return ip;
	}
    
    /**
     * 获取网站的title
     * @param url
     * @return
     * @throws java.io.IOException
     */
    public static String getUrlTitle(String url){
    	Document doc = null;
    	try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			return null;
		}
		return doc.title();
    }
    
    /**
     * 获取网站的域名
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    	return basePath;
    }
}
