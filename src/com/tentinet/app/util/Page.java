package com.tentinet.app.util;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;

/**
 * 封装分页
 * @author jobs1127
 *
 */
public class Page implements Serializable {
	private static final long serialVersionUID = -6286139129822199092L;
	public static final String ASC = "asc";//升序
	public static final String DESC = "desc";//降序
	public static final int MIN_PAGESIZE = 1;//最小页
	public static final int MAX_PAGESIZE = 500;//最大页
	protected int pageNo = 1;//当前页
	protected int pageSize = 15;//每页显示多少条
	protected String orderBy = null;//排序
	protected String order = "asc";//排序
	protected boolean autoCount = false;//是否自动分页
	protected int totalCount = -1;//共有多少条记录

	public Page() {
	}

	public Page(int pageSize) {
		setPageSize(pageSize);
	}

	public Page(int pageSize, boolean autoCount) {
		setPageSize(pageSize);
		this.autoCount = autoCount;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < MIN_PAGESIZE) {
			this.pageNo = 1;
		}
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize == -1) {
			return;
		}
		if (pageSize < MIN_PAGESIZE) {
			this.pageSize = MIN_PAGESIZE;
		}
		else if (pageSize > MAX_PAGESIZE) {
			this.pageSize = MAX_PAGESIZE;
		}
	}

	public int getFirst() {
		return (this.pageNo - 1) * this.pageSize;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(this.orderBy);
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		String[] orders = StringUtils.split(order, ',');
		for (String orderStr : orders) {
			if ((!StringUtils.equalsIgnoreCase("desc", orderStr))
					&& (!StringUtils.equalsIgnoreCase("asc", orderStr))) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}
		this.order = order.toLowerCase();
	}

	public String getPageRequest() {
		return getPageNo() + "|" + StringUtils.defaultString(getOrderBy())
				+ "|" + getOrder();
	}

	public void setPageRequest(String pageRequest) {
		if (StringUtils.isBlank(pageRequest)) {
			return;
		}

		String[] params = StringUtils.splitPreserveAllTokens(pageRequest, '|');

		if (StringUtils.isNumeric(params[0])) {
			setPageNo(Integer.valueOf(params[0]).intValue());
		}

		if (StringUtils.isNotBlank(params[1])) {
			setOrderBy(params[1]);
		}

		if (StringUtils.isNotBlank(params[2]))
			setOrder(params[2]);
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取共多少页
	 * @return
	 */
	public int getTotalPages2() {
		if (this.totalCount < 0) {
			return -1;
		}
		int count = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0) {
			count++;
		}
		return count;
	}
	
	public int getTotalPages() {
		if (this.totalCount < 0) {
			return -1;
		}
		double t = this.totalCount;
		double p = this.pageSize;
		int count = (int)Math.ceil(t/p);
		//System.out.println("count="+count);
		return count;
	}
	
	//是否有下一页
	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}
	//下一页
	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}
	//是否有上一页
	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}
	//上一页
	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}
	public static void main(String[] args) {
		String s = new Page().getInverseOrder();
		System.out.println(s);
	}
	/***
	 * 逆序
	 * @return
	 */
	public String getInverseOrder() {
		this.order = "desc,asc";
		String[] orders = StringUtils.split(this.order, ',');
		for (int i = 0; i < orders.length; i++) {
			if ("desc".equals(orders[i]))
				orders[i] = "asc";
			else {
				orders[i] = "desc";
			}
		}
		return StringUtils.join(orders);
	}

	public boolean isPageSetted() {
		return this.pageSize != -1;
	}
}
