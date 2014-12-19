package org.cgz.oseye.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.DraftsService;
import org.cgz.oseye.service.Post_categoriesService;
import org.cgz.oseye.service.PostsService;
import org.cgz.oseye.utils.JsoupUtils;
import org.cgz.oseye.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller("postsController")
@RequestMapping("/admin")
public class PostsController extends BaseController {

	@Resource
	private PostsService postsService;
	@Resource
	private Post_categoriesService postCategoriesService;
	@Resource
	private DraftsService draftsService;
	@Resource
	private BlogsService blogsService;
	
	/*****************************文章添加开始******************************/
	
	/**
	 * 文章添加或编辑页面
	 */
	@RequestMapping(value="/post/{toPage}",method = RequestMethod.GET)
	public String toPostNewOrEditPage(@PathVariable("toPage") String toPage,
									  @RequestParam(value="id",required=false) Integer postId,
									  ModelMap map) {
		Users user = getSessionUsers();
		if(user!=null) {
			List<Post_categories> categories = null;
			//如果是添加文章页面
			if("new".equals(toPage)) {
				categories = postCategoriesService.getCategories(user.getId(), -1, -1).getResultList();
				map.put("categories", categories);
			}
			//如果是修改文章页面
			if("edit".equals(toPage) && postId>=0) {
				Posts post = postsService.getPost(user.getId(), postId);
				if(post!=null) {
					map.put("post", post);
					categories = postCategoriesService.getCategories(user.getId(), -1, -1).getResultList();
					map.put("categories", categories);
				}
			}
 		}
		return "admin/admin-post-"+toPage;
	}
	
	/**
	 * 添加或修改文章
	 */
	@RequestMapping(value="/post/{type}-do",method = RequestMethod.POST)
	public void post_new_edit_do (@PathVariable("type") String type,
								  @RequestParam(value="id",required=false) Integer postId,
								  @RequestParam(value="title",required=false) String title,
								  @RequestParam(value="category",required=false) Integer category,
								  @RequestParam(value="content",required=false) String content,
								  @RequestParam(value="postType",required=false) Integer postType,
								  @RequestParam(value="reLink",required=false) String reLink,
								  @RequestParam(value="musicUrl",required=false) String musicUrl,
								  @RequestParam(value="tags",required=false) String tags,
								  @RequestParam(value="visible",required=false) Integer visible,
								  @RequestParam(value="canComment",required=false) Integer canComment,
								  @RequestParam(value="asTop",required=false,defaultValue = "0") Integer asTop,
								  HttpServletResponse response,HttpServletRequest request,ModelMap map) {
		Users user = getSessionUsers();
		String errMsg = checkPostNew(title, category, content,postType,reLink,visible,canComment,musicUrl,asTop);
		if(errMsg!=null) {
			returnJson(response, errMsg);
			return;
		}
		if(user!=null&&category!=null) {
			title = HtmlUtils.htmlEscape(title.trim());
			Post_categories postCategory = postCategoriesService.getCategory(category);
			if("new".equals(type) || "draft-to-post".equals(type)) {				//发表文章
				Posts post = new Posts(postCategory, user.getBlogs(), title.trim(),JsoupUtils.cleanHtml(content, getDomain(request)),visible.shortValue(), canComment.shortValue(),postType.shortValue(),reLink,asTop.shortValue());
				postsService.addPost(post);
				//如果文章是由草稿而来
				if("draft-to-post".equals(type)&&postId>=0) {
					draftsService.delDraft(user.getId(), postId);
					//更新session中的dratsNum
					Blogs blog = user.getBlogs();
					blog.setDraftsNum(blogsService.getDraftNum(user.getId()));
					user.setBlogs(blog);
					getSession().setAttribute(SystemConstant.SESSIONUSER, user);
				}
			}
			if("edit".equals(type)&&postId!=null) {	//修改文章
				Posts post = postsService.getPost(postId);
				post.setTitle(title.trim());
				post.setPost_categories(postCategory);
				post.setContent(content);
				post.setPostType(postType.shortValue());
				post.setCanComment(canComment.shortValue());
				post.setVisible(visible.shortValue());
				post.setAsTop(asTop.shortValue());
				post.setMusicUrl(musicUrl);
				post.setReLink(reLink);
				postsService.modifyPost(user.getId(), post);
			}
			returnJson(response, true);
		}
	}
	
