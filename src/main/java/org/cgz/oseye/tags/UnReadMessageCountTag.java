package org.cgz.oseye.tags;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.cgz.oseye.service.Private_MessageService;
import org.cgz.oseye.utils.SpringContextUtil;

/**
 * 显示用户未读消息数
 * @author Administrator
 */
public class UnReadMessageCountTag extends TagSupport{

	private static final long serialVersionUID = 8458534116306369105L;

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut(); 
		Integer uid = Integer.parseInt(id);
		if(uid>0) {
			Private_MessageService private_MessageService = (Private_MessageService) SpringContextUtil.getBean("private_MessageService");
			Long unReadMessageCount = private_MessageService.getUnReadPrivateMessagCount(uid);
			try {
				if(unReadMessageCount>0) {
					out.write("<a href='/admin/messages' target='_blank' style='padding-left:10px;padding-right:5px;'><span style='color: #990000;font-size:13px;'><img src='/images/new.gif' /> 您有<strong>"+unReadMessageCount+"</strong>条新消息</span></a>");
				}else {
					out.write("<a href='/admin/messages' style='padding-left:10px;padding-right:5px;'><i class='icon-envelope'></i>&nbsp;站内消息(0)</a>");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doStartTag();
	}
}
