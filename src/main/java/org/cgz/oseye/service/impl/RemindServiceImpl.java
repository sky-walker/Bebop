package org.cgz.oseye.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.cache.CacheBase;
import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.service.RemindService;
import org.springframework.stereotype.Service;

@Service("remindService")
public class RemindServiceImpl implements RemindService{

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 查询出用户最近的前10条通知
	 * @param userId
	 * @return
	 */
	public Pager<Feed> getLastRemind(Integer userId) {
		List<Feed> feeds = baseDao.getListData(QLBuilder.select(Feed.class).where("whose.id").and("isRemind").desc("optTime").setQlParams(userId,SystemConstant.FEED_IS_REMIND), 10);
		Pager<Feed> pager = new Pager<Feed>();
		pager.setPageSize(10);
		pager.setResultList(feeds);
		pager.setTotalRecords(feeds.size());
		readReminds(userId, pager);
		return pager;
	}
	
	/**
	 * 查询出关于用户的通知
	 * @param userId
	 * @return
	 */
	public Pager<Feed> getRemindPager(Integer userId,Integer pageNo,Integer pageSize,Short type) {
		Pager<Feed> pager = null;
		if(type!=null) {
			pager = baseDao.getScrollData(QLBuilder.select(Feed.class).where("whose.id").and("isRemind").and("optType").desc("optTime").setQlParams(userId,SystemConstant.FEED_IS_REMIND,type), pageNo,pageSize);
		}else {
			pager = baseDao.getScrollData(QLBuilder.select(Feed.class).where("whose.id").and("isRemind").desc("optTime").setQlParams(userId,SystemConstant.FEED_IS_REMIND), pageNo,pageSize);
		}
		readReminds(userId, pager);
		return pager;
	}
	
	/**
	 * 查询出未读通知的数量
	 * @param userId
	 * @return
	 */
	public long getunReadRemindCount(Integer userId) {
		Object unReadRemindCount = CacheBase.getInstance().get(SystemConstant.CACHE_UNREADREMINDCOUNT_REGION, String.valueOf(userId));
		if(unReadRemindCount==null) {
			unReadRemindCount = baseDao.getCountByWhere(QLBuilder.count(Feed.class).where("whose.id").and("isRemind").and("readStatus").setQlParams(userId,SystemConstant.FEED_IS_REMIND,SystemConstant.FEED_STATUS_UNREAD));
			CacheBase.getInstance().put(SystemConstant.CACHE_UNREADREMINDCOUNT_REGION, String.valueOf(userId), (Long)unReadRemindCount);
		}
		return (Long) unReadRemindCount;
	}
	
	/**
	 * 批量更新通知的阅读状态
	 * @param pager
	 */
	public void readReminds(Integer userId,Pager<Feed> pager) {
		List<Feed> list = pager.getResultList();
		StringBuffer ids = new StringBuffer();
		for(int i=0;i<list.size();i++) {
			Feed feed = list.get(i);
			//必须消息接收者是当前用户,同时消息是未读状态,才符合更新条件.
			if(feed.getReadStatus()==SystemConstant.FEED_STATUS_UNREAD) {
				ids.append(","+feed.getId());
			}
		}
		if(ids.length()>0) {
			ids.deleteCharAt(0);
			baseDao.update(QLBuilder.queryByHql("update Feed o set o.readStatus=? where o.id in("+ids.toString()+")").setQlParams(SystemConstant.FEED_STATUS_READ));
			removeUnReadRemindCounFromCache(userId);
		}
	}
	
	/**
	 * 从缓存中清除未读消息数
	 * @param uid
	 */
	public void removeUnReadRemindCounFromCache(Integer uid) {
		CacheBase.getInstance().remove(SystemConstant.CACHE_UNREADREMINDCOUNT_REGION, String.valueOf(uid));
	}
}
