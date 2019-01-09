package com.geology.model;

import java.io.Serializable;

public class Relic implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String bh;
	private String name;
	private String coordinate;
	private String descr;
	private String category;
	private String sPic;
	private String pic;
	private String scenicSpotsId;
	private String scenicSpotsIdForUpdate;
	private String updateTime;
	private String extra;
	private String deleteImg;
	public String getDeleteImg()
	{
		return deleteImg;
	}
	public void setDeleteImg(String deleteImg)
	{
		this.deleteImg = deleteImg;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getBh()
	{
		return bh;
	}
	public void setBh(String bh)
	{
		this.bh = bh;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCoordinate()
	{
		return coordinate;
	}
	public void setCoordinate(String coordinate)
	{
		this.coordinate = coordinate;
	}
	public String getDescr()
	{
		return descr;
	}
	public void setDescr(String descr)
	{
		this.descr = descr;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getsPic()
	{
		return sPic;
	}
	public void setsPic(String sPic)
	{
		this.sPic = sPic;
	}
	public String getPic()
	{
		return pic;
	}
	public void setPic(String pic)
	{
		this.pic = pic;
	}
	public String getScenicSpotsId()
	{
		return scenicSpotsId;
	}
	public void setScenicSpotsId(String scenicSpotsId)
	{
		this.scenicSpotsId = scenicSpotsId;
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
	public String getScenicSpotsIdForUpdate()
	{
		return scenicSpotsIdForUpdate;
	}
	public void setScenicSpotsIdForUpdate(String scenicSpotsIdForUpdate)
	{
		this.scenicSpotsIdForUpdate = scenicSpotsIdForUpdate;
	}

	
}
