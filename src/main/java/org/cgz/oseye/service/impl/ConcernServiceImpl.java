package org.cgz.oseye.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Concern;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.ConcernService;
import org.cgz.oseye.service.FeedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("concernService")
@Transactional
public class ConcernServiceImpl implements ConcernService {

	@Resource
	private BaseDao baseDao;
	@Resource
	private FeedService feedService;
	
	public Concern loadConcern(Integer followId,Integer fansId) {
		return baseDao.findByWhere(QLBuilder.select(Concern.class).where("follow.id").and("fans.id").setQlParams(followId,fansId));
	}
	
	/**
	 * 添加关注
	 * @param concern
	 */
	public void addConcern(Concern concern) {
		Users follow = concern.getFollow();
		Users fans = concern.getFans();
		Concern concern1 = baseDao.findByWhere(QLBuilder.select(Concern.class).where("follow.id").and("fans.id").setQlParams(follow.getId(),fans.getId()));
		if(concern1==null) {
			//查询是否互为关注
			Concern concern2 = baseDao.findByWhere(QLBuilder.select(Concern.class).where("follow.id").and("fans.id").setQlParams(fans.getId(),follow.getId()));
			if(concern2!=null) {
				concern2.setIsFriends(SystemConstant.CONCERN_IS_FRIEND);
				concern.setIsFriends(SystemConstant.CONCERN_IS_FRIEND);
			}
			baseDao.save(concern);
			
			//更新用户的关注和粉丝数
			follow.setFansNum(follow.getFansNum()+1);
			fans.setFollowsNum(fans.getFollowsNum()+1);
			feedService.addFeed(concern);
		}
	}
	
	/**
	 * 取消关注
	 * @param followId
	 * @param fansId
	 */
	public void delConcern(Integer followId,Integer fansId) {
		Concern concern = baseDao.findByWhere(QLBuilder.select(Concern.class).where("follow.id").and("fans.id").setQlParams(followId,fansId));
		if(concern!=null) {
			feedService.delFeedByOptCode(concern.getFans().getId(), SystemConstant.FEED_TYPE_CONCERN_ADD, concern.getFollow().getId(), concern.getFollow().getId());
			baseDao.delete(Concern.class, concern.getId());
			Users follow = concern.getFollow();
			Users fans = concern.getFans();
			if(follow.getFansNum()>0) {
				follow.setFansNum(follow.getFansNum()-1);
			}
			if(fans.getFollowsNum()>0) {
				fans.setFollowsNum(fans.getFollowsNum()-1);
			}
			//如果已经互粉
			if(concern.getIsFriends()==SystemConstant.CONCERN_IS_FRIEND) {
				Concern concern2 = baseDao.findByWhere(QLBuilder.select(Concern.class).where("follow.id").and("fans.id").setQlParams(fansId,followId));
				//取消对方的互粉标志
				if(concern2!=null && concern2.getIsFriends()==SystemConstant.CONCERN_IS_FRIEND) {
					concern2.setIsFriends(SystemConstant.CONCERN_NOT_FRIEND);
				}
			}
		}
	}
	
