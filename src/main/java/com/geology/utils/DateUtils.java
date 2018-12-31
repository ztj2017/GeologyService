package com.geology.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
	public static String stampToDate(String s)
	{
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}
}
