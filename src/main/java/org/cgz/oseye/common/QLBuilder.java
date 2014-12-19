package org.cgz.oseye.common;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;


/** 
 * @author 陈广志 
 * @Description: 链式SQL语句构建工具
 */
@SuppressWarnings("rawtypes")
public class QLBuilder {
	
	private StringBuffer ql;
	
	private Object[] qlParams;
	
	private Class entityClass;
	
	private StringBuffer cachekey;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	public QLBuilder() {
		
	}
	
	public QLBuilder(StringBuffer ql) {
		this.ql = ql;
	}
	
	public QLBuilder(StringBuffer ql,StringBuffer cachekey) {
		this.ql = ql;
		this.cachekey = cachekey;
	}
	
	public StringBuffer getCachekey() {
		return cachekey;
	}

	public StringBuffer getQl() {
		return ql;
	}
	
	public void setQl(StringBuffer ql) {
		this.ql = ql;
	}
	
	public Object[] getQlParams() {
		return qlParams;
	}

	public QLBuilder setQlParams(Object... qlParams) {
		this.qlParams = qlParams;
		return this;
	}

	/**
	 * 获得实体类的名称
	 * @param entityClass
	 * @return
	 */
	public static <T> String getEntityName(Class<T> entityClass) {
		return entityClass.getSimpleName();
	}
	
	/**
	 * 构建统计语句
	 * @param entityClass
	 * @return
	 */
	public static <T> QLBuilder count(Class<T> entityClass) {
		StringBuffer ql = new StringBuffer();
		StringBuffer cahcehKey = new StringBuffer("count");
		ql.append("SELECT COUNT(o) FROM ").append(QLBuilder.getEntityName(entityClass)).append(" o ");
		QLBuilder qlBuilder = new QLBuilder(ql,cahcehKey);
		qlBuilder.entityClass = entityClass;
		return qlBuilder;
	}
	
	/**
	 * 构建更新语句
	 * @param entityClass
	 * @return
	 */
	public static <T> QLBuilder update(Class<T> entityClass,String... setFields) {
		StringBuffer ql = new StringBuffer();
		StringBuffer cahcehKey = new StringBuffer();
		ql.append("UPDATE ").append(QLBuilder.getEntityName(entityClass)).append(" o ");
		if(setFields!=null && setFields.length>0) {
			ql.append(" SET ");
			for(String setField:setFields) {
				ql.append("o.").append(setField).append("=?").append(",");
			}
			ql.deleteCharAt(ql.length()-1);
		}
		QLBuilder qlBuilder = new QLBuilder(ql,cahcehKey);
		qlBuilder.entityClass = entityClass;
		return qlBuilder;
	}
	
	/**
	 * 构建删除语句
	 * @param entityClass
	 * @return
	 */
	public static <T> QLBuilder delete(Class<T> entityClass) {
		StringBuffer ql = new StringBuffer();
		StringBuffer cahcehKey = new StringBuffer();
		ql.append("DELETE FROM ").append(QLBuilder.getEntityName(entityClass)).append(" o ");
		QLBuilder qlBuilder = new QLBuilder(ql,cahcehKey);
		qlBuilder.entityClass = entityClass;
		return qlBuilder;
	}
	
	
	/**
	 * 构建单表多字段查询语句
	 * @param entityClass
	 * @param selectFields
	 * @return
	 */
	public static <T> QLBuilder select(Class<T> entityClass,String... selectFields) {
		StringBuffer ql = new StringBuffer();
		StringBuffer cahcehKey = new StringBuffer();
		if(selectFields!=null && selectFields.length>0) {
			ql.append("SELECT ");
			for(String selectField:selectFields) {
				ql.append("o.").append(selectField).append(",");
			}
			ql.deleteCharAt(ql.length()-1);
		}else {
			ql.append("SELECT o");
		}
		ql.append(" FROM ").append(QLBuilder.getEntityName(entityClass)).append(" o ");
		QLBuilder qlBuilder = new QLBuilder(ql,cahcehKey);
		qlBuilder.entityClass = entityClass;
		return qlBuilder;
	}
	
	
	
