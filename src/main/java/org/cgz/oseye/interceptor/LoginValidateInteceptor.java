package org.cgz.oseye.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Users;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 后台登录拦截器
 * @author Administrator
 *
 */
public class LoginValidateInteceptor extends HandlerInterceptorAdapter {

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
		Users user = (Users) request.getSession().getAttribute(SystemConstant.SESSIONUSER);
		if(user==null) {
			response.sendRedirect("/user/login");
			return false;
		}
		return true;
	}
}

