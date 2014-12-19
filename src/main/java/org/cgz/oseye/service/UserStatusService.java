package org.cgz.oseye.service;

import org.cgz.oseye.model.UserStatus;

public interface UserStatusService {
	
	/**
	 * 添加或更新
	 * @param uid
	 * @param userStatus
	 */
	public void setUserStatus(Integer uid,UserStatus userStatus);
	
	/**
	 * 删除
	 * @param uid
	 */
	public void removeUserStatus(Integer uid);
	
	/**
	 * 取得
	 * @param uid
	 * @return
	 */
	public UserStatus getUserStatus(Integer uid);
	
}
