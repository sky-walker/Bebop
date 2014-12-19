package org.cgz.oseye.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.cache.CacheBase;
import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Private_Message;
import org.cgz.oseye.model.Talk;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.Private_MessageService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("private_MessageService")
@Transactional
public class Private_MessageServiceImpl implements Private_MessageService {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 添加私信
	 * @param private_Message
	 */
	public void addMessage(Private_Message private_Message) {
		Users sender = private_Message.getSender();
		Users receiver = private_Message.getReceiver();
		private_Message.setUser(sender);
		private_Message.setFriend(receiver);
		
		Private_Message private_Message2 = new Private_Message();
		BeanUtils.copyProperties(private_Message, private_Message2);
		private_Message2.setUser(receiver);
		private_Message2.setFriend(sender);
		
		baseDao.save(private_Message);
		baseDao.save(private_Message2);
		removeUnReadPrivateMessagCounFromCache(receiver.getId());
	}
	
	/**
	 * 删除私信
	 * @param userId 删除私信的用户
	 * @param id 	  被删除的私信
	 */
	public void delMessage(Integer userId,Integer id) {
		Private_Message private_Message = baseDao.findByWhere(QLBuilder.select(Private_Message.class).where("id").and("user.id").setQlParams(id,userId));
		if(private_Message!=null) {
			baseDao.delete(Private_Message.class, id);
			//更新缓存
			if(private_Message.getReadStatus()==SystemConstant.MESSAGE_NEW) {
				removeUnReadPrivateMessagCounFromCache(userId);
			}
		}
	}
	
	/**
	 * 删除与某好友的所有私信记录
	 * @param userId
	 * @param friendId
	 */
	public void delMessagesByFriend(Integer userId,Integer friendId) {
		baseDao.deleteByWhere(QLBuilder.delete(Private_Message.class).where("user.id").and("friend.id").setQlParams(userId,friendId));
		removeUnReadPrivateMessagCounFromCache(userId);
	}
	
	/**
	 * 用户私信来往记录
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Talk> getTalkPager(Integer userId,Integer pageNo, Integer pageSize) {
		Session session = baseDao.getCurrentSession();
		Query query = session.createSQLQuery("select max(id) m_id,count(id),(select count(id) from ose_private_message b where b.user_id=? and b.friend_id=a.friend_id and b.read_status=? and b.receiver_id=?) unread_count from ose_private_message a where user_id=? group by friend_id order by m_id desc");
		query.setParameter(0, userId);
		query.setParameter(1, SystemConstant.MESSAGE_NEW);
		query.setParameter(2, userId);
		query.setParameter(3, userId);
		query.setFirstResult((pageNo-1)>=0?(pageNo-1)*pageSize:0).setMaxResults(pageSize);
		List<Object[]> resultList = query.list();
		long totalRecords =resultList.size(); 
		List<Talk> talks = new ArrayList<Talk>();
		for(Object[] objects:resultList) {
			Integer max_id = (Integer) objects[0];
			BigInteger count_id = (BigInteger) objects[1];
			BigInteger unread_count = (BigInteger) objects[2];
			Private_Message last_message = baseDao.find(Private_Message.class, max_id);
			Talk talk = new Talk();
			talk.setLastMessage(last_message);
			talk.setMsgCount(count_id);
			talk.setNewMsgNum(unread_count);
			talks.add(talk);
		}
		Pager<Talk> pager = new Pager<Talk>();
		pager.setResultList(talks);
		pager.setTotalRecords(totalRecords);
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		return pager;
	}
	
	/**
	 * 私信对话分页查询
	 * @param userId
	 * @param friendId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Private_Message> getPrivateMessagePager(Integer userId,Integer friendId,Integer pageNo, Integer pageSize) {
		Pager<Private_Message> pager = baseDao.getScrollData(QLBuilder.select(Private_Message.class).where("user.id").and("friend.id").desc("id").setQlParams(userId,friendId), pageNo, pageSize);
		readPrivateMessages(userId,pager);
		return pager;
	}
	
	/**
	 * 查询出未读私信的数量
	 * @param userId
	 * @return
	 */
	public long getUnReadPrivateMessagCount(Integer userId) {
		Object unReadPrivateMessagCount = CacheBase.getInstance().get(SystemConstant.CACHE_UNREADPRIVATEMESSAGECOUNT_REGION, String.valueOf(userId));
		if(unReadPrivateMessagCount==null) {
			unReadPrivateMessagCount = baseDao.getCountByWhere(QLBuilder.count(Private_Message.class).where("user.id").and("receiver.id").and("readStatus").setQlParams(userId,userId,SystemConstant.MESSAGE_NEW));
			CacheBase.getInstance().put(SystemConstant.CACHE_UNREADPRIVATEMESSAGECOUNT_REGION, String.valueOf(userId), (Long)unReadPrivateMessagCount);
		}
		return (Long) unReadPrivateMessagCount;
	}
	
	/**
	 * 批量更新私信的阅读状态
	 * @param pager
	 */
	public void readPrivateMessages(Integer userId,Pager<Private_Message> pager) {
		List<Private_Message> list = pager.getResultList();
		StringBuffer ids = new StringBuffer();
		for(int i=0;i<list.size();i++) {
			Private_Message private_Message = list.get(i);
			//必须消息接收者是当前用户,同时消息是未读状态,才符合更新条件.
			if(userId.intValue()==private_Message.getReceiver().getId().intValue() && private_Message.getReadStatus()==SystemConstant.MESSAGE_NEW) {
				ids.append(","+private_Message.getId());
			}
		}
		if(ids.length()>0) {
			ids.deleteCharAt(0);
			baseDao.update(QLBuilder.queryByHql("update Private_Message o set o.readStatus=? where o.id in("+ids.toString()+")").setQlParams(SystemConstant.MESSAGE_READ));
			removeUnReadPrivateMessagCounFromCache(userId);
		}
	}
	
	/**
	 * 从缓存中清除未读消息数
	 * @param uid
	 */
	private void removeUnReadPrivateMessagCounFromCache(Integer uid) {
		CacheBase.getInstance().remove(SystemConstant.CACHE_UNREADPRIVATEMESSAGECOUNT_REGION, String.valueOf(uid));
	}
}
