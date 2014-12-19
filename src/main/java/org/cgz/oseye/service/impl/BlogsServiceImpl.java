package org.cgz.oseye.service.impl;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.service.BlogsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 
 * @author 陈广志 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
@Service("blogsService")
@Transactional
public class BlogsServiceImpl implements BlogsService {
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * 通过名称查找
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Blogs findByName(String name) {
		return baseDao.findByWhere(QLBuilder.select(Blogs.class).where("name").setQlParams(name));
	}
	
	/**
	 * 更新
	 * @param blog
	 */
	public void update(Blogs blog) {
		baseDao.update(blog);
	}
	
	/**
	 * 拿到博客下的草稿数
	 */
	public int getDraftNum(Integer blogId) {
		Blogs blog = baseDao.find(Blogs.class, blogId);
		return blog.getDraftsNum();
	}

	/**
	 * 根据id拿到blog
	 */
	public Blogs findById(Integer id) {
		return baseDao.find(Blogs.class, id);
	}
	
	/**
     * 批量更新阅读数
     * @param queue
     */
	public void updateViewsCount(ConcurrentHashMap<Serializable, Integer> queue) {
		for(Serializable key : queue.keySet()) {
			Blogs blog= this.findById((Integer)key);
			if(blog!=null) {
				baseDao.update(QLBuilder.update(Blogs.class,"viewsNum").where("id").setQlParams(blog.getViewsNum()+queue.get(key),key));
			}
		}
	}
}
