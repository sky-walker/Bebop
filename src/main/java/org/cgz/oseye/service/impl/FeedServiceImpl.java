package org.cgz.oseye.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.cache.CacheBase;
import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.QLCompare;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.BaseModel;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Comments;
import org.cgz.oseye.model.Concern;
import org.cgz.oseye.model.Favorites;
import org.cgz.oseye.model.Feed;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.FeedService;
import org.cgz.oseye.service.RemindService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("feedService")
public class FeedServiceImpl implements FeedService{
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private RemindService remindService;
	
	/**
	 * 动态的添加
	 * @param optType
	 * @param model
	 */
	@Transactional
	public void addFeed(BaseModel model) {
		Feed feed = null;
		if(model instanceof Concern) {
			feed = concernAddFeed((Concern) model);
		}else if(model instanceof Posts) {
			feed = postAddFeed((Posts) model);
		}else if(model instanceof Comments) {
			feed = postCommentAddFeed((Comments) model);
		}else if(model instanceof Users) {
			feed = userAddFeed((Users) model);
		}else if(model instanceof Favorites) {
			favoriteAddFeed((Favorites) model);
		}
		//将用户最后一条feed放入缓存
		if(feed!=null) {
			  putUserLastFeedToCache(feed);
		}
	}
	
	/**
	 * 获取我所关注的对象的所有动态
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Feed> getFollowFeeds(Integer userId,Integer pageNo,Integer pageSize,Short type) {
		Pager<Concern> pager = baseDao.getScrollData(QLBuilder.select(Concern.class).where("fans.id").setQlParams(userId), -1, -1);
		List<Integer> followIds = new ArrayList<Integer>();
		for(Concern concern : pager.getResultList()) {
			followIds.add(concern.getFollow().getId());
		}
		if(followIds.size()>0) {
			if(type!=null) {
				return baseDao.getScrollData(QLBuilder.select(Feed.class).whereIn("who.id",followIds.toArray()).and("optType").desc("id").setQlParams(type), pageNo, pageSize);
			}else {
				
			}
			return baseDao.getScrollData(QLBuilder.select(Feed.class).setQlParams(followIds.toArray()).whereIn("who.id").desc("id"), pageNo, pageSize);
		}
		return new Pager<Feed>();
	}
	
	/**
	 * 获取我的动态
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Feed> getMyFeeds(Integer userId,Integer pageNo,Integer pageSize,Short type) {
		if(type!=null) {
			return baseDao.getScrollData(QLBuilder.select(Feed.class).where("who.id").and("optType").setQlParams(userId,type).desc("id"), pageNo, pageSize);
		}else {
			return baseDao.getScrollData(QLBuilder.select(Feed.class).where("who.id").setQlParams(userId).desc("id"), pageNo, pageSize);
		}
		
	}
	
	/**
	 * 获取当天的全站动态
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Feed> getAllFeeds(Integer pageNo,Integer pageSize,Short type) {
		if(type!=null) {
			return baseDao.getScrollData(QLBuilder.select(Feed.class).where("optType").setQlParams(type).desc("id"), pageNo, pageSize);
		}else {
			return baseDao.getScrollData(QLBuilder.select(Feed.class).desc("id"), pageNo, pageSize);
		}
	}
	
	/**
	 * 获取当天的全站动态
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Pager<Feed> getAllFeedsToday(Integer pageNo,Integer pageSize) {
		Date currentDate = new Date();
		Date yestoday = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate(), 0, 0, 0);
		Date today = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate(), 23, 59, 59);
		return baseDao.getScrollData(QLBuilder.select(Feed.class).where("optTime",QLCompare.GT).and("optTime",QLCompare.LE).setQlParams(yestoday,today).desc("id"), pageNo, pageSize);
	}
	
	/**
	 * 根据操作码删除动态
	 * @param id
	 */
	@Transactional
	public void delFeedByOptCode(Integer whoId,short optType,Integer whoseId,Integer optObjectId) {
		String optCode = createOptCode(whoId, optType, whoseId, optObjectId);
		Feed feed = baseDao.findByWhere(QLBuilder.select(Feed.class).where("optCode").setQlParams(optCode));
		if(feed!=null) {
			baseDao.delete(Feed.class, feed.getId());
			//如果用户最后动态和被删除的一致,那么就更新缓存
			if(getUserLastFeed(whoId).getId().intValue()==whoId.intValue()) {
				removeUserLastFeedFromCache(whoId);
			}
		}
	}
	
