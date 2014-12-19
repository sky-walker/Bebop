package org.cgz.oseye.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.model.UserSettings;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.UserSettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("userSettingsController")
public class UserSettingsController extends BaseController{

	private static final String PRIVACY_BLOG_ADD = "privacy_blog_add";
	private static final String PRIVACY_COMMENT_ADD = "privacy_comment_add";
	private static final String PRIVACY_COMMENT_REPLY = "privacy_comment_reply";
	private static final String PRIVACY_CONCERN_ADD = "privacy_concern_add";
	private static final String PRIVACY_BLOG_POST_FAVORITE = "privacy_blog_post_favorite";
	private static final String PRIVACY_USER_STATUS = "privacy_user_status";
	
	@Resource
	private UserSettingsService userSettingsService;
	
	/**
	 * 隐私设置管理页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/privacy",method=RequestMethod.GET)
	public String toPrivacyManagePager(ModelMap map) {
		Users user = getSessionUsers();
		UserSettings userSettings = userSettingsService.findUserSettings(user.getId());
		map.put("userSettings", userSettings);
		return "admin/admin-privacy";
	}
	
	/**
	 * 隐私设置
	 * @param type	隐私类型
	 * @param value 0(隐藏) 或 1(显示)
	 * @param respons
	 */
	@RequestMapping(value="/admin/privacy/{type}",method=RequestMethod.POST)
	public void pricacyManage(@PathVariable("type") String type,@RequestParam(value="value") short value,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null && value>=0 && value<2) {
			UserSettings userSettings = userSettingsService.findUserSettings(user.getId());
			if(userSettings!=null) {
				if(PRIVACY_BLOG_ADD.equals(type)) {
					userSettings.setPrivacy_blog_add(value);
				}else if(PRIVACY_COMMENT_ADD.equals(type)) {
					userSettings.setPrivacy_comment_add(value);
				}else if(PRIVACY_COMMENT_REPLY.equals(type)) {
					userSettings.setPrivacy_comment_reply(value);
				}else if(PRIVACY_CONCERN_ADD.equals(type)) {
					userSettings.setPrivacy_concern_add(value);
				}else if(PRIVACY_BLOG_POST_FAVORITE.equals(type)) {
					userSettings.setPrivacy_blog_post_favorite(value);
				}else if(PRIVACY_USER_STATUS.equals(type)) {
					userSettings.setPrivacy_user_status(value);
				}
				userSettingsService.modifyUserSettings(userSettings);
				//需要更新session中user的userSettings
				user.setUserSettings(userSettings);
				returnJson(response, true);
			}
		}
	}
}
