package org.cgz.oseye.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Private_Message;
import org.cgz.oseye.model.Talk;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.Private_MessageService;
import org.cgz.oseye.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("messageController")
public class MessageController extends BaseController{

	@Resource
	private Private_MessageService private_MessageService;
	@Resource
	private UsersService usersService;
	
	@RequestMapping(value="/admin/messages",method=RequestMethod.GET)
	public String toMessageManagePage() {
		return "forward:/admin/messages/private";
	}
	
	/**
	 * 私信联系人列表
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/messages/private",method=RequestMethod.GET)
	public String toPrivateMessageManagePage(@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Talk> pager = new Pager<Talk>(); 
		pager =	private_MessageService.getTalkPager(user.getId(),(page==null||page<0)?1:page, pager.getPageSize());
		map.put("pager", pager);
		return "/admin/admin-private-messages";
	}
	
	
	/**
	 * 私信来往记录
	 * @param friendId
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/messages/private/detail/{friendId}",method=RequestMethod.GET)
	public String toPrivateMessageDetailPage(@PathVariable("friendId") Integer friendId,@RequestParam(value="page",required=false) Integer page,ModelMap map) {
		Users user = getSessionUsers();
		Users friend = usersService.findById(friendId);
		Pager<Private_Message> pager = new Pager<Private_Message>();
		pager = private_MessageService.getPrivateMessagePager(user.getId(), friendId, (page==null||page<0)?1:page, pager.getPageSize());
		map.put("pager", pager);
		map.put("friend", friend);
		return "/admin/admin-private-messages-detail";
	}
	
	/**
	 * 删除私信
	 * @param pmId
	 * @param response
	 */
	@RequestMapping(value="/admin/messages/private/del",method=RequestMethod.POST)
	public void deletePrivateMessage(@RequestParam(value="id") Integer pmId,HttpServletResponse response) {
		Users user = getSessionUsers();
		private_MessageService.delMessage(user.getId(), pmId);
		returnJson(response, true);
	}
	
	/**
	 * 删除和好友的所有私信记录
	 * @param friendId
	 * @param response
	 */
	@RequestMapping(value="/admin/messages/private/delbyfriend",method=RequestMethod.POST)
	public void deletePrivateMessageByFriend(@RequestParam(value="id") Integer friendId,HttpServletResponse response) {
		Users user = getSessionUsers();
		private_MessageService.delMessagesByFriend(user.getId(), friendId);
		returnJson(response, true);
	}
	
	/**
	 * jbox获取消息发送页面
	 * @return
	 */
	@RequestMapping(value="/messages/private/send",method=RequestMethod.POST)
	public String prepareSentMsg(@RequestParam(value="fid",required=false) Integer fid,ModelMap map) {
		Users user = getSessionUsers();
		if(user==null) {
			map.put("islogin", "0");
		}
		if(fid!=null && fid>0) {
			Users friend = usersService.findById(fid);
			if(friend!=null) {
				map.put("friend", friend);
			}
		}
		return "/share/ajax-sendmsg-box";
	}
	
	@RequestMapping(value="/messages/private/unReadCount",method=RequestMethod.POST)
	public void getUnReadPMCount(@RequestParam(value="id",required=false) Integer id,ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			returnJson(response, true, String.valueOf(private_MessageService.getUnReadPrivateMessagCount(id)));
		}
	}
	
	/**
	 * 根据昵称发送消息
	 * @param name
	 * @param content
	 * @param response
	 */
	@RequestMapping(value="/messages/private/dosend",method=RequestMethod.POST)
	public void sendMsgByNickName(@RequestParam(value="nickname",required=false) String nickname,
						@RequestParam(value="content",required=false) String content,
						HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user==null) {
			returnJson(response, false, "您尚未登录,请先登录!");
			return;
		}
		if(nickname==null || "".equals(nickname.trim())) {
			returnJson(response, false, "用户不能为空!");
			return;
		}
		if(content==null || "".equals(content.trim())) {
			returnJson(response, false, "消息内容不能为空!");
			return;
		}
		Users receiver = usersService.findByNickName(nickname.trim());
		if(receiver==null) {
			returnJson(response, false, "用户 '"+nickname+"' 不存在!");
			return;
		}
		if(content.length()>250) {
			returnJson(response, false, "消息长度不得大于250!");
			return;
		}
		Users sender = getSessionUsers();
		if(sender.getId().intValue()==receiver.getId().intValue()) {
			returnJson(response, false, "无法给自己发送消息!");
			return;
		}
		Private_Message private_Message = new Private_Message();
		private_Message.setSender(sender);
		private_Message.setReceiver(receiver);
		private_Message.setContent(content);
		private_MessageService.addMessage(private_Message);
		returnJson(response, true);
	}
}
