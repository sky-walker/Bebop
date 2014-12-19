package org.cgz.oseye.controller;


import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Comments;
import org.cgz.oseye.model.Concern;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.model.Visits;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.CommentsService;
import org.cgz.oseye.service.ConcernService;
import org.cgz.oseye.service.FeedService;
import org.cgz.oseye.service.Post_categoriesService;
import org.cgz.oseye.service.PostsService;
import org.cgz.oseye.service.UsersService;
import org.cgz.oseye.service.VisitsService;
import org.cgz.oseye.service.impl.VisitStatService;
import org.cgz.oseye.utils.FreemarkerUtil;
import org.cgz.oseye.utils.HTMLFilter;
import org.cgz.oseye.utils.JsoupUtils;
import org.cgz.oseye.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("blogController")
@RequestMapping("/blog")
public class BlogController extends BaseController {
	
	@Resource
	private PostsService postsService;
	@Resource
	private Post_categoriesService postCategoriesService;
	@Resource
	private BlogsService blogsService;
	@Resource
	private UsersService usersService;
	@Resource
	private CommentsService commentsService;
	@Resource
	private ConcernService concernService;
	@Resource
	private FeedService feedService;
	@Resource
	private VisitsService visitsService;
	
//	@Resource
//	private PostsSearch postsSearch;
	
