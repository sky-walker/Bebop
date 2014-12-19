package org.cgz.oseye.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.model.Visits;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.UsersService;
import org.cgz.oseye.service.VisitsService;
import org.cgz.oseye.utils.SpringContextUtil;

public class VisitsRecordTag extends TagSupport {

	private static final long serialVersionUID = 4323439309413089665L;

	private String sessionUid;
	
	private String blogId;

	public String getSessionUid() {
		return sessionUid;
	}

	public void setSessionUid(String sessionUid) {
		this.sessionUid = sessionUid;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	
	@Override
	public int doStartTag() throws JspException {
		int suid = Integer.parseInt(sessionUid);
		int bid = Integer.parseInt(blogId);
		if(suid!=bid) {
			UsersService usersService = (UsersService) SpringContextUtil.getBean("userService");
			BlogsService blogsService = (BlogsService) SpringContextUtil.getBean("blogsService");
			Users sessionUser = usersService.findById(suid);
			Blogs blogs = blogsService.findById(bid);
			if(blogs!=null && sessionUser!=null) {
				Visits visits = new Visits();
				visits.setBlogs(blogs);
				visits.setVisitor(sessionUser);
				VisitsService visitsService = (VisitsService) SpringContextUtil.getBean("visitsService");
				visitsService.addVisits(visits);
				System.out.println(blogs.getName()+","+sessionUser.getName()+"来访啦...");
			}
		}
		return super.doStartTag();
	}
}