	/**
	 * 根据操作类型和操作类型相关对象删除动态
	 * @param optType
	 * @param objId
	 */
	@Transactional
	public void delFeedByOptTypeObjId(Integer whoId,short optType,Integer objTypeId) {
		baseDao.deleteByWhere(QLBuilder.delete(Feed.class).where("who.id").and("optType").and("optTypeObj").setQlParams(whoId,optType,objTypeId));
		removeUserLastFeedFromCache(whoId);
	}
	
	/**
	 * 删除和某操作对象有关的所有的动态(比如删除某篇文章需要删除文章和文章下评论所产生的动态)
	 * @param objId
	 */
	@Transactional
	public void delFeedByObjId(Integer whoId,BaseModel baseModel) {
		if(baseModel instanceof Posts) {
			Posts posts = (Posts) baseModel;
			baseDao.deleteByWhere(QLBuilder.delete(Feed.class).where("posts.id").setQlParams(posts.getId()));
		}else if(baseModel instanceof Concern) {
			Concern concern = (Concern) baseModel;
			baseDao.deleteByWhere(QLBuilder.delete(Feed.class).where("concern.id").setQlParams(concern.getId()));
		}else if(baseModel instanceof Comments) {
			Comments comments = (Comments) baseModel;
			baseDao.deleteByWhere(QLBuilder.delete(Feed.class).where("comments.id").setQlParams(comments.getId()));
		}
		removeUserLastFeedFromCache(whoId);
	}
	
	/**
	 * 用户注册加入oseye的动态
	 * @param user
	 * @return
	 */
	@Transactional
	private Feed userAddFeed(Users user) {
		String optCode = createOptCode(user.getId(), SystemConstant.FEED_TYPE_USER_ADD, user.getId(), user.getId());
		Feed feed = new Feed();
		feed.setWho(user);
		feed.setOptType(SystemConstant.FEED_TYPE_USER_ADD);
		feed.setWhose(user);
		feed.setOptCode(optCode);
		feed.setReadStatus(SystemConstant.FEED_STATUS_UNREAD);
		feed.setIsRemind(SystemConstant.FEED_IS_REMIND);
		baseDao.save(feed);
		return feed;
	}
	
	/**
	 * 文章添加动态
	 * @param post
	 * @return
	 */
	@Transactional
	private Feed postAddFeed(Posts post) {
		//文章所属博客
		Blogs blog = post.getBlogs();
		//文章所属用户
		Users user = blog.getUsers();
		//判断用户是否设置了隐私
		if(user.getUserSettings().getPrivacy_blog_add()==SystemConstant.PRIVACY_BLOG_ADD_HIDE) {
			return null;
		}
		String optCode = createOptCode(user.getId(), SystemConstant.FEED_TYPE_POST_ADD, user.getId(), post.getId());
		Feed feed = new Feed();
		feed.setWho(user);
		feed.setOptType(SystemConstant.FEED_TYPE_POST_ADD);
		feed.setWhose(user);
		feed.setPosts(post);
		feed.setOptCode(optCode);
		//因为用户发表文章不属于提醒类型,因此设置为已读和非通知类型
		feed.setReadStatus(SystemConstant.FEED_STATUS_READ);
		feed.setIsRemind(SystemConstant.FEED_NOT_REMIND);
		baseDao.save(feed);
		return feed;
	}
	
