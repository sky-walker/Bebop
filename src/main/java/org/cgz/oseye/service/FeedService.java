package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.BaseModel;
import org.cgz.oseye.model.Feed;

public interface FeedService {

	/**
	 * 动态的添加
	 * @param optType
	 * @param model
	 */
	public void addFeed(BaseModel model);
	
	/**
	 * 获取我所关注的对象的所有动态
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Feed> getFollowFeeds(Integer userId,Integer pageNo,Integer pageSize,Short type);
	
	/**
	 * 获取我的动态
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Feed> getMyFeeds(Integer userId,Integer pageNo,Integer pageSize,Short type);
	
	/**
	 * 获取全站动态
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Feed> getAllFeeds(Integer pageNo,Integer pageSize,Short type);
	
	/**
	 * 删除动态
	 * @param id
	 */
	public void delFeedByOptCode(Integer whoId,short optType,Integer whoseId,Integer optObjectId);
	
	/**
	 * 根据操作类型和操作对象删除动态
	 * @param optType
	 * @param objId
	 */
	public void delFeedByOptTypeObjId(Integer whoId,short optType,Integer objTypeId);
	
	/**
	 * 删除和某操作对象有关的所有的动态(比如删除某篇文章需要删除文章和文章下评论所产生的动态)
	 * @param objId
	 */
	public void delFeedByObjId(Integer whoId,BaseModel baseModel);
	
	/**
     * 创建用户操作记录码
     * @param whoId
     * @param optType
     * @param whoseId
     * @param optObjectId
     * @return
     */
    public String createOptCode(Integer whoId,short optType,Integer whoseId,Integer optObjectId);
    
    /**
     * 获得用户的最新一天动态
     * @param userId
     * @return
     */
    public Feed getUserLastFeed(Integer userId);
    
}
