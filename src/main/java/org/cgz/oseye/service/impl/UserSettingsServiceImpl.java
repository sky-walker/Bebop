package org.cgz.oseye.service.impl;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.model.UserSettings;
import org.cgz.oseye.service.UserSettingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userSettingsService")
public class UserSettingsServiceImpl implements UserSettingsService{

	@Resource
	private BaseDao baseDao;
	
	@Override
	@Transactional
	public UserSettings modifyUserSettings(UserSettings userSettings) {
		baseDao.update(userSettings);
		return userSettings;
	}

	@Override
	public UserSettings findUserSettings(Integer uid) {
		return baseDao.find(UserSettings.class, uid);
	}
}
