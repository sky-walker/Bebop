package org.cgz.oseye.controller;

import javax.annotation.Resource;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.FeedService;
import org.cgz.oseye.utils.FreemarkerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedController extends BaseController{

	@Resource
	private FeedService feedService;
	
	
	@RequestMapping(value="/admin/home",method=RequestMethod.GET)
	public String toFeedsManagePage(){
		return "forward:/admin/home/follows";
	}
	
	@RequestMapping(value="/admin/home/{type}",method=RequestMethod.GET)
	public String getFeeds(@PathVariable("type") String type,
						   @RequestParam(value="optType",required=false) Short optType,
						   @RequestParam(value="page",required=false) Integer page,
						   ModelMap map) {
		Users user = getSessionUsers();
		Pager<Feed> pager = new Pager<Feed>();
		if(null==optType || "".equals(optType) || optType <0 || optType >5) {
			optType = null;
		}
		if("follows".equals(type)) {
			pager = feedService.getFollowFeeds(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(),optType);
		}else if("my".equals(type)) {
			pager = feedService.getMyFeeds(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(),optType);
		}else if("all".equals(type)) {
			pager = feedService.getAllFeeds((page==null||page<0)?1:page, pager.getPageSize(),optType);
		}
		pager.addQueryBy("optType", optType);
		map.put("jsoupUtils", FreemarkerUtil.userStaticClass("org.cgz.oseye.utils.JsoupUtils"));
		map.put("pager", pager);
		map.put("type",type);
		map.put("optType", optType);
		return "admin/admin-home";
	}
}
