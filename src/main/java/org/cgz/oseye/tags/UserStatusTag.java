package org.cgz.oseye.tags;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.UserSettings;
import org.cgz.oseye.model.UserStatus;
import org.cgz.oseye.service.UserSettingsService;
import org.cgz.oseye.service.UserStatusService;
import org.cgz.oseye.utils.SpringContextUtil;

/**
 * 显示用户的在线状态
 * @author Administrator
 */
public class UserStatusTag extends TagSupport {

	private static final long serialVersionUID = -8491767080258415885L;
	
	private String uid;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
//	<span class='badge userstatus-offline'><i class='icon-user icon-white'></i> 现在离线</span>
//	
//	<span class='badge badge-success userstatus-online' ><i class='icon-user icon-white'></i> 现在在线</span>
//	
//	<a href='' target='_blank' title=''><span class='badge badge-info userstaus-read' ><i class='icon-eye-open icon-white'></i> 在看博客</span></a>
//	
//	<a href='' title=''>
//  	<span class='badge badge-warning userstaus-search' ><i class='icon-search icon-white'></i> 搜索文章</span></a>
//			
//		
//	
//	
//	<span class='badge badge-important userstaus-admin' ><i class='icon-wrench icon-white'></i> 管理博客</span>
//	
//	<span class='badge badge-important userstaus-admin-message' ><i class='icon-envelope icon-white'></i> 阅读消息</span>
//	
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut(); 
		try {
			if(uid!=null && !"".equals(uid)) {
				Integer userId = Integer.parseInt(uid);
				if(userId>0) {
					UserStatusService userStatusService = (UserStatusService) SpringContextUtil.getBean("userStatusService");
					UserStatus userStatus = userStatusService.getUserStatus(userId);
					if(userStatus==null) {
						out.print("<span class='badge userstatus-offline' title='用户不在线'><i class='icon-user icon-white'></i> 现在离线</span>");
					}else {
						UserSettingsService userSettingsService = (UserSettingsService) SpringContextUtil.getBean("userSettingsService");
						UserSettings userSettings = userSettingsService.findUserSettings(userId);
						String visittip = userStatus.getVisittip();
						String visiturl = userStatus.getVisiturl();
						short status = userStatus.getStatus();
						StringBuffer sb = new StringBuffer();
						if(userSettings!=null && userSettings.getPrivacy_user_status()==SystemConstant.PRIVACY_USER_STATUS_HIDE) {
							sb.append("<span class='badge badge-success userstatus-online' title='用户已在线'><i class='icon-user icon-white'></i> 现在在线</span>");
						}else {
							//阅读博客
							if(status == SystemConstant.VISITS_STATUS_READBLOG) {
								sb.append("<a href='").append(visiturl).append("' target='_blank' title='").append(visittip).append("'>")
								  .append("<span class='badge badge-info userstaus-read' ><i class='icon-eye-open icon-white'></i> 在看博客</span></a>");
							//搜索文章
							}else if(status == SystemConstant.VISITS_STATUS_SEARCH_POST) {
								sb.append("<a href='").append(visiturl).append("' target='_blank' title='").append(visittip).append("'>")
								  .append("<span class='badge badge-warning userstaus-search' ><i class='icon-search icon-white'></i> 搜索文章</span></a>");
							//管理博客
							}else if(status == SystemConstant.VISITS_STATUS_ADMIN) {
								sb.append("<span class='badge badge-important userstaus-admin' title='正在管理博客'><i class='icon-wrench icon-white'></i> 管理博客</span>");
							//阅读消息
							}else if(status==SystemConstant.VISITS_STATUS_ADMIN_MESSAGE) {
								sb.append("<span class='badge badge-important userstaus-admin-message' title='正在阅读私信'><i class='icon-envelope icon-white'></i> 阅读消息</span>");
							//在线状态
							}else {
								sb.append("<span class='badge badge-success userstatus-online' title='用户已在线'><i class='icon-user icon-white'></i> 现在在线</span>");
							}
						}
						out.print(sb.toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return super.doStartTag();
	}
}
