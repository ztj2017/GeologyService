package com.geology.appconfig;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.geology.filter.SessionFilter;

@ApplicationPath("/*")
public class AirConfig extends ResourceConfig
{
	public AirConfig()
	{
		packages("com.geology.roa");
		register(MultiPartFeature.class);
		register(SessionFilter.class);
	}
}
