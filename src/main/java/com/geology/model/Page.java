package com.geology.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class Page implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize = 2;
	private int thisPage;
	private int maxPage;
	private int rowsNum;
	private int lastPage;
	private int nextPage;

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getThisPage()
	{
		return thisPage;
	}

	public void setThisPage(int thisPage)
	{
		this.thisPage = thisPage;
	}

	public int getMaxPage()
	{
		return maxPage;
	}

	public void setMaxPage(int maxPage)
	{
		this.maxPage = maxPage;
	}

	public int getRowsNum()
	{
		return rowsNum;
	}

	public void setRowsNum(int rowsNum)
	{
		this.rowsNum = rowsNum;
	}

	public int getLastPage()
	{
		if (thisPage > 1)
			return thisPage - 1;
		return 0;
	}

	public void setLastPage(int lastPage)
	{
		this.lastPage = lastPage;
	}

	public int getNextPage()
	{
		if (thisPage < maxPage)
			return thisPage + 1;
		return maxPage;
	}

	public void setNextPage(int nextPage)
	{
		this.nextPage = nextPage;
	}
	
	public static void main(String[] args)
	{
		Page page = new Page();
		page.setThisPage(4);
		page.setPageSize(15);
		page.setRowsNum(45);
		page.setMaxPage(3);
		
		System.out.println(JSON.toJSONString(page));
	}

}
