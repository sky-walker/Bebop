package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Concern;

public interface ConcernService {

	
	public Concern loadConcern(Integer followId,Integer fansId);
	
	/**
	 * 添加关注
	 * @param concern
	 */
	public void addConcern(Concern concern);

	/**
	 * 取消关注
	 * @param followId
	 * @param fansId
	 */
	public void delConcern(Integer followId, Integer fansId);

	/**
	 * 获得用户的所有关注对象
	 * @param fansId
	 * @param qlBuilder
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy(update:最近更新;time:关注时间;login:登录时间)
	 * @param isDetail 是否显示用户最新动态
	 * @return
	 */
	public Pager<Concern> getFollowsPager(Integer userId,Integer pageNo, Integer pageSize, String sortBy,boolean isDetail);

	/**
	 * 获得用户的所有粉丝
	 * @param userId
	 * @param qlBuilder
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy(update:最近更新;time:关注时间;login:登录时间)
	 * @param isDetail 是否显示用户最新动态
	 * @return
	 */
	public Pager<Concern> getFansPager(Integer userId,Integer pageNo, Integer pageSize, String sortBy,boolean isDetail);
	
	/**
	 * 获取所有的好友(互为关注)
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param isDetail 是否显示用户最新动态
	 * @return
	 */
	public Pager<Concern> getFriendsPager(Integer userId,Integer pageNo,Integer pageSize,String sortBy,boolean isDetail);

}