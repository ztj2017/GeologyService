package com.geology.appconfig;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/*")
public class AirConfig extends ResourceConfig
{
	public AirConfig()
	{
		packages("com.geology.roa");
	}
}
