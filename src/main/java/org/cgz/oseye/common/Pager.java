package org.cgz.oseye.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager<T> {

	/**每页显示的记录数**/
	private int pageSize = 15;
	
	/**当前页**/
	private int pageNo = 1;
	
	/**查询结果集**/
	private List<T> resultList;
	
	/**总记录数**/
	private long totalRecords = 0;
	
	/**查询参数**/
	private Map<String, Object> queryBy = new HashMap<String, Object>();
	
	public Pager() { }
	
	public Pager(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public Pager(int pageNo,int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Pager(int pageSize,List<T> resultList,long totalRecords) {
		this.resultList = resultList;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
	}
	
	public Pager(List<T> resultList,long totalRecords) {
		this.resultList = resultList;
		this.totalRecords = totalRecords;
	}
	
	public Pager(int pageSize,int pageNo,long totalRecords) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.totalRecords = totalRecords;
	}
	
	/**
	 * 总页数
	 * @return
	 */
	public int getTotalPages() {
		return (int) ((totalRecords + pageSize - 1)/pageSize);
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = (pageNo<0)?1:pageNo;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public void setQueryBy(Map<String, Object> queryBy) {
		this.queryBy = queryBy;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void addQueryBy(String queryKey,Object queryValue) {
		this.queryBy.put(queryKey, queryValue);
	}

	public Map<String, Object> getQueryBy() {
		return queryBy;
	}
}
