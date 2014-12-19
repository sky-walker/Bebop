package org.cgz.oseye.service;

import java.util.List;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Visits;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface VisitsService {
	/**
	 * 分页查询
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Visits> getVisitsPager(Integer blogId,int pageNo, int pageSize);
	
	/**
	 * 拿到前pageSize个访问者
	 * @param blogId
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public List<Visits> getTopVisitsPager(Integer blogId,int pageSize);
	
	
	/**
	 * 删除访客记录 (只是设置删除标记)
	 * @param visitorId
	 */
	public void removeVisitor(Integer blogId,Integer visitsId);
	
	/**
	 * 更新访客状态
	 * @param visits
	 */
	public void updateVisits(Visits visits);
	
	/**
	 * 添加访客
	 * @param visits
	 */
	public void addVisits(Visits visits);
	
	/**
	 * 我的足迹
	 * @param userId 当前用户
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Visits> getFootsPager(Integer userId,int pageNo, int pageSize);
}
