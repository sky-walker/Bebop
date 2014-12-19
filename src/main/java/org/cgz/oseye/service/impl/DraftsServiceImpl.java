package org.cgz.oseye.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Drafts;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.DraftsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("draftsService")
@Transactional
public class DraftsServiceImpl implements DraftsService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private BlogsService blogsService;
	
	/**
	 * 添加草稿
	 * @param drafts
	 */
	public void addDraft(Drafts drafts) {
		Blogs blog = drafts.getBlogs();
		baseDao.save(drafts);
		blog.setDraftsNum(blog.getDraftsNum()+1);
		blogsService.update(blog);
	}
	
	/**
	 * 删除草稿
	 * @param draftId
	 */
	public void delDraft(Integer blogId,Integer draftId) {
		Drafts draft = baseDao.findByWhere(QLBuilder.select(Drafts.class).where("id").and("blogs.id").setQlParams(draftId,blogId));
		Blogs blog = draft.getBlogs();
		Integer draftNum =blog.getDraftsNum();
		if(draft!=null) {
			baseDao.delete(Drafts.class, draftId);
			if(draftNum>0) {
				blog.setDraftsNum(draftNum-1);
				blogsService.update(blog);
			}
		}
	}
	
	
	/**
	 * 更新草稿
	 * @param draft
	 */
	public void modifyDraft(Drafts draft) {
		baseDao.update(draft);
	}
	
	/**
	 * 获取草稿
	 * @param blogId
	 * @param draftId
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Drafts getDraft(Integer blogId,Integer draftId) {
		return baseDao.findByWhere(QLBuilder.select(Drafts.class).where(Drafts.ID).and("blogs.id").setQlParams(draftId,blogId));
	}
	
	
	/**
	 * 获取博客下所有的草稿
	 * @param blogId
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public List<Drafts> getDrafts(Integer blogId) {
		Pager<Drafts> pager = baseDao.getScrollData(QLBuilder.select(Drafts.class).where("blogs.id").desc("id").setQlParams(blogId), -1, -1);
		return pager.getResultList();
	}
}