	/**
	 * 博客评论
	 * @param post
	 * @return
	 */
	@Transactional
	private Feed postCommentAddFeed(Comments comment) {
		//评论作者
		Users auth = comment.getUsers();
		//判断用户是否设置了隐私
		if(auth.getUserSettings().getPrivacy_comment_add()==SystemConstant.PRIVACY_COMMENT_HIDE) {
			return null;
		}
		//评论所属文章
		Posts post = comment.getPosts();
		Blogs postBlog = comment.getBlogs();
		Feed feed = new Feed();
		feed.setWho(auth);
		feed.setOptType(SystemConstant.FEED_TYPE_POST_COMMENT_ADD);
		feed.setWhose(postBlog.getUsers());
		feed.setPosts(post);
		feed.setComments(comment);
		feed.setOptCode(createOptCode(auth.getId(), SystemConstant.FEED_TYPE_POST_COMMENT_ADD, postBlog.getId(), post.getId()));
		feed.setReadStatus(SystemConstant.FEED_STATUS_UNREAD);
		//如果自己评论自己的文章,则不属于通知类型
		if(auth.getId().intValue()==postBlog.getId().intValue()) {
			feed.setIsRemind(SystemConstant.FEED_NOT_REMIND);
		}else {
			feed.setIsRemind(SystemConstant.FEED_IS_REMIND);
			//更新被评论者的通知数
			remindService.removeUnReadRemindCounFromCache(postBlog.getId());
		}
		baseDao.save(feed);
		//评论有可能是评论的回复
		commentReplyFeed(comment);
		return feed;
	}
	
	
	/**
	 * 添加关注
	 * @param post
	 * @return
	 */
	@Transactional
	private Feed concernAddFeed(Concern concern) {
		//粉丝
		Users fans = concern.getFans();
		//判断用户是否设置了隐私
		if(fans.getUserSettings().getPrivacy_concern_add()==SystemConstant.PRIVACY_CONCERN_ADD_HIDE) {
			return null;
		}
		//被关注的用户
		Users follow = concern.getFollow();
		Feed feed = new Feed();
		feed.setWho(fans);
		feed.setOptType(SystemConstant.FEED_TYPE_CONCERN_ADD);
		feed.setWhose(follow);
		feed.setConcern(concern);
		feed.setOptCode(createOptCode(fans.getId(), SystemConstant.FEED_TYPE_CONCERN_ADD, follow.getId(), follow.getId()));
		feed.setReadStatus(SystemConstant.FEED_STATUS_UNREAD);
		feed.setIsRemind(SystemConstant.FEED_IS_REMIND);
		baseDao.save(feed);
		remindService.removeUnReadRemindCounFromCache(follow.getId());
		return feed;
	}
	
	/**
	 * 添加收藏(内链)
	 * @param post
	 * @return
	 */
	@Transactional
	private Feed favoriteAddFeed(Favorites favorites) {
		Users who = favorites.getUsers();
		//判断用户是否设置了隐私
		if(who.getUserSettings().getPrivacy_blog_post_favorite()==SystemConstant.PRIVACY_BLOG_POST_FAVORITE_HIDE) {
			return null;
		}
		Posts posts = favorites.getPosts();
		Users whose = posts.getBlogs().getUsers();
		String optCode = createOptCode(who.getId(), SystemConstant.FEED_TYPE_FAVORITE_ADD, whose.getId(), posts.getId());
		Feed feed = getFeedByOptCode(optCode);
		//因为在删除收藏时,不会去删除用户收藏文章所产生的动态,因此当用户在此收藏同一文章时,只更新此动态的时间和阅读状态
		if(feed==null) {
			feed = new Feed();
			feed.setOptCode(optCode);
			feed.setWho(who);
			feed.setOptType(SystemConstant.FEED_TYPE_FAVORITE_ADD);
			feed.setWhose(whose);
			feed.setPosts(posts);
			if(who.getId().intValue()!=whose.getId().intValue()) {
				feed.setReadStatus(SystemConstant.FEED_STATUS_UNREAD);
				feed.setIsRemind(SystemConstant.FEED_IS_REMIND);
				baseDao.save(feed);
			}
		}else {
			feed.setOptTime(new Date());
			feed.setReadStatus(SystemConstant.FEED_STATUS_UNREAD);
		}
		remindService.removeUnReadRemindCounFromCache(whose.getId());
		return feed;
	}
	
