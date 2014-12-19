package org.cgz.oseye.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Concern;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.ConcernService;
import org.cgz.oseye.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("concernController")
public class ConcernController extends BaseController {

	@Resource
	private ConcernService concernService;
	@Resource
	private UsersService usersService;
	
	/**
	 * 关注管理页面
	 * @return
	 */
	@RequestMapping(value="/admin/concern",method=RequestMethod.GET)
	public String toConcernManagePage() {
		return "forward:/admin/concern/follows";
	}
	
	/**
	 *  关注,粉丝,好友的查询
	 * @param type
	 * @param page
	 * @param sortby(排序方式：update 更新时间,time 关注时间,login 登录时间)
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/concern/{type}",method=RequestMethod.GET)
	public String toConcernPager(@PathVariable("type") String type,@RequestParam(value="page",required=false) Integer page,
								 @RequestParam(value="sortby",required=false,defaultValue="update") String sortby,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Concern> pager = new Pager<Concern>();
		if("follows".equals(type.trim())) {			//查询关注者
			pager = concernService.getFollowsPager(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(), sortby,true);
		}else if("fans".equals(type.trim())) {		//查询粉丝
			pager = concernService.getFansPager(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(), sortby,true);
		}else if("friends".equals(type.trim())) {	//查询好友(互为关注)
			pager = concernService.getFriendsPager(user.getId(), (page==null||page<0)?1:page, pager.getPageSize(), sortby,true);
		}
		pager.addQueryBy("sortby", sortby);
		map.put("pager", pager);
		map.put("sortby", sortby);
		map.put("type", type);
		return "admin/admin-concern-"+type;
	}
	
	/**
	 * 添加或取消关注
	 * @param type(true:添加关注,false:取消)
	 * @param map
	 */
	@RequestMapping(value="/concern/followopt",method=RequestMethod.POST)
	public void addOrDelFollow(@RequestParam(value="id",required=false) Integer uid,
							   @RequestParam(value="type",required=false) boolean type,ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user==null) {
			returnJsonNum(response, 0,"请先登录!");
			return;
		}
		if(user.getId().equals(uid)) {
			returnJsonNum(response,2,"无法添加自己为关注对象!");
			return;
		}
		Users follow = usersService.getById(uid);
		if(follow!=null) {
			Concern concern = new Concern(follow, user);
			if(type) {
				concernService.addConcern(concern);
				returnJsonNum(response, 1,"已添加关注");
			}else {
				concernService.delConcern(uid, user.getId());
				returnJsonNum(response, 1,"已取消关注");
			}
		}else {
			returnJsonNum(response,2,"你关注的对象不存在!");
			return;
		}
	}
	
	/**
	 * jbox添加关注
	 * @return
	 */
	@RequestMapping(value="/concern/box/add",method=RequestMethod.POST)
	public String prepareAddConcern(ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user==null) {
			map.put("islogin", "0");
		}
		return "/share/ajax-concern-add-box";
	}
	
	/**
	 * jbox添加关注
	 * @return
	 */
	@RequestMapping(value="/concern/box/adddo",method=RequestMethod.POST)
	public void addConcernByNickName(@RequestParam(value="name",required=false) String name,ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user==null) {
			returnJson(response, false, "您尚未登录,请先登录!");
			return;
		}
		if(name==null || "".equals(name.trim())) {
			returnJson(response, false, "昵称不能为空!");
			return;
		}
		Users users = usersService.findByNickName(name.trim());
		if(users==null) {
			returnJson(response, false, "用户 '"+name.trim()+"' 不存在!");
			return;
		}
		if(user.getId().equals(users.getId())) {
			returnJson(response, false, "无法添加自己为好友!");
			return;
		}
		Concern concern0 = concernService.loadConcern(users.getId(), user.getId());
		if(concern0!=null) {
			returnJson(response, false, "用户 '"+name.trim()+"' 已是你的好友!");
			return;
		}
		Concern concern = new Concern(users,user);
		concernService.addConcern(concern);
		returnJson(response, true);
	}
}
