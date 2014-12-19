package org.cgz.oseye.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.model.Visits;
import org.cgz.oseye.service.VisitsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("visitsController")
public class VisitsController extends BaseController{
	
	@Resource
	VisitsService visitsService;
	
	/**
	 * 访客记录
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/visits",method=RequestMethod.GET)
	public String toVisitsManagePage(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Visits> pager = new Pager<Visits>(); 
		pager = visitsService.getVisitsPager(user.getId(), (page==null||page<0)?1:page, pager.getPageSize());
		map.put("pager", pager);
		return "admin/admin-visits";
	}
	
	/**
	 * 足迹
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/foots",method=RequestMethod.GET)
	public String toVisitsFootsManagePage(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Visits> pager = new Pager<Visits>(); 
		//设置pageSize为40
		pager = visitsService.getFootsPager(user.getId(), (page==null||page<0)?1:page, 40);
		map.put("pager", pager);
		return "admin/admin-foots";
	}
	
	/**
	 * 删除访客记录
	 * @param id
	 * @param response
	 */
	@RequestMapping(value="/admin/visit/del",method=RequestMethod.POST)
	public void visitsDel(@RequestParam(value="id",required=false) int id,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			visitsService.removeVisitor(user.getId(), id);
		}
		returnJson(response, true);
	}
}