	/**
	 * 博客首页
	 * @param username
	 * @param page
	 * @param categoryId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}",method=RequestMethod.GET)
	public String toBlogPage(@PathVariable("username") String username,
							 @RequestParam(value="page",required=false) Integer page,
							 @RequestParam(value="category",required=false) Integer categoryId,
							 HttpServletRequest request,ModelMap map) {
		Users user = usersService.findByName(username);
		if(user!=null) {
			Pager<Posts> pager = new Pager<Posts>();
			if(page==null || page <=0) {
				page=1;
			}
			if(categoryId!=null&&postCategoriesService.getCategory(user.getId(), categoryId)) {
                pager = postsService.getPostsByCategory(categoryId, page, pager.getPageSize());
				pager.addQueryBy("category", categoryId);
				map.put("categoryId", categoryId);
			}else {
                pager = postsService.getPosts(user.getId(), page, pager.getPageSize());
			}
			map.put("jsoupUtils", FreemarkerUtil.userStaticClass("org.cgz.oseye.utils.JsoupUtils"));
			if(categoryId!=null && categoryId>=0) {
				pager.addQueryBy("category", categoryId);
			}
			checkFollow(user.getId(),map);
			map.put("pager", pager);
			map.put("blog", user.getBlogs());
			map.put("user", user);
			updateBlogViewsNum(user.getId());
			return "front/blog";
		}else {
			return "index/404";
		}
	}
	
	/**
	 * 文章页面显示
	 * @param blogName
	 * @param postId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/{postId}",method=RequestMethod.GET)
	public String toBlogPostPage(@PathVariable("username") String username,
							 @PathVariable("postId") Integer postId,
							 @RequestParam(value="page",required=false) Integer page,
							 ModelMap map) {
		Users user = usersService.findByName(username);
		if(user!=null) {
			Posts post = postsService.getPost(user.getId(), postId);
			map.put("entity", post);
			map.put("user", user);
			map.put("blog", user.getBlogs());
			Pager<Comments> pager = new Pager<Comments>();
			if(page==null || page <=0) {
				page=1;
			}
			pager = commentsService.getPostComments(postId, page, pager.getPageSize());
			map.put("jsoupUtils", FreemarkerUtil.userStaticClass("org.cgz.oseye.utils.JsoupUtils"));
			map.put("pager", pager);
			checkFollow(user.getId(),map);
			updatePostViewsNum(post.getId());
			return "front/blog-post";
		}else {
			return "index/404";
		}
	}
	
	/**
	 * 评论的添加(包括评论的回复)
	 * @param blogId
	 * @param postId
	 * @param authId
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/comment/reply",method=RequestMethod.POST)
	public String replyComment(@RequestParam(value="blog") Integer blogId,
							 @RequestParam(value="post") Integer postId,
							 @RequestParam(value="auth",required=false) Integer authId,
							 @RequestParam(value="parent",required=false) Integer parentId,
							 @RequestParam(value="content",required=false) String content,
							 HttpServletRequest request,ModelMap map,HttpServletResponse response) {
		Posts post = postsService.getPost(blogId, postId);
		Users auth = getSessionUsers();
		Blogs blog = blogsService.findById(blogId);
		HTMLFilter htmlFilter = new HTMLFilter();
		content = htmlFilter.filter(content);
		//用户未登录
		if(auth==null) {
			map.put("errorMsg", "!评论前必须先登录,点此<a href='#ajax-login-box' data-toggle='modal' class='commen_a'>登录</a>");
			return "front/blog-comment-add-error";
		}
		//文章不存在
		if(post==null || blog==null) {
			map.put("errorMsg", "!评论出现错误.");
			return "front/blog-comment-add-error";
		}
		//文章锁定
		if(post.getCanComment()==SystemConstant.POSTS_NO_COMMENT) {
			map.put("errorMsg", "!文章已被锁定,无法进行评论.");
			return "front/blog-comment-add-error";
		}
		//评论长度不合法
		if(content.trim().length()<5 || content.trim().length()/2>800) {
			map.put("errorMsg", "!请检查输入的内容(评论内容过短或过长)");
			return "front/blog-comment-add-error";
		}
		Comments comment = new Comments();
		comment.setPosts(post);
		comment.setUsers(auth);
		comment.setBlogs(blog);
		comment.setContent(content);
		if(parentId!=null) {
			Comments parent;
			try {
				parent = commentsService.loadComment(parentId);
				if(parent.getVisible()==SystemConstant.COMMENTS_INVISIBLE || parent.getDelStatus()==SystemConstant.COMMENTS_DEL) {
					map.put("errorMsg", "!您回复的评论不存在.");
					return "front/blog-comment-add-error";
				}
				comment.setParent(parent);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("errorMsg", "!您回复的评论不存在.");
				return "front/blog-comment-add-error";
			}
		}
		comment.setIp(WebUtils.getIpAddr(request));
		commentsService.addComment(comment);
		map.put("comment", comment);
		map.put("blog", blog);
		map.put("auth", auth);
		map.put("post", post);
		return "front/blog-comment";
	}
	
	/**
	 * 评论的添加(包括评论的回复)
	 * @param blogId
	 * @param postId
	 * @param authId
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/comment/add",method=RequestMethod.POST)
	public String addComment(@RequestParam(value="blog") Integer blogId,
							 @RequestParam(value="post") Integer postId,
							 @RequestParam(value="auth",required=false) Integer authId,
							 @RequestParam(value="parent",required=false) Integer parentId,
							 @RequestParam(value="content",required=false) String content,
							 HttpServletRequest request,ModelMap map,HttpServletResponse response) {
		Posts post = postsService.getPost(blogId, postId);
		Users auth = getSessionUsers();
		Blogs blog = blogsService.findById(blogId);
		content = JsoupUtils.cleanHtml(content, getDomain(request));
		//用户未登录
		if(auth==null) {
			map.put("errorMsg", "!评论前必须先登录,点此<a href='#ajax-login-box' data-toggle='modal' class='commen_a'>登录</a>");
			return "front/blog-comment-add-error";
		}
		//文章不存在
		if(post==null || blog==null) {
			map.put("errorMsg", "!评论出现错误.");
			return "front/blog-comment-add-error";
		}
		//文章锁定
		if(post.getCanComment()==SystemConstant.POSTS_NO_COMMENT) {
			map.put("errorMsg", "!文章已被锁定,无法进行评论.");
			return "front/blog-comment-add-error";
		}
		//评论长度不合法
		if(content.trim().length()<5 || content.trim().length()/2>800) {
			map.put("errorMsg", "!请检查输入的内容(评论内容过短或过长)");
			return "front/blog-comment-add-error";
		}
		Comments comment = new Comments();
		comment.setPosts(post);
		comment.setUsers(auth);
		comment.setBlogs(blog);
		comment.setContent(content);
		if(parentId!=null) {
			Comments parent;
			try {
				parent = commentsService.loadComment(parentId);
				if(parent.getVisible()==SystemConstant.COMMENTS_INVISIBLE || parent.getDelStatus()==SystemConstant.COMMENTS_DEL) {
					map.put("errorMsg", "!您回复的评论不存在.");
					return "front/blog-comment-add-error";
				}
				comment.setParent(parent);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("errorMsg", "!您回复的评论不存在.");
				return "front/blog-comment-add-error";
			}
		}
		comment.setIp(WebUtils.getIpAddr(request));
		commentsService.addComment(comment);
		map.put("comment", comment);
		map.put("blog", blog);
		map.put("auth", auth);
		map.put("post", post);
		return "front/blog-comment";
	}
	
	/**
	 * 首页左侧博客分类
	 * @param blogName
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/categories",method=RequestMethod.POST)
	public String toBlogCategoriesPage(@RequestParam Integer id,ModelMap map) {
		Users user = usersService.findById(id);
		List<Post_categories> categories = null;
		if(user!=null) {
			categories = postCategoriesService.getCategories(user.getId(), -1, -1).getResultList();
			map.put("user", user);
			map.put("blog", user.getBlogs());
		}
		map.put("categories", categories);
		return "front/blog-categories";
	}
	
	/**
	 * 阅读数排行
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/mostRead",method=RequestMethod.POST)
	public String mostReadPost(@RequestParam Integer id,ModelMap map) {
		Users user = usersService.findById(id);
		if(user!=null) {
			Pager<Posts> pager = postsService.getPostsByReadNumSort(user.getId(), 1, 10);
			map.put("user", user);
			map.put("pager", pager);
		}
		return "front/blog-left-mostread";
	}
	
	/**
	 * 评论排行
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/mostComment",method=RequestMethod.POST)
	public String mostCommentPost(@RequestParam Integer id,ModelMap map) {
		Users user = usersService.findById(id);
		if(user!=null) {
			Pager<Posts> pager = postsService.getPostsByCommentsNumSort(user.getId(), 1, 10);
			map.put("user", user);
			map.put("pager", pager);
		}
		return "front/blog-left-mostcomment";
	}
	
	/**
	 * 关注
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/leftFollows",method=RequestMethod.POST)
	public String leftFollows(@RequestParam Integer id,ModelMap map) {
		Users user = usersService.findById(id);
		if(user!=null) {
			Pager<Concern> pager = concernService.getFollowsPager(user.getId(), 1, 12, "login",false);
			map.put("user", user);
			map.put("pager", pager);
		}
		return "front/blog-left-follows";
	}
	
	/**
	 * 粉丝
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/leftFans",method=RequestMethod.POST)
	public String leftFans(@RequestParam Integer id,ModelMap map) {
		Users user = usersService.findById(id);
		if(user!=null) {
			Pager<Concern> pager = concernService.getFansPager(user.getId(), 1, 12, "login",false);
			map.put("user", user);
			map.put("pager", pager);
		}
		return "front/blog-left-fans";
	}
	
	/**
	 * 查询博客最近动态
	 * @param blogName
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/feeds",method=RequestMethod.GET)
	public String toBlogFeedsPage(@PathVariable("username") String username,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = usersService.findByName(username);
		Pager<Feed> pager = new Pager<Feed>();
		pager = feedService.getMyFeeds(user.getId(), page==null||page<=0?1:page,pager.getPageSize(),null);
		map.put("pager", pager);
		System.out.println(pager.getTotalRecords());
		map.put("user", user);
		map.put("blog", user.getBlogs());
		checkFollow(user.getId(),map);
		return "front/blog-feeds";
	}
	
	/**
	 * 用户关注
	 * @param blogName
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/follows",method=RequestMethod.GET)
	public String toBlogFollowsPage(@PathVariable("username") String username,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = usersService.findByName(username);
		Pager<Concern> pager = new Pager<Concern>();
		pager = concernService.getFollowsPager(user.getId(), page==null||page<=0?1:page,pager.getPageSize(), "login", true);
		map.put("pager", pager);
		map.put("type", "follows");
		map.put("user", user);
		map.put("blog", user.getBlogs());
		checkFollow(user.getId(),map);
		return "front/blog-follows";
	}
	
	/**
	 * 用户粉丝
	 * @param blogName
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/fans",method=RequestMethod.GET)
	public String toBlogFansPage(@PathVariable("username") String username,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = usersService.findByName(username);
		Pager<Concern> pager = new Pager<Concern>();
		pager = concernService.getFansPager(user.getId(), page==null||page<=0?1:page,pager.getPageSize(), "login", true);
		map.put("pager", pager);
		map.put("type", "fans");
		map.put("user", user);
		map.put("blog", user.getBlogs());
		checkFollow(user.getId(),map);
		return "front/blog-fans";
	}
	
	/**
	 * 左侧最近访客
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/leftVisits",method=RequestMethod.POST)
	public String leftVisits(@RequestParam Integer id,ModelMap map) {
		Users user = usersService.findById(id);
		if(user!=null) {
			List<Visits> visits = visitsService.getTopVisitsPager(user.getId(), 8);
			map.put("user", user);
			map.put("visits", visits);
		}
		return "front/blog-left-visits";
	}
	
	/**
	 * 博客访问记录
	 * @param blogName
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/visits",method=RequestMethod.GET)
	public String toBlogVisitsPage(@PathVariable("username") String username,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = usersService.findByName(username);
		Pager<Visits> pager = new Pager<Visits>();
		pager = visitsService.getVisitsPager(user.getId(), page==null||page<=0?1:page,pager.getPageSize());
		map.put("pager", pager);
		map.put("user", user);
		map.put("blog", user.getBlogs());
		checkFollow(user.getId(),map);
		return "front/blog-visits";
	}
	
//	@RequestMapping(value="/{blogName}/search",method=RequestMethod.GET)
//	public String toSearchPostPage(@PathVariable("blogName") String blogName,@RequestParam(value="query",required=false) String query,
//							 @RequestParam(value="page",required=false) Integer page,ModelMap map) {
//		Blogs blog = blogsService.findByName(blogName);
//		if(blog!=null) {
//			if(query==null || "".equals(query.trim())) {
//				return "redirect:";
//			}
//			Users user = usersService.findById(blog.getId());
//			Pager<Posts> pager = new Pager<Posts>();
//			pager = postsSearch.query(query, (page==null||page<=0)?1:page, pager.getPageSize(), blog.getId());
//			map.put("jsoupUtils", FreemarkerUtil.userStaticClass("org.cgz.oseye.utils.JsoupUtils"));
//			checkFollow(blog.getId(),map);
//			map.put("pager", pager);
//			map.put("blog", blog);
//			map.put("user", user);
//			pager.addQueryBy("query", query);
//		}
//		return "front/blog";
//	}
	
	
	/**
	 * 判断当前用户是否关注了博主
	 * @param map
	 */
	private void checkFollow(Integer blogId,ModelMap map) {
		Users sessionUser = getSessionUsers();
		if(sessionUser!=null) {
			map.put("sessionUser", sessionUser);
			Concern concern = concernService.loadConcern(blogId, sessionUser.getId());
			if(concern!=null) {
				map.put("concernIsFriends", concern.getIsFriends());
			}
		}
	}
	
	
	private void updatePostViewsNum(Integer id) {
		VisitStatService.start();
		VisitStatService.record((byte) 1, id);
	}
	
	private void updateBlogViewsNum(Integer id) {
		VisitStatService.start();
		VisitStatService.record((byte) 2, id);
	}
}
