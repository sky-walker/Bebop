package org.cgz.oseye.tags;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.cgz.oseye.service.RemindService;
import org.cgz.oseye.utils.SpringContextUtil;

/**
 * 显示用户未读消息数
 * @author Administrator
 */
public class UnReadRemindCountTag extends TagSupport{

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
			RemindService remindService = (RemindService) SpringContextUtil.getBean("remindService");
			Long unReadRemindCount = remindService.getunReadRemindCount(uid);
			try {
				if(unReadRemindCount>0) {
					out.write("<a id='remind_count' oldtitle='没有未读消息' class='remind_clicked'><span>"+unReadRemindCount+"</span></a>");
				}else {
					out.write("<a id='remind_count' class='' title='没有未读消息'>0</a>");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doStartTag();
	}
}
