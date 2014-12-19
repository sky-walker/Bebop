package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;


/**
 * 首页
 * @author Administrator
 */
public interface IndexService {
	
	public Pager<Users> getUsersPage(int pageNo,int pageSize);
	
	public Pager<Posts> getPostsPage(int pageNo,int pageSize);
	
	public Pager<Feed> getFeedsPage(int pageNo,int pageSize);
	
	public Pager<Posts> getPublicPosts(int pageNo, int pageSize);
	
	public Users getAdminUser();
}
