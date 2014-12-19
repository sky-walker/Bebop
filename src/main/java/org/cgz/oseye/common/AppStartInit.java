package org.cgz.oseye.common;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class AppStartInit implements InitializingBean{
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		//TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		//System.setProperty("user.timezone","Asia/Shanghai");
		//TimeZone.setDefault(TimeZone.getTimeZone("PRC"));
	}
}
