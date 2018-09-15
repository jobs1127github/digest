package com.tentinet.weixin.util;

/**
 * 分页信息类
 */
public class Pager implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
    /**
     * 每页默认行数
     */
    private final int DEFAULT_PAGE_ROWS = 20;

    /**
     * 总行数
     */
    private int totalRows = 0;

    /**
     * 每页显示的行数
     */
    private int pageRows = DEFAULT_PAGE_ROWS;

    /**
     * 当前页
     */
    private int pageIndex = 1;
    
    /**
     * 是否传递分页对象
     */
    private boolean paged=false;

    /**
     * 默认构造方法
     */
    public Pager()
    {
    }

    /**
     * 构造方法
     * @param pageIndex 当前页
     */
    public Pager(int pageIndex)
    {
        setPageIndex(pageIndex);
    }

    /**
     * 构造方法
     * @param pageIndex 当前页
     * @param pageRows 每页行数
     */
    public Pager(int pageIndex, int pageRows)
    {
    	setPageIndex(pageIndex);
    	setPageRows(pageRows);
    }

    /**
     * 构造方法
     * @param pageIndex 当前页
     * @param pageRows 每页行数
     * @param totalRows 总行数
     */
    public Pager(int pageIndex, int pageRows, int totalRows)
    {
        setPageIndex(pageIndex);
        setPageRows(pageRows);
        setTotalRows(totalRows);
    }

    /**
     * 设置当前页
     * @param pageIndex 当前页，如果小于1，则设为1。
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex < 1 ? 1 : pageIndex;
    }

    /**
     * 设置每页行数
     * @param pageRows 每页行数，如果小于1则设为默认行数
     */
    public void setPageRows(int pageRows)
    {
        this.pageRows = pageRows < 1 ? DEFAULT_PAGE_ROWS : pageRows;
    }

    /**
     * 设置总行数，如果总行数小于0则为0。
     * @param totalRows 总行数，如果小于0则设为0。
     */
    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows < 0 ? 0 : totalRows;
    }
    
    /**
     * 设置是否分页属性
     * @return 是否分页状态 true：分页，false：不分页
     */
	public void setPaged(boolean paged) {
		this.paged = paged;
	}

    /**
     * 获得请求页数
     * @return 请求页数
     */
    public int getPageIndex()
    {
		return pageIndex;
	}
    
    /**
     * 获得请求的每页行数
     * @return 请求的每页行数
     */
    public int getPageRows()
	{
		return pageRows;
	}
    
    /**
     * 获取是否分页
     * param 是否分页状态 true：分页，false：不分页
     */
    public boolean getPaged() {
		return paged;
	}

	/**
     * 获得默认每页行数
     * @return 默认每页行数
     */
    public int getDefaultPageRows()
    {
    	return DEFAULT_PAGE_ROWS;
    }

    /**
     * 获得总行数
     * @return 总行数
     */
    public int getTotalRows()
    {
        return totalRows;
    }

    /**
     * 获得总页数
     * @return 总页数
     */
    public int getTotalPages()
    {
        return totalRows / pageRows + (totalRows % pageRows == 0 ? 0 : 1);
    }

    /**
     * 获得实际当前页的行数
     * @return 实际当前页行数
     */
    public int getCurrPageRows()
    {
    	if (pageIndex > getTotalPages())
    	{
    		return 0;
    	}
    	else if (totalRows / pageIndex < pageRows)
    	{
    		return totalRows % pageRows;
    	}
    	else
    	{
    		return pageRows;
    	}
    }

    /**
     * 获得当前页在总行数中的开始位置
     * @return 当前页在总行数中的开始位置（从0开始，可能超出总行数）
     */
    public int getPageStartRow()
    {
        return (pageIndex - 1) * pageRows;
    }

    /**
     * 获得当前页在总行数中的结束位置
     * @return 当前页在总行数中的结束位置（从0开始，可能超出总行数）
     */
    public int getPageEndRow()
    {
    	return pageIndex * pageRows - 1;
    }

    /**
     * 是否有前一页
     * @return true有前一页，否则没有
     */
    public boolean getHasPrevPage()
    {
    	return pageIndex > 1;
    }
    
    /**
     * 是否有后一页
     * @return true有后一页，否则没有
     */
    public boolean getHasNextPage()
    {
    	return pageIndex < getTotalPages();
    }
    
    @Override
    public String toString()
	{
		return "{pageIndex:" + getPageIndex() + ",pageRows:" + getPageRows() + ",totalRows:"
				+ getTotalRows() + ",totalPages:" + getTotalPages() + ",currPageRows:"
				+ getCurrPageRows() + ",pageStartRow:" + getPageStartRow() + ",pageEndRow:"
				+ getPageEndRow() + ",hasPrevPage:" + getHasPrevPage() + ",hasNextPage:"
				+ getHasNextPage() + "}";
    }
    
    public static void main(String[] args)
	{
    	System.out.println(new Pager(1, 10, 0));
    	System.out.println(new Pager(2, 10, 0));
		System.out.println(new Pager(1, 10, 20));
		System.out.println(new Pager(2, 10, 20));
		System.out.println(new Pager(3, 10, 20));
		System.out.println(new Pager(4, 10, 20));
		System.out.println(new Pager(1, 10, 25));
		System.out.println(new Pager(2, 10, 25));
		System.out.println(new Pager(3, 10, 25));
		System.out.println(new Pager(4, 10, 25));
	}
}