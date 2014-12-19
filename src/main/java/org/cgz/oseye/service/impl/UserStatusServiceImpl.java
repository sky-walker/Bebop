package org.cgz.oseye.service.impl;

import org.cgz.oseye.cache.CacheBase;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.UserStatus;
import org.cgz.oseye.service.UserStatusService;
import org.springframework.stereotype.Service;

@Service("userStatusService")
public class UserStatusServiceImpl implements UserStatusService{
	
	/**
	 * 添加或更新
	 * @param uid
	 * @param userStatus
	 */
	public void setUserStatus(Integer uid,UserStatus userStatus) {
		CacheBase.getInstance().put(SystemConstant.CACHE_USER_STATUS_REGION, uid, userStatus);
	}
	
	/**
	 * 删除
	 * @param uid
	 */
	public void removeUserStatus(Integer uid) {
		CacheBase.getInstance().remove(SystemConstant.CACHE_USER_STATUS_REGION,uid);
	}
	
	/**
	 * 取得
	 * @param sessionKey
	 * @return
	 */
	public UserStatus getUserStatus(Integer uid) {
		return (UserStatus)CacheBase.getInstance().get(SystemConstant.CACHE_USER_STATUS_REGION,uid);
	}
}
