package org.cgz.oseye.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.cgz.oseye.common.Gender;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.UserStatus;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.UserStatusService;
import org.cgz.oseye.service.UsersService;
import org.cgz.oseye.utils.HTMLFilter;
import org.cgz.oseye.utils.StringUtils;
import org.cgz.oseye.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.code.kaptcha.Constants;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/** 
 * @author 陈广志 
 */
@Controller("userController")
public class UserController extends BaseController {

	@Resource
	private UsersService usersService;
	@Resource
	private UserStatusService userStatusService;
	
	public final static List<String> nameEscape = Arrays.asList("administrator","superuser","manager","supermanager","bebop","bebopmanager");
	
	public final static List<String> nicknameEscape = Arrays.asList("administrator","manager","supermanager","bebop","bebopmanager","bebop管理员","网站管理员","管理员","超级用户","超级管理员","rooter","root","admin","superadmin","superuser");

    /*****************************用户注册开始******************************/
    /**
     * 用户注册页面显示
     * @return
     */
    @RequestMapping(value = "/user/register")
    public String register() {
        return "front/user-register";
    }

    /**
     * 处理注册信息(注册用户,发送激活链接)
     * @return
     */
    @RequestMapping(value = "/user/regactive",method = RequestMethod.POST)
    public String regactive(@RequestParam(required=false) String name,@RequestParam(required=false) String nickname,@RequestParam(required=false) String pwd,@RequestParam(required=false) String repassword,
                            @RequestParam(required=false) String email,@RequestParam(required=false) Integer gender,@RequestParam(required=false) String province,
                            @RequestParam(required=false) String city,@RequestParam(required=false) String vcode,
                            ModelMap map,HttpSession session,HttpServletRequest request) {
    	HTMLFilter htmlFilter = new HTMLFilter();
        String errMessage = checkReg(name,nickname,pwd,repassword,email,gender,province,city,vcode,session);
        if(errMessage!=null) {
            map.put("message",errMessage);
            return "forward:register";
        }
        Users users = new Users(htmlFilter.filter(name),htmlFilter.filter(nickname),pwd,email,gender.shortValue(),htmlFilter.filter(province),htmlFilter.filter(city));
        usersService.prepareRegister(users,getDomain(request)+"user/regfinish");
        map.put("email",email);
        return "redirect:regok";
    }

    @RequestMapping(value = "/user/regok")
    public String regok() {
        return "front/user-regactive";
    }

    /**
     * 完成账户激活
     * @return
     */
    @RequestMapping(value = "/user/regfinish",method = RequestMethod.GET)
    public String regfinish(@RequestParam String name,@RequestParam String activateCode,String message,ModelMap map) {
        if(usersService.checkRegUrl(name, activateCode)) {
            usersService.finishRegister(name, activateCode);
            map.put("name",name);
            return "front/user-regfinish";
        }else {
            return "redirect:regerror";
        }
    }

    /**
     * 注册激活链接检查
     * @return
     */
    @RequestMapping(value = "/user/regerror",method = RequestMethod.GET)
    public String regerror() {
        return "front/user-regerror";
    }

    /**
     * 判断用户是否被注册
     * @return
     */
    @RequestMapping(value = "/user/check-user-exist",method = RequestMethod.POST)
    public void usernameIsExist(@RequestParam String name,HttpServletResponse response) {
        if(usersService.userNameIsExist(name) || nameEscape.indexOf(name.trim())!=-1) {
            returnJson(response, false,"此用户名已存在,请填写其它用户名!");
        }else {
            returnJson(response, true,"此用户名可以注册");
        }
    }

    /**
     * 判断邮箱是否被注册
     * @return
     */
    @RequestMapping(value = "/user/check-email-exist",method = RequestMethod.POST)
    public void emailIsExist(@RequestParam String email,HttpServletResponse response) {
    	System.out.println(email);
        if(usersService.emailIsExist(email)) {
            returnJson(response, false, "此邮箱已存在,请填写其它邮箱!");
        }else {
            returnJson(response, true, "此邮箱可以注册");
        }
    }
    
    /**
     * 判断昵称是否被使用
     * @return
     */
    @RequestMapping(value = "/user/check-nickname-exist",method = RequestMethod.POST)
    public void nickNameIsExist(@RequestParam(value="nickname") String nickname,HttpServletResponse response) {
        if(usersService.checkNickNameIsExist(nickname) || nicknameEscape.indexOf(nickname.trim())!=-1) {
            returnJson(response, false, "此昵称已存在,请使用其它昵称!");
        }else {
            returnJson(response, true, "此昵称可以使用");
        }
    }

