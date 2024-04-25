package org.utwente.cs.ds.semi.lod.ieee.scraping.util;

import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils
{
	public static Properties loadProperties() throws ApplicationException
	{
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application.properties";
		try
		{
			Properties appProps = new Properties();
			appProps.load(new FileInputStream(appConfigPath));
			return appProps;
		}
		catch (IOException e)
		{
			throw new ApplicationException("Failed to load properties file");
		}
	}
}
