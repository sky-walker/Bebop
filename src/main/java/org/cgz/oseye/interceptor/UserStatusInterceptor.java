package org.cgz.oseye.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.controller.BlogController;
import org.cgz.oseye.controller.CommentsController;
import org.cgz.oseye.controller.ConcernController;
import org.cgz.oseye.controller.DraftsController;
import org.cgz.oseye.controller.FavoritesController;
import org.cgz.oseye.controller.FeedController;
import org.cgz.oseye.controller.IndexController;
import org.cgz.oseye.controller.MessageController;
import org.cgz.oseye.controller.Post_categoryController;
import org.cgz.oseye.controller.PostsController;
import org.cgz.oseye.controller.RemindController;
import org.cgz.oseye.controller.UserController;
import org.cgz.oseye.controller.UserSettingsController;
import org.cgz.oseye.controller.VisitsController;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.UserStatus;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.model.Visits;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.UserStatusService;
import org.cgz.oseye.service.UsersService;
import org.cgz.oseye.service.VisitsService;
import org.springframework.stereotype.Component;

@Aspect
@Component("onlineStatusInterceptor")
public class UserStatusInterceptor {
	
	@Resource
	private BlogsService blogsService;
	@Resource
	private BaseDao baseDao;
	@Resource
	private VisitsService visitsService;
	@Resource
	private UserStatusService userStatusService;
	@Resource
	private UsersService usersService;
	
	
	///****************************用户前台在线浏览状态***********************************///
	/**
	 * 用户在浏览状态
	 */
	@Pointcut("execution(* org.cgz.oseye.controller.BlogController.toBlogPage(..)) || " +
			  "execution(* org.cgz.oseye.controller.BlogController.toBlogPostPage(..)) || " +
			  "execution(* org.cgz.oseye.controller.BlogController.toBlogFeedsPage(..)) || " +
			  "execution(* org.cgz.oseye.controller.BlogController.toBlogFollowsPage(..)) || " +
			  "execution(* org.cgz.oseye.controller.BlogController.toBlogFansPage(..)) || " +
			  "execution(* org.cgz.oseye.controller.BlogController.toBlogVisitsPage(..))")
	private void frontPages() {}
	
