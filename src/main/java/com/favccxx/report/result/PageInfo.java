package com.favccxx.report.result;

import java.util.List;

public class PageInfo<T> {
	

	/**
	 * 当前页索引
	 */
	private int pageIndex;

	/**
	 * 每页显示的条目
	 */
	private int pageSize;

	/**
	 * 总记录数
	 */
	private int totalCount;

	/**
	 * 总页数
	 */
	private int totalPages;

	/**
	 * 从第X条开始索引
	 */
	private long offset;

	/**
	 * 是否为第一页
	 */
	private boolean isFirst;

	/**
	 * 是否是最后一页
	 */
	private boolean isLast;
	
	private List<T> dataList;

	public PageInfo(int pageIndex, int pageSize, int totalCount) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		if(pageIndex==totalPages) {
			this.isFirst = true;
		}
		if(pageIndex==totalPages) {
			this.isLast = true;
		}
	}
	
	public PageInfo(int pageIndex, int pageSize, int totalCount, List<T> dataList) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.dataList = dataList;
		this.totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		
		if(pageIndex==totalPages) {
			this.isFirst = true;
		}
		if(pageIndex==totalPages) {
			this.isLast = true;
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	

	

}
