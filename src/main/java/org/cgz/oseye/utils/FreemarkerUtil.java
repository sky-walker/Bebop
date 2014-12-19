package org.cgz.oseye.utils;


import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
/**
 * freemarker 工具类:用于在ftl文件中使用类中的方法
 * @author Administrator
 */
public class FreemarkerUtil {
	
	/**
	 * 指定在ftl中需要使用的类
	 * @param className 需要使用的类全路径
	 * @return
	 */
	public static TemplateHashModel userStaticClass(String className) {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		try {
			TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(className);
			return  fileStatics;
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
