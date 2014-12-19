package org.cgz.oseye.interceptor;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.UserStatus;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.UserStatusService;
import org.cgz.oseye.service.UsersService;
import org.cgz.oseye.utils.SpringContextUtil;
import org.cgz.oseye.utils.WebUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 自动登录
 * @author Administrator
 */
public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		//先从session中获取user,如果没有 则表示用户还未登录
		Users user = (Users) request.getSession().getAttribute(SystemConstant.SESSIONUSER);
		if(user==null) {//用户未登录,看cookie是否存在
			Cookie cookie = WebUtils.getCookieByName(request, SystemConstant.COOKIENAME);
			if(cookie!=null && cookie.getName().trim().equals(SystemConstant.COOKIENAME)) {
				String cookieValue = cookie.getValue();
				if(cookieValue!=null && !"".equals(cookieValue.trim())) {
					String cookieValueAfterDecode = new String(Base64.decodeBase64(cookieValue),"utf-8");
					String[] cookieValues = cookieValueAfterDecode.split(":");
					//判断解析后的cookie数组值大小是否正确
					if(cookieValues.length!=3) {	
						response.setContentType("text/html; charset=utf-8"); 
						PrintWriter out = response.getWriter(); 
						out.println("你正在用非正常方式进入本站..."); 
						out.close(); 
						return false; 
					}
					//获取cookie的有效期
					long cookieValidateTime = new Long(cookieValues[1]);
					if(cookieValidateTime < System.currentTimeMillis() ) {	//cookie已经失效
						WebUtils.clearCookie(response);//清除cookie
						return false;
					}
					//如果cookie合法,且未过期,此时就为用户进行自动登录(就是将user设置到session中)
					String userId = cookieValues[0];
					if(userId!=null && !"".equals(userId)) {
						UsersService userService = (UsersService) SpringContextUtil.getBean("userService");
						UserStatusService userStatusService =  (UserStatusService) SpringContextUtil.getBean("userStatusService");
						user = userService.getById(Integer.parseInt(userId));
						if(user!=null && user.getPwd()!=null) {
							String cookieValueWithSHA = cookieValues[2];
							if(cookieValueWithSHA.equals(DigestUtils.shaHex(userId+"|"+user.getPwd()+"|"+cookieValidateTime+"|"+SystemConstant.WEBKEY)) ) {
								HttpSession session = request.getSession();
								user.setThisLoginIp(WebUtils.getIpAddr(request));
								user.setThisLoginTime(new Date());
								//user.setSessionKey(session.getId());
								userService.update(user);
								session.setAttribute(SystemConstant.SESSIONUSER, user);
								//session.setAttribute("status",SystemConstant.USERS_ONLINE);
								//用户状态加入缓存
								userStatusService.setUserStatus(user.getId(), new UserStatus(user));
								System.out.println("自动登录......");
							}
						}
					}
				}
			}
		}
		return true;
	}
}


