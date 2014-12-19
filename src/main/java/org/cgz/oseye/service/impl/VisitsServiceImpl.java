package org.cgz.oseye.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Visits;
import org.cgz.oseye.service.VisitsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("visitsService")
@Transactional
public class VisitsServiceImpl implements VisitsService {
	
	@Resource
	private BaseDao baseDao;
	
	/**
	 * 分页查询(查处所有的访客)
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Visits> getVisitsPager(Integer blogId,int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Visits.class).where("blogs.id").and("userDel").desc("visittime").setQlParams(blogId,SystemConstant.VISITS_KEEP_STATUS), pageNo, pageSize);
	}
	
	/**
	 * 拿到前pageSize个访问者
	 * @param blogId
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public List<Visits> getTopVisitsPager(Integer blogId,int pageSize) {
		return baseDao.getListData(QLBuilder.select(Visits.class).where("blogs.id").and("userDel").desc("visittime").setQlParams(blogId,SystemConstant.VISITS_KEEP_STATUS), pageSize);
	}
	
	
	/**
	 * 删除访客记录 (只是设置删除标记)
	 * @param visitsId
	 */
	public void removeVisitor(Integer blogId,Integer visitsId) {
		Visits visits = baseDao.findByWhere(QLBuilder.select(Visits.class).where("id").and("blogs.id").and("userDel").setQlParams(visitsId,blogId,SystemConstant.VISITS_KEEP_STATUS));
		if(visits!=null) {
			visits.setUserDel(SystemConstant.VISITS_DEL_STATUS);
		}
	}
	
	/**
	 * 更新访客状态
	 * @param visits
	 */
	public void updateVisits(Visits visits) {
		baseDao.update(visits);
	}
	
	/**
	 * 添加访客
	 * @param visits
	 */
	public void addVisits(Visits visits) {
		Visits visit = baseDao.findByWhere(QLBuilder.select(Visits.class).where("blogs.id").and("visitor.id").setQlParams(visits.getBlogs().getId(),visits.getVisitor().getId()));
		if(visit!=null) {
			visit.setVisittime(new Date());
			visit.setVisiturl(visits.getVisiturl());
			visit.setUserDel(SystemConstant.VISITS_KEEP_STATUS);
		}else {
			baseDao.save(visits);
		}
	}
	
	/**
	 * 我的足迹
	 * @param userId 当前用户
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Visits> getFootsPager(Integer userId,int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Visits.class).where("visitor.id").desc("visittime").setQlParams(userId), pageNo, pageSize);
	}
}
