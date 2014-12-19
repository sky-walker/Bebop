package org.cgz.oseye.controller;

import javax.annotation.Resource;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("indexController")
public class IndexController extends BaseController{

	@Resource
	IndexService indexService;
	
	@RequestMapping(value="/index/unsupport-browser",method=RequestMethod.GET)
	public String checkBroswer() {
		return "index/unsupport-browser";
	}
	
	@RequestMapping(value="/error/404",method=RequestMethod.GET)
	public String error404() {
		return "index/404";
	}
	
	@RequestMapping(value="/error/500",method=RequestMethod.GET)
	public String error500() {
		return "index/404";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(ModelMap map) {
		Users users = indexService.getAdminUser();
		//公告
		Pager<Posts> publicPosts = new Pager<Posts>();
		publicPosts = indexService.getPublicPosts(0, 15);
		
		//---新加代码
		Pager<Users> usersPage = new Pager<Users>();
		usersPage = indexService.getUsersPage(1, usersPage.getPageSize());
		map.put("usersPage", usersPage);
		
		Pager<Posts> postsPage = new Pager<Posts>();
		postsPage = indexService.getPostsPage(1, postsPage.getPageSize());
		map.put("postsPage", postsPage);
		
		Pager<Feed> feedsPage = new Pager<Feed>();
		feedsPage = indexService.getFeedsPage(1, feedsPage.getPageSize());
		map.put("feedsPage", feedsPage);
		
		//---
		
		map.put("publicPosts", publicPosts);
		map.put("admin",users);
		return "index/index";
	}
	
	@RequestMapping(value="/index/users",method=RequestMethod.GET)
	public String getUsersScrollData(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Pager<Users> pager = new Pager<Users>();
		pager = indexService.getUsersPage((page==null||page<0)?1:page, pager.getPageSize());
		map.put("usersPage", pager);
		return "index/index-users";
	}
	
	@RequestMapping(value="/index/posts",method=RequestMethod.GET)
	public String getPostsScrollData(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Pager<Posts> pager = new Pager<Posts>();
		pager = indexService.getPostsPage((page==null||page<0)?1:page, pager.getPageSize());
		map.put("postsPage", pager);
		return "index/index-posts";
	}
	
	@RequestMapping(value="/index/feeds",method=RequestMethod.GET)
	public String getFeedsScrollData(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Pager<Feed> pager = new Pager<Feed>();
		pager = indexService.getFeedsPage((page==null||page<0)?1:page, pager.getPageSize());
		map.put("feedsPage", pager);
		return "index/index-feeds";
	}
}
