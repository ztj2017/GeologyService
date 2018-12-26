package com.geology.model;

import java.io.Serializable;

public class User implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String userName;
	private String passPharse;
	private Long loginTime;
	private String extraInfo;
	private Integer roleType = 0; // 0 查  1 增删改查 
	private Integer status = 0; // 登陆状态
	private String parentId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassPharse()
	{
		return passPharse;
	}

	public void setPassPharse(String passPharse)
	{
		this.passPharse = passPharse;
	}

	public Long getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(Long loginTime)
	{
		this.loginTime = loginTime;
	}

	public String getExtraInfo()
	{
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo)
	{
		this.extraInfo = extraInfo;
	}

	public Integer getRoleType()
	{
		return roleType;
	}

	public void setRoleType(Integer roleType)
	{
		this.roleType = roleType;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

}
