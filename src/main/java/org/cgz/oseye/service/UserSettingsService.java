package org.cgz.oseye.service;

import org.cgz.oseye.model.UserSettings;

public interface UserSettingsService {

	public UserSettings modifyUserSettings(UserSettings userSettings);
	
	public UserSettings findUserSettings(Integer uid);
}
