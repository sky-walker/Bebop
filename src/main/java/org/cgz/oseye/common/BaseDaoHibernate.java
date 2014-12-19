package org.cgz.oseye.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.model.BaseModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/** 
 * @author 陈广志 
 * @Description: Dao的Hibernate实现类
 */
@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoHibernate extends HibernateDaoSupport implements BaseDao {

	@Resource(name="sessionFactory")
	public void setASessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 保存实体对象
	 */
	public <T extends BaseModel> void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	
	/**
	 * 更新实体对象
	 */
	public <T extends BaseModel> void update(T entity) {
		getHibernateTemplate().update(entity);
	}
	
	/**
	 * 条件更新
	 * @param qlBuilder
	 */
	public <T extends BaseModel> void update(QLBuilder qlBuilder) {
		Query query = getSession().createQuery(qlBuilder.build());
        query = QLBuilder.setQueryParameters(query, qlBuilder.getQlParams());
        query.executeUpdate();
	}
	
	/**
	 * 删除实体对象
	 */
	public <T extends BaseModel,PK extends Serializable> void delete(Class<T> entityClass,PK entityId) {
		T entity = getHibernateTemplate().get(entityClass, entityId);
		if(entity!=null) {
			getHibernateTemplate().delete(entity);
		}
	}
	
	/**
	 * 条件删除
	 * @param qlBuilder
	 */
	public <T extends BaseModel,PK extends Serializable> void deleteByWhere(QLBuilder qlBuilder) {
		Query query = getSession().createQuery(qlBuilder.build());
        query = QLBuilder.setQueryParameters(query, qlBuilder.getQlParams());
        query.executeUpdate();
	}
	
	/************************************************查询开始***************************************************/
	
	/**
	 * 单体查询
	 */
	public <T extends BaseModel,PK extends Serializable> T find(Class<T> entityClass, PK entityId) {
		return (T) getHibernateTemplate().load(entityClass, entityId);
	}
	
	public <T extends BaseModel, PK extends Serializable> T get(Class<T> entityClass, PK entityId) {
		return (T) getHibernateTemplate().get(entityClass, entityId);
	}
	
	/**
	 * 条件单体查询
	 */
	public <T extends BaseModel> T findByWhere(QLBuilder qlBuilder) {
        Query query = getSession().createQuery(qlBuilder.build());
        query = QLBuilder.setQueryParameters(query, qlBuilder.getQlParams());
        query.setCacheable(true);
		return (T) query.uniqueResult();
	}
	
	
	/**
	 * 判断是否存在
	 */
	public <T extends BaseModel> boolean isExistedByWhere(QLBuilder qlBuilder) {
		long count = getCountByWhere(qlBuilder);
		return count>0 ? true : false;
	}

	/**
	 * 统计查询
	 */
	public <T> long getCount(Class<T> entityClass) {
		return getCountByWhere(QLBuilder.count(entityClass));
	}
	
	public <T> long getCount_cache(Class<T> entityClass) {
		return getCountByWhere(QLBuilder.count(entityClass));
	}
	
	public long getCountByWhere(QLBuilder qlBuilder) {
		Query query = getSession().createQuery(qlBuilder.build());
		return (Long) QLBuilder.setQueryParameters(query, qlBuilder.getQlParams()).uniqueResult();
	}
	
	/**
	 * 拿取前size条记录
	 * @param <T>
	 * @param qlBuilder 查询的必须是对象的id列表
	 * @param size
	 * @return
	 */
	public <T extends BaseModel> List<T> getListData(QLBuilder qlBuilder,Integer size) {
		Pager<T> pager = getScrollData(qlBuilder, -1, size);
		return pager.getResultList();
	}

	/**
	 * 分页查询
	 * @param <T>
	 * @param qlBuilder 查询的必须是对象的id列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public <T extends BaseModel> Pager<T> getScrollData(QLBuilder qlBuilder,Integer pageNo,Integer pageSize) {
		Pager<T> pager = new Pager<T>();
		Query query = getSession().createQuery(qlBuilder.build());
		//设置查询参数
		query = QLBuilder.setQueryParameters(query, qlBuilder.getQlParams());
		query.setCacheable(true);
		long totalRecords = 0;
		//需要进行分页查询
		if(pageNo!=-1 && pageSize!=-1) {
			//totalRecords = getCountByWhere(qlBuilder.switchToCount());
			totalRecords = query.list().size();
			query.setFirstResult((pageNo-1)>=0?(pageNo-1)*pageSize:0).setMaxResults(pageSize);
		}
		//如果只查询前N条记录
		if(pageNo==-1 && pageSize>0) {
			query.setFirstResult(0).setMaxResults(pageSize);
		}
		List<T> list = query.list();
		pager.setResultList(list);
		pager.setPageNo(pageNo);
		if(list!=null && list.size()>0) {
			pager.setTotalRecords(totalRecords);
		}
		return pager;
	}
	

	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	
	public void flush() {
		getHibernateTemplate().flush();
	}
	
	public void clear() {
		getHibernateTemplate().clear();
	}
}