	/**
	 * 获得用户的所有关注对象
	 * @param fansId
	 * @param qlBuilder
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy(update:最近更新;time:关注时间;login:登录时间)
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Concern> getFollowsPager(Integer userId,Integer pageNo,Integer pageSize,String sortBy,boolean isDetail) {
		//如果不显示用户最近的动态,则默认按用户登陆时间排序
		if(!isDetail) {
			sortBy = "login";
		}
		Pager<Concern> pager = new Pager<Concern>();
		if("update".equals(sortBy)) {
			Pager<Feed> maxFeeds = baseDao.getScrollData(QLBuilder.queryByHql("select f3 from Feed f3 where f3.id in(select max(f2.id) from Feed f2 where f2.id in(select f1.id from Feed f1 where f1.who.id in(select c.follow.id from Concern c where c.fans.id=?) order by f1.id desc) group by f2.who.id) order by f3.id desc").setQlParams(userId), pageNo, pageSize);
			List<Integer> maxFeedWhoIds = new ArrayList<Integer>();
			StringBuffer sbQl = new StringBuffer();
			for(int i=0;i<maxFeeds.getResultList().size();i++) {
				maxFeedWhoIds.add(maxFeeds.getResultList().get(i).getWho().getId());
				sbQl.append(",").append(maxFeeds.getResultList().get(i).getWho().getId());
			}
			if(sbQl.length()>0) {
				sbQl.deleteCharAt(0);
			}
			List<Concern> concerns = new ArrayList<Concern>();
			for(int i=0;i<maxFeedWhoIds.size();i++) {
				Concern concern = baseDao.findByWhere(QLBuilder.select(Concern.class).where("fans.id").and("follow.id").setQlParams(userId,maxFeedWhoIds.get(i)));
				concern.setLastFeed(maxFeeds.getResultList().get(i));
				concerns.add(concern);
			}
			pager.setResultList(concerns);
			pager.setTotalRecords(maxFeeds.getTotalRecords());
			pager.setPageNo(pageNo);
			pager.setPageSize(pageSize);
			
		}
		if("time".equals(sortBy)) {
			pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("fans.id").desc("id").setQlParams(userId), pageNo, pageSize);
			setLastFeedToConcern(pager,isDetail,"follow");
		}
		
		if("login".equals(sortBy)) {
			pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("fans.id").desc("follow.thisLoginTime").setQlParams(userId), pageNo, pageSize);
			setLastFeedToConcern(pager,isDetail,"follow");
		}
		return pager;
	}
	
	
	/**
	 * 获得用户的所有粉丝
	 * @param userId
	 * @param qlBuilder
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy(time:关注时间;login:登录时间.注解：没有按最近更新排序,因为如果粉丝量非常大的话,查询效率会很低.新浪微博也没有按 粉丝的最近更新来排序)
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Concern> getFansPager(Integer userId,Integer pageNo,Integer pageSize,String sortBy,boolean isDetail) {
		//如果不显示用户最近的动态,则默认按用户登陆时间排序
		if(!isDetail) {
			sortBy = "login";
		}
		Pager<Concern> pager = new Pager<Concern>();
		if("update".equals(sortBy)) {
			Pager<Feed> maxFeeds = baseDao.getScrollData(QLBuilder.queryByHql("select f3 from Feed f3 where f3.id in(select max(f2.id) from Feed f2 where f2.id in(select f1.id from Feed f1 where f1.who.id in(select c.fans.id from Concern c where c.follow.id=?) order by f1.id desc) group by f2.who.id) order by f3.id desc").setQlParams(userId), pageNo, pageSize);
			List<Integer> maxFeedWhoIds = new ArrayList<Integer>();
			StringBuffer sbQl = new StringBuffer();
			for(int i=0;i<maxFeeds.getResultList().size();i++) {
				maxFeedWhoIds.add(maxFeeds.getResultList().get(i).getWho().getId());
				sbQl.append(",").append(maxFeeds.getResultList().get(i).getWho().getId());
			}
			if(sbQl.length()>0) {
				sbQl.deleteCharAt(0);
			}
			List<Concern> concerns = new ArrayList<Concern>();
			for(int i=0;i<maxFeedWhoIds.size();i++) {
				Concern concern = baseDao.findByWhere(QLBuilder.select(Concern.class).where("follow.id").and("fans.id").setQlParams(userId,maxFeedWhoIds.get(i)));
				concern.setLastFeed(maxFeeds.getResultList().get(i));
				concerns.add(concern);
			}
			pager.setResultList(concerns);
			pager.setTotalRecords(maxFeeds.getTotalRecords());
			pager.setPageNo(pageNo);
			pager.setPageSize(pageSize);
			
		}
		if("time".equals(sortBy)) {
			pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("follow.id").desc("id").setQlParams(userId), pageNo, pageSize);
			setLastFeedToConcern(pager,isDetail,"fans");
		}
		
		if("login".equals(sortBy)) {
			pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("follow.id").desc("fans.thisLoginTime").setQlParams(userId), pageNo, pageSize);
			setLastFeedToConcern(pager,isDetail,"fans");
		}
		return pager;
	}
	
	/**
	 * 获取所有的好友(互为关注)
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Concern> getFriendsPager(Integer userId,Integer pageNo,Integer pageSize,String sortBy,boolean isDetail) {
		//如果不显示用户最近的动态,则默认按用户登陆时间排序
		if(!isDetail) {
			sortBy = "login";
		}
		Pager<Concern> pager = new Pager<Concern>();
		if("update".equals(sortBy)) {
			Pager<Feed> maxFeeds = baseDao.getScrollData(QLBuilder.queryByHql("select f3 from Feed f3 where f3.id in(select max(f2.id) from Feed f2 where f2.id in(select f1.id from Feed f1 where f1.who.id in(select c.follow.id from Concern c where c.fans.id=? and c.isFriends=1) order by f1.id desc) group by f2.who.id) order by f3.id desc").setQlParams(userId), pageNo, pageSize);
			List<Integer> maxFeedWhoIds = new ArrayList<Integer>();
			StringBuffer sbQl = new StringBuffer();
			for(int i=0;i<maxFeeds.getResultList().size();i++) {
				maxFeedWhoIds.add(maxFeeds.getResultList().get(i).getWho().getId());
				sbQl.append(",").append(maxFeeds.getResultList().get(i).getWho().getId());
			}
			if(sbQl.length()>0) {
				sbQl.deleteCharAt(0);
			}
			List<Concern> concerns = new ArrayList<Concern>();
			for(int i=0;i<maxFeedWhoIds.size();i++) {
				Concern concern = baseDao.findByWhere(QLBuilder.select(Concern.class).where("fans.id").and("follow.id").setQlParams(userId,maxFeedWhoIds.get(i)));
				concern.setLastFeed(maxFeeds.getResultList().get(i));
				concerns.add(concern);
			}
			pager.setResultList(concerns);
			pager.setTotalRecords(maxFeeds.getTotalRecords());
			pager.setPageNo(pageNo);
			pager.setPageSize(pageSize);
			
		}
		if("time".equals(sortBy)) {
			pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("fans.id").and("isFriends").desc("id").setQlParams(userId,SystemConstant.CONCERN_IS_FRIEND), pageNo, pageSize);
			setLastFeedToConcern(pager,isDetail,"friends");
		}
		
		if("login".equals(sortBy)) {
			pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("fans.id").and("isFriends").desc("follow.thisLoginTime").setQlParams(userId,SystemConstant.CONCERN_IS_FRIEND), pageNo, pageSize);
			setLastFeedToConcern(pager,isDetail,"friends");
		}
		return pager;
	}
	
	/**
	 * 用户最后一条动态
	 * @param pager
	 * @param isDetail 是否设置了隐私
	 * @param type
	 */
	private void setLastFeedToConcern(Pager<Concern> pager,boolean isDetail,String type) {
		if(isDetail) {
			List<Concern> concerns = new ArrayList<Concern>();
			for(int i=0;i<pager.getResultList().size();i++) {
				Concern concern = pager.getResultList().get(i);
				Feed lastFeed = null;
				if("fans".equals(type)) {
					lastFeed = feedService.getUserLastFeed(concern.getFans().getId());
				}else {
					lastFeed = feedService.getUserLastFeed(concern.getFollow().getId());
				}
				concern.setLastFeed(lastFeed);
				concerns.add(concern);
			}
			pager.setResultList(concerns);
		}
	}
}