    /**
     * 检查验证码是否正确
     * @return
     */
    @RequestMapping(value = "/user/check-vcode",method = RequestMethod.POST)
    public void checkVcode(@RequestParam String vcode,HttpServletResponse response,HttpServletRequest request) {
        if(!vcode.equals(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
            returnJson(response, false, "验证码有误,请重新填入!");
        }else {
            returnJson(response, true);
        }
    }

    /**
     * 后台注册校验
     * @return
     */
    private String checkReg(String name,String nickname,String pwd,String repassword,String email,Integer gender,String province,String city,String vcode,HttpSession session) {
        String usernameRex = "^[a-zA-Z]\\w{5,19}$";
        String emailRegex = "^[a-z0-9_\\-]+(\\.[_a-z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)$";
        String nicknameRegex = "^[\u4e00-\u9fa5a-zA-Z0-9_]{1,16}$";
        if(!name.matches(usernameRex)) {
            return "用户名不合法,请重新输入!";
        }else if(usersService.userNameIsExist(name)) {
            return "此用户名已被使用,请重新输入!";
        }
        if(!email.matches(emailRegex)) {
            return "email不合法,请重新输入!";
        }else if(usersService.emailIsExist(email)) {
            return "此email已被使用,请重新输入!";
        }
        if(!nickname.trim().matches(nicknameRegex) || StringUtils.getStrLength(nickname.trim())>16 || StringUtils.getStrLength(nickname.trim())<=0) {
            return "昵称不合法,请重新输入!";
        }else if(usersService.checkNickNameIsExist(nickname)) {
            return "此昵称已被使用,请重新输入!";
        }
        if(pwd.trim().length()<6 || pwd.trim().length()>16) {
            return  "您输入的密码不合法,请重新输入!";
        }else if(!(repassword.trim().equals(pwd.trim()))) {
            return "2次输入的密码不一致,请确认!";
        }
        if(gender==null || Gender.Female.getValue()!=2 || Gender.Male.getValue()!=1) {
            return "性别必须选择,请确认!";
        }

        if(!vcode.equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
            return "验证码错误,请确认!";
        }

        if(province.trim()==null || province.trim().length()<=0 || city.trim()==null || city.trim().length()<=0) {
            return "居住地必须选择!";
        }
        return null;
    }

    /*****************************用户注册结束******************************/

    /*****************************用户登录开始******************************/
    /**
     * 登录页面显示
     * @return
     */
    @RequestMapping(value = "/user/login",method= RequestMethod.GET)
    public String login() {
        return "front/user-login";
    }
    
    /**
     * 弹窗登陆页面显示
     * @return
     */
    @RequestMapping(value = "/user/box-login",method= RequestMethod.POST)
    public String ajax_login_box() {
        return "share/ajax-login-box";
    }



    /**
     * 处理用户登录
     * @param name
     * @param pwd
     * @param response
     */
    @RequestMapping(value = "/user/loginactive",method = RequestMethod.POST)
    public void loginactive(@RequestParam(required = false) String email,
                            @RequestParam(required = false) String pwd,
                            @RequestParam(required = false) boolean rememberme,
                            @RequestParam(required = false) String vcode,
                            HttpServletResponse response,HttpServletRequest request,HttpSession session) {
        if(email==null || "".equals(email.trim()) || pwd==null || "".equals(pwd.trim())) {
            returnJson(response,false,"邮箱或密码不能为空!");
            return;
        }
        if(vcode==null || "".equals(vcode.trim()) || !vcode.equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
        	returnJson(response,false,"验证码不正确!");
        	return;
        }
        Users user = usersService.login(email,pwd);
        if(user!=null) {
        	if(user.getStatus()==SystemConstant.USERS_FREEZING) {
        		 returnJson(response,false,"您的账户尚未激活,请先激活!");
        		 return;
        	}
            session.setAttribute(SystemConstant.SESSIONUSER,user);//登录成功后,将user放到session中
            user.setOnline(SystemConstant.USERS_ONLINE);
            user.setThisLoginIp(WebUtils.getIpAddr(request));
            System.out.println("-------------------//////////////此次登录的IP："+WebUtils.getIpAddr(request));
            user.setThisLoginTime(new Date());
            user.setSessionKey(session.getId());
            usersService.update(user);
            if(rememberme) {//用户选择自动登录
                rememberByCookie(user,response);
            }
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
            //用户状态加入缓存
            userStatusService.setUserStatus(user.getId(), new UserStatus(user));
            returnJson(response,true);
        }
        returnJson(response,false,"邮箱或密码错误!");
    }


    /**
     * 两周自动登录
     * @param users
     * @param response
     */
    private void rememberByCookie(Users users,HttpServletResponse response) {
        //设置cookie的有效期
        long cookieValidateTime = System.currentTimeMillis() + (WebUtils.COOKIEMAXAGE*1000);
        String cookieValueWithSHA = DigestUtils.shaHex(users.getId() + "|" + users.getPwd() + "|" + cookieValidateTime + "|" + WebUtils.WEBKEY);
        String cookieValue = users.getId()+":"+cookieValidateTime+":"+cookieValueWithSHA;
        String cookieValueWithBase64 = Base64.encodeBase64String(cookieValue.getBytes());
        //保存cookie
        Cookie cookie = new Cookie(WebUtils.COOKIENAME, cookieValueWithBase64);
        cookie.setMaxAge((int) WebUtils.COOKIEMAXAGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
//    /**
//     * 获取验证码图片
//     * @return
//     * @throws
//     */
//    @RequestMapping(value = "/user/captcha",method= RequestMethod.GET)
//    public void getCaptcha(HttpServletResponse response,HttpSession session) throws IOException {
//        // 设置响应的类型格式为图片格式
//        response.setContentType("image/jpeg");
//        //禁止图像缓存。
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        ValidateCode vCode = new ValidateCode(120,40,5,100);
//        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, vCode.getCode());
//        vCode.write(response.getOutputStream());
//    }
    /*****************************用户登录开始******************************/
    
    /**
     * 头像上传页面
     */
    @RequestMapping(value="/admin/portraint",method=RequestMethod.GET)
    public String toPortraintManagePage() {
    	return "admin/admin-portraint";
    }
    
    
    
    /**
     * 密码管理页面
     * @return
     */
    @RequestMapping(value="/admin/password",method=RequestMethod.GET)
    public String toPasswordManagePage() {
    	return "admin/admin-password";
    }
    
    /**
     * 密码修改
     * @return
     */
    @RequestMapping(value="/admin/password-change",method=RequestMethod.POST)
    public void changePassword(@RequestParam(required=false) String oldPassword,@RequestParam(required=false) String newPassword,
    						   @RequestParam(required=false) String reNewPassword,HttpServletResponse response,HttpServletRequest request) {
    	Users sessionUser = getSessionUsers();
    	if(sessionUser!=null) {
    		if(!DigestUtils.shaHex(oldPassword).equals(sessionUser.getPwd())) {
    			returnJson(response, false, "您输入的旧密码不正确!");
    			return;
        	}
    		if(newPassword.trim().length()<5 || newPassword.trim().length()>12 || reNewPassword.trim().length()<5||reNewPassword.trim().length()>12) {
    			returnJson(response, false, "您输入的新密码长度不正确!");
    			return;
    		}
    		if(!newPassword.trim().equals(reNewPassword.trim())) {
    			returnJson(response, false, "新密码前后不一致!");
    			return;
    		}
    		Users user = usersService.findById(sessionUser.getId());
    		if(user!=null) {
    			usersService.changePassword(user, reNewPassword);	//修改密码
    			clearUserstatus();
    			//清空session
    			getSession().setAttribute(SystemConstant.SESSIONUSER, null);
    			//清空cookie
    			WebUtils.clearCookie(response);
    			returnJson(response, true, "密码修改成功，点击<a href='"+getDomain(request)+"user/login'>这里</a>重新登录");
    		}
    	}
    }
    
    /**
     * 密码重置页面
     * @param map
     * @return
     */
    @RequestMapping(value="/user/password-reset",method=RequestMethod.GET)
	public String toResetPasswordPage(ModelMap map) {
		Users user = getSessionUsers();
		if (user != null) {
			String email = user.getEmail();
			map.put("email", email);
		}
		return "front/user-password-reset";
	}
    
    
    /**
     * 发送重置密码链接到用户邮箱中
     * @return
     */
    @RequestMapping(value="/user/prepare-reset-password",method=RequestMethod.POST)
    public void sentResetEmail(@RequestParam(required=false) String email,
    						   @RequestParam(required=false) String vcode,
    						   HttpServletResponse response,HttpServletRequest request) {
    	if(email!=null && !"".equals(email.trim())) {
    		if(!vcode.equals(getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
    			returnJson(response, false, "验证码有误,请重新填入!");
    			return;
    		}
    		Users user = usersService.findByEmail(email);
    		if(user==null) {
    			returnJson(response, false, "您输入的邮箱地址尚未注册!");
    			return;
    		}else {
    			usersService.prepareResetPassword(email, getDomain(request)+"user/finish-reset-password");
    			clearUserstatus();
    			WebUtils.clearCookie(response);//清空cookie
    			getSession().setAttribute(SystemConstant.SESSIONUSER, null);//清空session
    			returnJson(response, true, "重置密码链接已发送到您的邮箱中,请查收!");
    		}
    	}else {
    		returnJson(response, false, "邮箱地址不能为空!");
			return;
		}
    }
    
   
    
    /**
     * 完成密码的重置,密码重置后 需要进行重新登录
     * @return
     */
    @RequestMapping(value="/user/finish-reset-password",method=RequestMethod.GET)
    public String finishResetPassword(@RequestParam String email,
    								  @RequestParam String activateCode,
    								  HttpServletResponse response,ModelMap map) {
    	if(email!=null && !"".equals(email.trim()) && activateCode!=null && !"".equals(activateCode.trim())) {
    		usersService.finishResetPassword(email, activateCode);
    		WebUtils.clearCookie(response);//清空cookie
			getSession().setAttribute(SystemConstant.SESSIONUSER, null);//清空session
			getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
			map.put("msg", "密码重置成功,请使用新的随机密码登入并修改密码.");
    	}
    	return "front/user-login";
    }
    
    /**
     * 清除用户状态
     */
    private void clearUserstatus() {
		UserStatus userStatus = userStatusService.getUserStatus(getSessionUsers().getId());
		if(userStatus!=null) {
			userStatusService.removeUserStatus(getSessionUsers().getId());
		}
    }
    
    /**
     * 邮箱修改页面
     * @return
     */
    @RequestMapping(value="/admin/email",method=RequestMethod.GET)
    public String toEmailManagePage() {
    	return "admin/admin-email";
    }
    
    /**
     * 向用户的新邮箱中发送激活新邮箱的邮件
     * @return
     */
    @RequestMapping(value="/admin/prepare-change-email",method=RequestMethod.POST)
    public void prepareChangeEmail(@RequestParam(required=false) String email,@RequestParam(required=false) String vcode,HttpServletResponse response,HttpServletRequest request) {
    	String emailRegex = "^[a-z0-9_\\-]+(\\.[_a-z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)$";
    	if(!email.matches(emailRegex)) {
    		returnJson(response, false, "请填写正确的邮箱地址!");
    		return;
    	}
    	if(usersService.emailIsExist(email)) {
    		returnJson(response, false, "此邮箱已被注册!");
    		return;
    	}
    	if(vcode==null || "".equals(vcode.trim()) || !vcode.equals(getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
        	returnJson(response,false,"验证码不正确!");
        	return;
        }
    	Users user = getSessionUsers();
    	if(user!=null) {
    		String oldEmail = user.getEmail();
    		usersService.prepareChangeEmail(oldEmail, email, getDomain(request)+"admin/change-email");
    		getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
    		returnJson(response, true, "验证邮件已发出，请打开"+email+"查收");
    		return;
    	}
    }
    
    /**
     * 跳转到完成修改邮箱页面
     * @return
     */
    @RequestMapping(value="/admin/change-email",method=RequestMethod.GET)
    public String toFinishChangeEmailPage(@RequestParam Integer id,@RequestParam String email,@RequestParam String activateCode,ModelMap map) {
    	map.put("id", id);
    	map.put("email", email);
    	map.put("activateCode", activateCode);
    	return "admin/admin-email-change";
    }
    
    /**
     * 完成新邮箱的激活
     * @return
     */
    @RequestMapping(value="/admin/finish-change-email",method=RequestMethod.POST)
    public void finishChangeEmail(@RequestParam Integer id,
								  @RequestParam String email,
								  @RequestParam String activateCode,
								  @RequestParam String password,
								  HttpServletResponse response,HttpServletRequest request) {
    	if(id!=null && id>=0 && email!=null && !"".equals(email.trim()) && password!=null && !"".equals(password.trim()) && activateCode!=null && !"".equals(activateCode.trim())) {
			if(usersService.finishChangeEmail(id, password, email, activateCode)) {
				clearUserstatus();
        		WebUtils.clearCookie(response);//清空cookie
    			getSession().setAttribute(SystemConstant.SESSIONUSER, null);//清空session
    			returnJson(response, true, "新邮箱已经被激活,请点击<a href='"+getDomain(request)+"user/login'>这里</a>重新登录");
        		return;
    		}
    	}
    	returnJson(response, false, "激活失败,请检查密码或激活链接是否正确!");
    }
    
    /**
     * 退出登录[退出登陆时,需要清除用户状态]
     * @param gotopage
     * @param request
     * @param response
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="/admin/logout",method=RequestMethod.GET)
    public void logOut(@RequestParam(value="gotopage",required=false) String gotopage,
    				   HttpServletRequest request,HttpServletResponse response) throws IOException {
    	Users user = getSessionUsers();
    	if(user!=null) {
    		String homePage = "/blog/"+user.getName();
        	WebUtils.logOut(request, response);//销毁sesson
        	WebUtils.clearCookie(response);//清空cookie
        	if(gotopage!=null && "home".equals(gotopage.trim())) {
        		response.sendRedirect(homePage);	//如果是从后台退出,则跳转到用户首页
        	}else {
        		response.sendRedirect(request.getHeader("referer"));	//如果是从前台退出,则跳转到来路页
        	}
    	}
    }
    
    /**
     * 个人资料修改页面
     * @param map
     * @return
     */
    @RequestMapping(value="/admin/profile",method=RequestMethod.GET)
    public String toProfileEditPage(ModelMap map) {
    	Users user = getSessionUsers();
    	map.put("user", user);
    	return "admin/admin-profile";
    }
    
   /**
    * 保存个人资料的修改
    * @param nickname
    * @param gender
    * @param province
    * @param city
    * @param signature
    * @param occupation
    * @param industry
    * @param workyear
    * @param response
    * @param map
    * @return
    */
    @RequestMapping(value="/admin/profile/edit",method=RequestMethod.POST)
    public void profileEdit(@RequestParam(value="nickname",required=false) String nickname,
    						@RequestParam(value="gender",required=false) Short gender,
    						@RequestParam(value="province",required=false) String province,
    						@RequestParam(value="city",required=false) String city,
    						@RequestParam(value="signature",required=false) String signature,
    						@RequestParam(value="occupation",required=false) String occupation,
    						@RequestParam(value="industry",required=false) String industry,
    						@RequestParam(value="workyear",required=false) String workyear,
    						HttpServletResponse response,
    						ModelMap map) {
    	Users user = getSessionUsers();
    	HTMLFilter htmlFilter = new HTMLFilter();
    	String nicknameRegex = "^[\u4e00-\u9fa5a-zA-Z0-9_]{1,16}$";
    	if(user!=null) {
    		if(null==nickname || "".equals(nickname.trim()) || !nickname.trim().matches(nicknameRegex) || StringUtils.getStrLength(nickname.trim())>16 || StringUtils.getStrLength(nickname.trim())<1) {
        		returnJson(response, false, "昵称输入有误,请检查!");
                return;
        	}else {
        		if(!user.getNickname().equals(nickname.trim()) && usersService.checkNickNameIsExist(nickname) || nicknameEscape.indexOf(nickname.trim())!=-1){
        			returnJson(response, false, "'"+nickname+"' 该昵称已经存在,请重新选择!");
                    return;
        		}
        	}
        	if(gender==null || Gender.Female.getValue()!=2 || Gender.Male.getValue()!=1) {
                returnJson(response, false, "性别必须选择,请确认!");
                return;
            }
        	if(province.trim()==null || province.trim().length()<=0 || city.trim()==null || city.trim().length()<=0) {
        		returnJson(response, false, "居住地必须选择!");
                return;
            }
        	if(null==signature || "".equals(signature.trim()) || StringUtils.getStrLength(signature.trim())>66) {
        		returnJson(response, false, "个性签名不能为空或大于32个字,请检查!");
                return;
            }
        	user.setNickname(htmlFilter.filter(nickname));
        	user.setGender(gender);
        	user.setProvince(htmlFilter.filter(province));
        	user.setCity(htmlFilter.filter(city));
        	user.setSignature(htmlFilter.filter(signature));
        	user.setOccupation(htmlFilter.filter(occupation));
        	user.setIndustry(htmlFilter.filter(industry));
        	user.setWorkyear(htmlFilter.filter(workyear));
        	usersService.update(user);
        	setSessionUsers(user);
        	returnJson(response, true);
    	}
    }
}
