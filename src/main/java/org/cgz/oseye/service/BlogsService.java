package org.cgz.oseye.service;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.cgz.oseye.model.Blogs;

/** 
 * @author 陈广志 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public interface BlogsService {

	public Blogs findById(Integer id);
	
	public Blogs findByName(String name);
	
	public void update(Blogs blog);
	
	public int getDraftNum(Integer blogId);
	
	/**
     * 批量更新阅读数
     * @param queue
     */
	public void updateViewsCount(ConcurrentHashMap<Serializable, Integer> queue);
}
