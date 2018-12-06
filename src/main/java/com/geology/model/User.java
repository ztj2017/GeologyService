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

}
