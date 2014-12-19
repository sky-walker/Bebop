package org.cgz.oseye.service.impl;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.QLCompare;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.IndexService;
import org.springframework.stereotype.Service;

@Service("indexService")
public class IndexServiceImpl implements IndexService{
	
	private int adminId = 1;
	
	@Resource
	private BaseDao baseDao;
	
	@Override
	public Pager<Users> getUsersPage(int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Users.class).where("id",QLCompare.NEQ).and("status").desc("thisLoginTime").setQlParams(adminId,SystemConstant.USERS_ACTIVE), pageNo, pageSize);
		//return baseDao.getScrollData(QLBuilder.select(Users.class).where("status").desc("thisLoginTime").setQlParams(SystemConstant.USERS_ACTIVE), pageNo, pageSize);
	}
	
	
	public Users getAdminUser() {
		return baseDao.find(Users.class, adminId);
	}
	
	/**
	 * 公告[可见的]
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Posts> getPublicPosts(int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where("blogs.id").and(Posts.VISIBLE).desc(Posts.ASTOP).desc(Posts.ID).setQlParams(adminId,SystemConstant.POSTS_VISIBLE),pageNo,pageSize);
	}
	
	@Override
	public Pager<Posts> getPostsPage(int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where("blogs.id",QLCompare.NEQ).desc("id").setQlParams(adminId), pageNo, pageSize);
	}

	@Override
	public Pager<Feed> getFeedsPage(int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Feed.class).desc("id"), pageNo, pageSize);
	}
}
