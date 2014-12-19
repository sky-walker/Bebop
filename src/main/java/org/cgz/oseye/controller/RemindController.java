package org.cgz.oseye.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.RemindService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("remindController")
public class RemindController extends BaseController {

	@Resource
	private RemindService remindService;
	
	/**
	 * 查询出最近10条通知
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/remind/last",method=RequestMethod.POST)
	public String getLastRemind(ModelMap map) {
		Users user = getSessionUsers();
		Pager<Feed> pager = new Pager<Feed>();
		if(user!=null) {
			pager = remindService.getLastRemind(user.getId());
		}
		map.put("pager", pager);
		return "front/blog-remind";
	}
	
	/**
	 * 未读通知数
	 * @param response
	 */
	@RequestMapping(value="/remind/unread",method=RequestMethod.POST)
	public void getUnReadRemindCount(HttpServletResponse response) {
		Users user = getSessionUsers();
		long count = 0;
		if(user!=null) {
			count = remindService.getunReadRemindCount(user.getId());
		}
		returnJson(response, true, String.valueOf(count));
	}
	
	/**
	 * 通知分页
	 * @param type
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/reminds",method=RequestMethod.GET)
	public String getReminds(@RequestParam(value="type",required=false) String type,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Feed> pager = new Pager<Feed>();
		String typeRegex = "^[2-5]$";
		if(null!=type && !"".equals(type.trim())) {
			if(type.matches(typeRegex)) {
				Short optType = Short.parseShort(type);
				pager = remindService.getRemindPager(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(),optType);
				pager.addQueryBy("type", type);
				map.put("type", type);
			}
		}else {
			pager = remindService.getRemindPager(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(),null);
			map.put("type", "");
		}
		map.put("pager", pager);
		return "admin/admin-reminds";
	}
}
