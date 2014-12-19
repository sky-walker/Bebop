package org.cgz.oseye.common;

import java.io.Serializable;
import java.util.List;

import org.cgz.oseye.model.BaseModel;
import org.hibernate.Session;

public interface BaseDao {

	/**
	 * 保存实体对象
	 * @param <T>
	 * @param entity
	 */
	public <T extends BaseModel> void save(T entity);


	/**
	 * 更新实体对象
	 * @param <T>
	 * @param entity
	 */
	public <T extends BaseModel> void update(T entity);

	/**
	 * 条件更新
	 * @param qlBuilder
	 */
	public <T extends BaseModel> void update(QLBuilder qlBuilder);
	
	/**
	 * 删除实体
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param entityId
	 */
	public <T extends BaseModel, PK extends Serializable> void delete(Class<T> entityClass, PK entityId);
	

	/**
	 * 条件删除
	 * @param qlBuilder
	 */
	public <T extends BaseModel,PK extends Serializable> void deleteByWhere(QLBuilder qlBuilder);
	
	/**
	 * 单体查询
	 */
	public <T extends BaseModel, PK extends Serializable> T find(Class<T> entityClass, PK entityId);
	
	public <T extends BaseModel, PK extends Serializable> T get(Class<T> entityClass, PK entityId);
            

	/**
	 * 条件查询
	 * @param <T>
	 * @param qlBuilder
	 * @return
	 */
	public <T extends BaseModel> T findByWhere(QLBuilder qlBuilder);

	public <T extends BaseModel> boolean isExistedByWhere(QLBuilder qlBuilder);

	/**
	 * 统计查询
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> long getCount(Class<T> entityClass);


	/**
	 * 条件统计查询
	 * @param qlBuilder
	 * @return
	 */
	public long getCountByWhere(QLBuilder qlBuilder);
	
	/**
	 * 拿取前size条记录
	 * @param <T>
	 * @param qlBuilder 查询的必须是对象的id列表
	 * @param size
	 * @return
	 */
	public <T extends BaseModel> List<T> getListData(QLBuilder qlBuilder,Integer size);

	/**
	 * 列表查询
	 * @param <T>
	 * @param qlBuilder
	 * @return
	 */
	public <T extends BaseModel> Pager<T> getScrollData(QLBuilder qlBuilder,Integer pageNo, Integer pageSize);
	
	public Session getCurrentSession();

	public void flush();

	public void clear();
}