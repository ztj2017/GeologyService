package com.geology.model;

import java.io.Serializable;

public class Spots implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String owner;
	private String parkId ;
	private String info;
	private String updateTime;
	private String extra;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getOwner()
	{
		return owner;
	}
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	public String getParkId()
	{
		return parkId;
	}
	public void setParkId(String parkId)
	{
		this.parkId = parkId;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	public String getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
	public String getExtra()
	{
		return extra;
	}
	public void setExtra(String extra)
	{
		this.extra = extra;
	}
	
}
