package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Private_Message;
import org.cgz.oseye.model.Talk;

public interface Private_MessageService {

	/**
	 * 添加私信
	 * @param private_Message
	 */
	public void addMessage(Private_Message private_Message);
	
	/**
	 * 删除私信
	 * @param id
	 */
	public void delMessage(Integer userId,Integer id);
	
	/**
	 * 删除与某好友的所有私信记录
	 * @param userId
	 * @param friendId
	 */
	public void delMessagesByFriend(Integer userId,Integer friendId);
	
	/**
	 * 用户私信来往记录
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Talk> getTalkPager(Integer userId,Integer pageNo, Integer pageSize);
	
	/**
	 * 私信对话分页查询
	 * @param userId
	 * @param friendId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Private_Message> getPrivateMessagePager(Integer userId,Integer friendId,Integer pageNo, Integer pageSize);
	
	/**
	 * 查询出未读私信的数量
	 * @param userId
	 * @return
	 */
	public long getUnReadPrivateMessagCount(Integer userId);
	
	/**
	 * 批量更新私信的阅读状态
	 * @param pager
	 */
	public void readPrivateMessages(Integer userId,Pager<Private_Message> pager);
}