	/*****************************文章添加结束******************************/
	
	
	
	
	/*****************************文章管理开始******************************/
	/**
	 * 文章管理页面
	 * @param page
	 * @param categoryId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/posts",method = RequestMethod.GET)
	public String toManagePage(@RequestParam(value="page",required=false) Integer page,
							   @RequestParam(value="category",required=false) Integer categoryId,
							   ModelMap map) {
		Users user = getSessionUsers();
		if(page==null || page <=0) {
			page=1;
		}
		Pager<Posts> pager = new Pager<Posts>();
		if(categoryId!=null && categoryId>0) {
            pager = postsService.getPostsByCategory(categoryId, page, pager.getPageSize());
			pager.addQueryBy("category", categoryId);
			map.put("categoryId", categoryId);
			Post_categories category = postCategoriesService.getCategory(categoryId);
			if(category!=null) {
				String categoryName = category.getName();
				if(categoryName!=null && !"".equals(categoryName)) {
					map.put("categoryName", categoryName);
				}
			}
		}else {
            pager = postsService.getPosts(user.getId(), page, pager.getPageSize());
		}
		List<Post_categories> categories = postCategoriesService.getCategories(user.getBlogs().getId(), -1, -1).getResultList();
		map.put("categories", categories);
		map.put("pager", pager);
		return "admin/admin-posts";
	}
	
	/**
	 * 删除文章
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/del",method = RequestMethod.POST)
	public void post_del(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			postsService.deletePost(user.getId(), postId);
			returnJson(response, true);
		}
	}
	
	/**
	 * 置顶文章
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/top",method = RequestMethod.POST)
	public void post_top(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		postsService.topPost(user.getId(), postId);
		returnJson(response, true);
	}
	
	/**
	 * 取消置顶
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/untop",method = RequestMethod.POST)
	public void post_untop(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		postsService.unTopPost(user.getId(), postId);
		returnJson(response, true);
	}
	
	/**
	 * 锁定
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/lock",method = RequestMethod.POST)
	public void post_lock(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		postsService.lockPost(user.getId(), postId);
		returnJson(response, true);
	}
	
	/**
	 * 取消锁定
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/unlock",method = RequestMethod.POST)
	public void post_unlock(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		postsService.unLockPost(user.getId(), postId);
		returnJson(response, true);
	}
	
	/**
	 * 文章可见
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/visible",method = RequestMethod.POST)
	public void post_visible(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		postsService.visiblePost(user.getId(), postId);
		returnJson(response, true);
	}
	
	/**
	 * 文章可见
	 * @param postId
	 * @param response
	 */
	@RequestMapping(value="/posts/unvisible",method = RequestMethod.POST)
	public void post_unvisible(@RequestParam(value="id") Integer postId,HttpServletResponse response) {
		Users user = getSessionUsers();
		postsService.unvisiblePost(user.getId(), postId);
		returnJson(response, true);
	}
	
	/*****************************文章管理结束******************************/
	
	/**
	 * 服务器端表文章添加校验
	 * @param title
	 * @param category
	 * @param content
	 * @return
	 */
	protected String checkPostNew(String title,Integer category,String content,Integer postType,String reLink,Integer  visible,Integer canComment,String musicUrl,Integer asTop) {
		if(title==null || "".equals(title.trim())) {
			return returnJsonString(false, "文章标题不能为空!");
		}
		if(title!=null && !"".equals(title.trim()) && title.trim().length()>100) {
			return returnJsonString(false, "文章标题长度不能超过100!");
		}
		if(category==null || category<0) {
			return returnJsonString(false, "请添加文章的分类!");
		}
		if(content==null || "".equals(content.trim())) {
			return returnJsonString(false, "文章内容不能为空!");
		}
		
		if(postType!=null && postType==SystemConstant.POSTS_RELINK && (reLink==null || "".equals(reLink.trim()))) {
			if(!StringUtils.checkUrl(reLink)) {
				return returnJsonString(false, "请输入正确的转载原文的链接!");
			}
		}
		
		if(postType==null || postType<SystemConstant.POSTS_ORIGINAL || postType>SystemConstant.POSTS_RELINK) {
			return returnJsonString(false, "请选择正确的文章类别!");
		}
		
		if(visible==null || visible>SystemConstant.POSTS_VISIBLE || visible<SystemConstant.POSTS_INVISIBLE) {
			return returnJsonString(false, "请选择正确的隐私类别!");
		}
		
		if(canComment==null || canComment<SystemConstant.POSTS_NO_COMMENT|| canComment>SystemConstant.POSTS_CAN_COMMENT) {
			return returnJsonString(false, "请选择正确的评论设置!");
		}
		
		if(musicUrl!=null && !"".equals(musicUrl.trim()) && !StringUtils.checkUrl(musicUrl) ) {
			return returnJsonString(false, "请输入正确的音乐链接地址!");
		}
		
		if(asTop!=null && (asTop<SystemConstant.POSTS_NOT_AS_TOP || asTop>SystemConstant.POSTS_AS_TOP)) {
			return returnJsonString(false, "请选择正确的置顶方式!");
		}
		return null;
	}
}
