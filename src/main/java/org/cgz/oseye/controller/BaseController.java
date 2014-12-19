package org.cgz.oseye.controller;


import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.utils.JsonUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
	
	
	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取session 对象
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 获取session中的Users对象
	 * @return
	 */
	public Users getSessionUsers() {
		return (Users) getSession().getAttribute(SystemConstant.SESSIONUSER);
	}
	
	/**
	 * 设置session中的Users对象
	 * @param users
	 */
	public void setSessionUsers(Users users) {
		getSession().setAttribute(SystemConstant.SESSIONUSER,users);
	}
	

    /**
     * 获取域
     * @param request
     * @return
     */
	public String getDomain(HttpServletRequest request) {
        //return request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		return SystemConstant.DOMAIN;
    }

    /**
     * 简单地返回json格式数据
     * @param response
     * @param status
     * @param msg
     */
    protected void returnJson(HttpServletResponse response,boolean status,String msg) {
        String result = returnJsonString(status,msg);
        JsonUtils.returnJsonAsHtml(response,result);
    }

    /**
     * 简单滴返回json字符串
     * @param status
     * @param msg
     * @return
     */
    protected String returnJsonString(boolean status,String msg) {
        return  "{\"status\":"+status+",\"msg\":\""+msg+"\"}";
    }
    
    /**
     * 简单滴返回json字符串
     * @param status
     * @param msg
     * @return
     */
    protected String returnJsonString(String result) {
        return  result;
    }

    /**
     * 简单地返回json格式数据
     * @param response
     * @param status
     */
    protected void returnJson(HttpServletResponse response,boolean status) {
        String result = "{\"status\":"+status+"}";
        JsonUtils.returnJsonAsHtml(response,result);
    }
    
    /**
     * 返回的状态是数字类型
     * @param response
     * @param status
     * @param result
     */
    protected void returnJsonNum(HttpServletResponse response,int status,String msg) {
    	String result = "{\"status\":"+status+",\"msg\":\""+msg+"\"}";
        JsonUtils.returnJsonAsHtml(response,result);
    }
    
    /**
     * 简单地返回json格式数据
     * @param response
     * @param status
     */
    protected void returnJson(HttpServletResponse response,String result) {
        JsonUtils.returnJsonAsHtml(response,result);
    }
    
}