	@Around("frontPages()")
	public Object frontPage(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		BlogController blogController =	(BlogController) pjp.getThis();
		HttpServletRequest request = blogController.getRequest();
		Users visitor = blogController.getSessionUsers();
		//只对登录的用户进行拦截
		if(visitor!=null) {
			//用户访问的url
			String visitUrl = request.getRequestURI();
			if(visitUrl!=null) {
				String urlchar[] = visitUrl.split("/");
				//用户状态描述
				String visittip = "";
				//博客名称
				String username = urlchar[2];
				//文章id
				Integer postId;
				//用户状态
				UserStatus userStatus = userStatusService.getUserStatus(visitor.getId());
				if(userStatus==null) {
					userStatusService.setUserStatus(visitor.getId(), new UserStatus(visitor));
				}
				if(username!=null) {
					Users user = usersService.findByName(username);
					if(user!=null) {
						String nickname = user.getNickname();
						if(visitUrl.matches("^/blog/[a-zA-Z]\\w{5,19}/?$")) {				//博客主页
							visittip = new StringBuffer("正在看 ").append(nickname).append(" 的博客").toString();
						}
						if(visitUrl.matches("^/blog/[a-zA-Z]\\w{5,19}/[0-9]*[1-9][0-9]*/?$")) {	//文章
							postId = Integer.parseInt(urlchar[3]);
							System.out.println(urlchar[3]);
							Posts post = baseDao.find(Posts.class, postId);
							if(post!=null) {
								visittip = new StringBuffer("正在看<<").append(post.getTitle()).append(">>").toString();
							}
						}
						if(visitUrl.matches("^/blog/[a-zA-Z]\\w{5,19}/fans$")) {			//粉丝
							visittip = new StringBuffer("正在看 ").append(nickname).append(" 的粉丝").toString();
						}
						if(visitUrl.matches("^/blog/[a-zA-Z]\\w{5,19}/follows$")) {			//关注
							visittip = new StringBuffer("正在看 ").append(nickname).append(" 的关注").toString();
						}
						if(visitUrl.matches("^/blog/[a-zA-Z]\\w{5,19}/feeds$")) {			//动态
							visittip = new StringBuffer("正在看 ").append(nickname).append(" 的动态").toString();
						}
						if(visitUrl.matches("^/blog/[a-zA-Z]\\w{5,19}/visits$")) {			//访客
							visittip = new StringBuffer("正在看 ").append(nickname).append(" 的访客").toString();
						}
						if(!"".equals(visittip)) {
							if(userStatus!=null) {
								userStatus.setStatus(SystemConstant.VISITS_STATUS_READBLOG);
								userStatus.setVisittime(new Date());
								userStatus.setVisiturl(visitUrl);
								userStatus.setVisittip(visittip);
								userStatusService.setUserStatus(visitor.getId(), userStatus);
								//用户浏览的不是自己的主页
								if(visitor.getId().intValue()!=user.getId().intValue()) {
									Visits visits = new Visits();
									visits.setVisitor(visitor);
									visits.setBlogs(user.getBlogs());
									visits.setVisiturl(visitUrl);
									visitsService.addVisits(visits);
									System.out.println("记录了用户的访问...");
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	
	
	
	///****************************用户后台在线浏览状态***********************************///
	
	///1.UserController
	@Pointcut("execution(* org.cgz.oseye.controller.UserController.toPortraintManagePage(..)) || " +
			  "execution(* org.cgz.oseye.controller.UserController.toPasswordManagePage(..)) || " +
			  "execution(* org.cgz.oseye.controller.UserController.toEmailManagePage(..))   ||  " +
			  "execution(* org.cgz.oseye.controller.UserController.toProfileEditPage(..)) ")
	private void admin_user_page () {}
	
	@Around("admin_user_page()")
	public Object admin_user(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		UserController controller =	(UserController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	
	///2.UserSettingsController
	@Pointcut("execution(* org.cgz.oseye.controller.UserSettingsController.toPrivacyManagePager(..))")
	private void admin_userSettings_page () {}
	
	@Around("admin_userSettings_page()")
	public Object admin_userSettings(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		UserSettingsController controller =	(UserSettingsController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	///3.VisitsController
	@Pointcut( "execution(* org.cgz.oseye.controller.VisitsController.toVisitsManagePage(..)) || execution(* org.cgz.oseye.controller.VisitsController.toVisitsFootsManagePage(..)) ")
	private void admin_visits_page () {}
	
	@Around("admin_visits_page()")
	public Object admin_visits(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		VisitsController controller =(VisitsController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	///4.RemindController
	@Pointcut("execution(* org.cgz.oseye.controller.RemindController.getReminds(..))")
	private void admin_remind_page () {}
	
	@Around("admin_remind_page()")
	public Object admin_remind(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		RemindController controller =	(RemindController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
		
		
	///5.PostsController
	@Pointcut("execution(* org.cgz.oseye.controller.PostsController.toPostNewOrEditPage(..)) || execution(* org.cgz.oseye.controller.PostsController.toManagePage(..))")
	private void admin_posts_page () {}
	
	@Around("admin_posts_page()")
	public Object admin_posts(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		PostsController controller =	(PostsController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	
	///6.Post_categoryController
	@Pointcut("execution(* org.cgz.oseye.controller.Post_categoryController.toManagePage(..))")
	private void admin_category_page () {}
	
	@Around("admin_category_page()")
	public Object admin_category(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		Post_categoryController controller = (Post_categoryController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	
	///7.MessageController
	@Pointcut("execution(* org.cgz.oseye.controller.MessageController.toPrivateMessageManagePage(..)) || execution(* org.cgz.oseye.controller.MessageController.toPrivateMessageDetailPage(..))")
	private void admin_message_page () {}
	
	@Around("admin_message_page()")
	public Object admin_message(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		MessageController controller = (MessageController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN_MESSAGE);
		return result;
	}
	
	///8.FeedController
	@Pointcut("execution(* org.cgz.oseye.controller.FeedController.toFeedsManagePage(..)) || execution(* org.cgz.oseye.controller.FeedController.getFeeds(..))")
	private void admin_feed_page () {}
	
	@Around("admin_feed_page()")
	public Object admin_feed(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		FeedController controller = (FeedController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	
	///9.FavoritesController
	@Pointcut("execution(* org.cgz.oseye.controller.FavoritesController.toFavoritesManagePage(..))")
	private void admin_favorite_page () {}
	
	@Around("admin_favorite_page()")
	public Object admin_favorite(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		FavoritesController controller = (FavoritesController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
		
		
	///10.DraftsController
	@Pointcut("execution(* org.cgz.oseye.controller.DraftsController.toManagePage(..)) || execution(* org.cgz.oseye.controller.DraftsController.draft_edit_page(..))")
	private void admin_drafts_page () {}
	
	@Around("admin_drafts_page()")
	public Object admin_drafts(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		DraftsController controller = (DraftsController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	
	///11.ConcernController
	@Pointcut("execution(* org.cgz.oseye.controller.ConcernController.toConcernPager(..))")
	private void admin_concern_page () {}
	
	@Around("admin_concern_page()")
	public Object admin_concern(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		ConcernController controller = (ConcernController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
		
		
		
	///12.CommentsController
	@Pointcut("execution(* org.cgz.oseye.controller.CommentsController.getComments(..))")
	private void admin_comments_page () {}
	
	@Around("admin_comments_page()")
	public Object admin_comments(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		CommentsController controller = (CommentsController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ADMIN);
		return result;
	}
	
	///13.IndexController
	@Pointcut("execution(* org.cgz.oseye.controller.IndexController.index(..))")
	private void index_page () {}
	
	@Around("index_page()")
	public Object index(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		IndexController controller = (IndexController) pjp.getThis();
		HttpServletRequest request = controller.getRequest();
		mkAdminStatus(request,SystemConstant.VISITS_STATUS_ONLINE);
		return result;
	}
	
	private void mkAdminStatus(HttpServletRequest request,short type) {
		Users user = (Users) request.getSession().getAttribute(SystemConstant.SESSIONUSER);
		//只对登录的用户进行拦截
		if(user!=null) {
			//用户状态
			UserStatus userStatus = userStatusService.getUserStatus(user.getId());
			if(userStatus==null) {
				userStatus = new UserStatus(user);
			}
			userStatus.setStatus(type);
			userStatus.setVisittime(new Date());
			userStatus.setVisittip("正在管理博客 ");
			userStatusService.setUserStatus(user.getId(), userStatus);
		}
	}
	
	
	
	
	///****************************退出时 清除在线状态***********************************///
	/**
	 * 用户退出时 清除用户状态
	 */
	@Pointcut("execution(* org.cgz.oseye.controller.UserController.logOut(..))")
	private void logOut() {}
	
	@Before("logOut()")
	public void logOutPointCut(JoinPoint pjp) throws Throwable {
		UserController userController =	(UserController) pjp.getThis();
		//获取访问者
		Users visitor = userController.getSessionUsers();
		//用户在线
		if(visitor!=null) {
			UserStatus userStatus = userStatusService.getUserStatus(visitor.getId());
			if(userStatus!=null) {
				userStatusService.removeUserStatus(visitor.getId());
			}
		}
	}
	
	
	/**
	 * 用户在搜索状态
	 */
	@Pointcut("execution(* org.cgz.oseye.controller.BlogController.toSearchPostPage(..))")
	private void searchPages() {}
	
	@Around("searchPages()")
	public Object searchPage(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		BlogController blogController =	(BlogController) pjp.getThis();
		HttpServletRequest request = blogController.getRequest();
		//获取访问者
		Users visitor = blogController.getSessionUsers();
		if(visitor!=null) {
			//用户访问的页面URL
			String visitUrl = request.getRequestURI();
			UserStatus userStatus = userStatusService.getUserStatus(visitor.getId());
			if(userStatus==null) {
				userStatusService.setUserStatus(visitor.getId(), new UserStatus(visitor));
			}
			if(visitUrl!=null) {
				String urlchar[] = visitUrl.split("/");
				String blogname = urlchar[2];
				if(blogname!=null) {
					Blogs blogs = blogsService.findByName(blogname);
					if(blogs!=null) {
						userStatus.setStatus(SystemConstant.VISITS_STATUS_SEARCH_POST);
						userStatus.setVisittime(new Date());
						userStatus.setVisiturl(visitUrl);
						userStatus.setVisittip("");
						userStatusService.setUserStatus(visitor.getId(), userStatus);
					}
				}
			}
		}
		return result;
	}
}
