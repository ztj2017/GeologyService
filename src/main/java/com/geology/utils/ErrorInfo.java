package com.geology.utils;

public class ErrorInfo
{
	private String errorCode;
	
	private String casue;

	public ErrorInfo()
	{
		
	}
	public ErrorInfo(String errorCode, String casue)
	{
		this.errorCode = errorCode;
		this.casue = casue;
	}
	
	public String getCasue()
	{
		return casue;
	}

	public void setCasue(String casue)
	{
		this.casue = casue;
	}
	
	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}


}
