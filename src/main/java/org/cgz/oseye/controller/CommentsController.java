package org.cgz.oseye.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Comments;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.CommentsService;
import org.cgz.oseye.utils.FreemarkerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("commentsController")
@RequestMapping("/admin")
public class CommentsController extends BaseController {

	@Resource
	private CommentsService commentsService;
	
	/**
	 * 评论管理页面的显示
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/comments",method=RequestMethod.GET)
	public String toManagePage(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		return "forward:/admin/comments/normal";
	}
	
	/**
	 * 选择显示隐藏的或可见的评论
	 * @param type
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/comments/{type}",method=RequestMethod.GET)
	public String getComments(@PathVariable("type") String type,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Comments> pager = new Pager<Comments>();
		String toPage = "admin/admin-comments";
		if("normal".equals(type.trim())) {
			pager = commentsService.getBlogVisibleComments(user.getId(), (page==null||page<0)?1:page, pager.getPageSize());
		}else if ("trash".equals(type.trim())) {
			pager = commentsService.getBlogInvisibleComments(user.getId(), (page==null||page<0)?1:page, pager.getPageSize());
			toPage = "admin/admin-comments-trash";
		}else if("my".equals(type.trim())) {
			pager = commentsService.getMyComments(user.getId(), (page==null||page<0)?1:page, pager.getPageSize());
			toPage = "admin/admin-comments-my";
		}
		map.put("pager", pager);
		map.put("category", type);
		map.put("jsoupUtils", FreemarkerUtil.userStaticClass("org.cgz.oseye.utils.JsoupUtils"));
		return toPage;
	}
	
	/**
	 * 显示或隐藏评论
	 * @param type
	 * @param commentId
	 * @param response
	 * @param map
	 */
	@RequestMapping(value="/comment/{type}",method=RequestMethod.POST)
	public void showOrHideComment(@PathVariable(value="type") String type,@RequestParam(value="id") Integer commentId,
								  HttpServletResponse response) {
		Users user = getSessionUsers();
		if("show".equals(type.trim())) {
			commentsService.visibleComment(user.getId(),commentId);
		}else if("hide".equals(type.trim())) {
			commentsService.invisibleComment(user.getId(),commentId);
		}
		returnJson(response, true);
	}
	
	/**
	 * 删除评论
	 * @param commentId
	 * @param response
	 * @param map
	 */
	@RequestMapping(value="/comment/del",method=RequestMethod.POST)
	public void delComment(@RequestParam(value="id") Integer commentId,HttpServletResponse response) {
		Users user = getSessionUsers();
		commentsService.deleteComment(user.getId(), commentId);
		returnJson(response, true);
	}
}
