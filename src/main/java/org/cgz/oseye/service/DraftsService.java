package org.cgz.oseye.service;

import java.util.List;
import org.cgz.oseye.model.Drafts;

public interface DraftsService {

	/**
	 * 添加草稿
	 * @param drafts
	 */
	public void addDraft(Drafts drafts);
	
	/**
	 * 删除草稿
	 * @param draftId
	 */
	public void delDraft(Integer userId, Integer draftId);
	
	/**
	 * 更新草稿
	 * @param draft
	 */
	public void modifyDraft(Drafts draft);
	
	/**
	 * 获取草稿
	 * @param blogId
	 * @param draftId
	 * @return
	 */
	public Drafts getDraft(Integer blogId, Integer draftId);
	
	
	/**
	 * 获取博客下所有的草稿
	 * @param blogId
	 * @return
	 */
	public List<Drafts> getDrafts(Integer blogId);
}
