package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Feed;

public interface RemindService {

	/**
	 * 查询出用户最近的前10条通知
	 * @param userId
	 * @return
	 */
	public Pager<Feed> getLastRemind(Integer userId);
	
	/**
	 * 查询出关于用户的通知
	 * @param userId
	 * @return
	 */
	public Pager<Feed> getRemindPager(Integer userId,Integer pageNo,Integer pageSize,Short type);
	
	/**
	 * 查询出未读通知的数量
	 * @param userId
	 * @return
	 */
	public long getunReadRemindCount(Integer userId);
	
	/**
	 * 从缓存中清除未读消息数
	 * @param uid
	 */
	public void removeUnReadRemindCounFromCache(Integer uid);
}