	/**
	 * 评论的回复
	 * @param post
	 * @return
	 */
	@Transactional
	private Feed commentReplyFeed(Comments comment) {
		//评论作者
		Users auth = comment.getUsers();
		//判断用户是否设置了隐私
		if(auth.getUserSettings().getPrivacy_comment_reply()==SystemConstant.PRIVACY_COMMENT_REPLY_HIDE) {
			return null;
		}
		Comments parent = comment.getParent();
		Feed feed = null;
		if(parent!=null) {
			Users whose = parent.getUsers();
			feed = new Feed();
			//评论所属文章
			Posts post = comment.getPosts();
			Blogs postBlog = comment.getBlogs();
			feed.setWho(auth);
			feed.setOptType(SystemConstant.FEED_TYPE_COMMENT_REPLY);
			feed.setWhose(whose);
			feed.setComments(comment);
			feed.setPosts(post);
			feed.setOptCode(createOptCode(auth.getId(), SystemConstant.FEED_TYPE_COMMENT_REPLY, postBlog.getUsers().getId(), comment.getId()));
			feed.setReadStatus(SystemConstant.FEED_STATUS_UNREAD);
			feed.setIsRemind(SystemConstant.FEED_IS_REMIND);
			//如果自己回复自己的评论,则不属于通知类型
			if(auth.getId().intValue()==parent.getUsers().getId().intValue()) {
				feed.setIsRemind(SystemConstant.FEED_NOT_REMIND);
			}else {
				feed.setIsRemind(SystemConstant.FEED_IS_REMIND);
				//更新被评论者的通知数
				remindService.removeUnReadRemindCounFromCache(whose.getId());
			}
			baseDao.save(feed);
		}
		return feed;
	}
	
	/**
	 * 根据操作码获取动态
	 * @param optCode
	 * @return
	 */
	public Feed getFeedByOptCode(String optCode) {
		return baseDao.findByWhere(QLBuilder.select(Feed.class).where("optCode").setQlParams(optCode));
	}
	
    /**
     * 创建用户操作记录码
     * @param whoId
     * @param optType
     * @param whoseId
     * @param optObjectId
     * @return
     */
    public String createOptCode(Integer whoId,short optType,Integer whoseId,Integer optObjectId) {
    	StringBuffer sbCode = new StringBuffer();
    	sbCode.append(whoId)
    		  .append("-")
    		  .append(optType);
    	if(whoseId!=null && whoseId>=0) {
    		sbCode.append("-")
    			  .append(whoseId);
    	}
    	if(optObjectId!=null && optObjectId>=0) {
    		sbCode.append("-")
    			  .append(optObjectId);  
    	}
    	return sbCode.toString();
    }
    
    /**
     * 获得用户的最新动态
     * @param userId
     * @return
     */
    public Feed getUserLastFeed(Integer userId) {
    	Feed userLastFeed = (Feed)CacheBase.getInstance().get(SystemConstant.CACHE_USERLASTFEED_REGION, String.valueOf(userId));
		if(userLastFeed==null) {
			userLastFeed = baseDao.findByWhere(QLBuilder.queryByHql("select f1 from Feed f1 where f1.id in(select max(f.id) from Feed f where f.who.id=?)").setQlParams(userId));
			CacheBase.getInstance().put(SystemConstant.CACHE_USERLASTFEED_REGION, String.valueOf(userId), userLastFeed);
		}
		return userLastFeed;
    }
    
    /**
     * 添加到缓存中
     * @param lastFeed
     */
    public void putUserLastFeedToCache(Feed lastFeed) {
    	CacheBase.getInstance().put(SystemConstant.CACHE_USERLASTFEED_REGION, String.valueOf(lastFeed.getWho().getId()), lastFeed);
    }
    
    /**
	 * 从缓存中清除未读消息数
	 * @param uid
	 */
	public void removeUserLastFeedFromCache(Integer uid) {
		CacheBase.getInstance().remove(SystemConstant.CACHE_USERLASTFEED_REGION, String.valueOf(uid));
	}
}