    /**
     * 构建第一个where查询条件,如果没有QLCompare,默认是=
     * @param whereField
     * @return
     */
	public QLBuilder where(String whereField) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" WHERE o.").append(whereField).append("=").append("? ");
		if(cahcehKey!=null) {
			cahcehKey.append("#").append(whereField).append("=").append("?");
		}
		return this;
	}
	
	/**
	 * 构建第一个where查询条件
	 * @param whereField
	 * @param qlCompare
	 * @return
	 */
	public QLBuilder where(String whereField,QLCompare qlCompare) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" WHERE o.").append(whereField).append(qlCompare.getValue()).append("? ");
		cahcehKey.append("#").append(whereField).append(qlCompare.getValue()).append("?");
		return this;
	}
	
	/**
	 * where in语句(必须先设置参数)
	 * @param whereInField
	 * @return
	 */
	public QLBuilder whereIn(String whereInField) {
		StringBuffer ql = this.getQl();
		ql.append(" WHERE o.").append(whereInField).append(" IN(");
		if(qlParams.length<1) {
			throw new RuntimeException("请先设置参数");
		}else {
			for(int i=0;i<qlParams.length;i++) {
				ql.append("?,");
			}
			ql.deleteCharAt(ql.length()-1);
		}
		ql.append(")");
		return this;
	}
	
	/**
	 * where in语句(必须先设置参数)
	 * @param whereInField
	 * @return
	 */
	public QLBuilder whereIn(String whereInField,Object[] whereInParams) {
		StringBuffer ql = this.getQl();
		ql.append(" WHERE o.").append(whereInField).append(" IN(");
		for(int i=0;i<whereInParams.length;i++) {
			ql.append(whereInParams[i]+",");
		}
		ql.deleteCharAt(ql.length()-1);
		ql.append(")");
		return this;
	}
	
	
	/**
     * 构建AND条件
     * @param whereField
     * @return
     */
	public QLBuilder and(String whereField) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" AND o.").append(whereField).append("=").append("? ");
		cahcehKey.append("&").append(whereField).append("=").append("?");
		return this;
	}
	
	/**
	 * 构建AND条件
	 * @param whereField
	 * @param qlCompare
	 * @return
	 */
	public QLBuilder and(String whereField,QLCompare qlCompare) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" AND o.").append(whereField).append(qlCompare.getValue()).append("? ");
		cahcehKey.append("&").append(whereField).append(qlCompare.getValue()).append("?");
		return this;
	}

    /**
     * 构建OR条件
     * @param whereField
     * @return
     */
	public QLBuilder or(String whereField) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" OR o.").append(whereField).append("=").append("? ");
		cahcehKey.append("|").append(whereField).append("=").append("?");
		return this;
	}
	
	/**
	 * 构建OR条件
	 * @param whereField
	 * @param qlCompare
	 * @return
	 */
	public QLBuilder or(String whereField,QLCompare qlCompare) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" OR o.").append(whereField).append(qlCompare.getValue()).append("? ");
		cahcehKey.append("|").append(whereField).append(qlCompare.getValue()).append("?");
		return this;
	}
	
	/**
	 * 构建GROUP BY条件
	 * @param whereField
	 * @param qlCompare
	 * @return
	 */
	public QLBuilder group(String groupField) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		ql.append(" GROUP BY o.").append(groupField);
		cahcehKey.append("||").append(groupField);
		return this;
	}
	
	/**
	 * 降序排列
	 * @param orderField
	 * @return
	 */
	public QLBuilder desc(String orderField) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		if(ql.indexOf("ORDER BY")!=-1) {
			ql.append(" , ").append("o.").append(orderField).append(" DESC");
		}else {
			ql.append(" ORDER BY ").append("o.").append(orderField).append(" DESC");
		}
		cahcehKey.append("\\").append(orderField);
		return this;
	}
	
	/**
	 * 升序排列
	 * @param orderField
	 * @return
	 */
	public QLBuilder asc(String orderField) {
		StringBuffer ql = this.getQl();
		StringBuffer cahcehKey = this.getCachekey();
		if(ql.indexOf("ORDER BY")!=-1) {
			ql.append(" , ").append("o.").append(orderField).append(" ASC");
		}else {
			ql.append(" ORDER BY ").append("o.").append(orderField).append(" ASC");
		}
		ql.append(" ORDER BY ").append("o.").append(orderField).append(" ASC");
		cahcehKey.append("/").append(orderField);
		return this;
	}


	/**
	 * 分页参数设置,只用于缓存key的生成
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public QLBuilder pager(Integer pageNo,Integer pageSize) {
		StringBuffer cahcehKey = this.getCachekey();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		cahcehKey.append("[pageNo=").append(pageNo).append(",").append("pageSize=").append(pageSize).append("]");
		return this;
	}
	
	/**
	 * 根据HQL进行查询
	 * @param hql
	 * @return
	 */
	public static QLBuilder queryByHql(String hql) {
		StringBuffer ql = new StringBuffer(hql);
		QLBuilder qlBuilder = new QLBuilder(ql);
		return qlBuilder;
	}
	
	public String build() {
		return this.getQl().toString();
	}
	
	/**
	 * 根据SQL语句拼装chache key
	 * @return
	 */
	public String getQlCacheKey() {
		StringBuffer cahcehKey = this.getCachekey();
		Object[] qlParams = this.getQlParams();
		String[] strs = cahcehKey.toString().split("\\?");
		StringBuffer key = new StringBuffer();
		/**
		 * 第一种情况,分割后字符数组大小和参数数量一样
		 * xxx?xxx?xxx?xxxx?xxx?
		 * xxx xxx xxx xxxx xxx 
		 */
		if(qlParams.length==strs.length) {
			for(int i=0;i<qlParams.length;i++) {
				key.append(strs[i]).append(qlParams[i]);
			}
		/**
		 * 第二种情况,分割后字符数组大小比参数数量多一个
		 * xxx?xxx?xxx?xxxx?xxx?xxxx
		 * xxx xxx xxx xxxx xxx xxxx
		 */
		}else if(strs.length>qlParams.length) {
			for(int i=0;i<qlParams.length;i++) {
				key.append(strs[i]).append(qlParams[i]);
			}
			key.append(strs[strs.length-1]);
		}
		return key.toString();
	}
	
	/**
	 * ql语句切换到count
	 * @param ql
	 * @return
	 */
	public QLBuilder switchToCount(){
		StringBuffer ql = this.getQl();
		QLBuilder qlBuilder = new QLBuilder();
		BeanUtils.copyProperties(this, qlBuilder);
		qlBuilder.setQl(new StringBuffer("SELECT COUNT(o) "+ql.toString().substring(ql.toString().indexOf("FROM"))));
		return qlBuilder;
	} 
	
	/**
	 * 将对象查询转换成对象主键查询
	 * @return
	 */
	public QLBuilder switchToQueryId() {
		StringBuffer ql = this.getQl();
		this.ql = new StringBuffer("SELECT o.id "+ql.toString().substring(ql.toString().indexOf("FROM")));
		return this;
	}
	
	/**
	 * 设置查询参数
	 * @param query
	 * @param queryParams
	 */
	public static Query setQueryParameters(Query query, Object... queryParams) {
		if(queryParams != null && queryParams.length > 0) {
			for(int i=0;i<queryParams.length;i++) {
				query.setParameter(i, queryParams[i]);
			}
		}
		return query;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}
}
