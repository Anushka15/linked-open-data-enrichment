package org.utwente.cs.ds.semi.lod.ieee.scraping.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * This a simple singleton that would act as a persistent store to save the auth token. If you
 * are using the browser, you can replace the TokenManager with the LocalStorage.
 */
public class TokenManager
{
	private Map<Object, Object> map;
	public static final String TOKEN_DT = "tokenDate";
	public static final String TOKEN_VAL = "tokenVal";
	private static final TokenManager INSTANCE = new TokenManager();

	private TokenManager()
	{
		map = new HashMap<Object, Object>();
	}

	public static TokenManager getInstance()
	{
		return INSTANCE;
	}

	public void put(String key, Object value)
	{
		map.put(key, value);
	}

	public Object get(String key)
	{
		return map.get(key);
	}

	/**
	 * Makes sure the auth token is saved in the Map and the last accessed
	 * date time is ensured to be with in the allotted keep alive time.
	 *
	 * @param timeoutMinutes -- loaded from the properties file.
	 * @return - true if token is expired, false if alive.
	 */
	public boolean isExpired(int timeoutMinutes)
	{
		boolean isExpired = true;
		if(!map.isEmpty())
		{
			LocalDateTime tokenLastAccessed = (LocalDateTime)get(TOKEN_DT);
			LocalDateTime now = LocalDateTime.now();
			long diff = ChronoUnit.MINUTES.between(tokenLastAccessed, now);
			isExpired = diff >= timeoutMinutes;
		}
		return isExpired;
	}
}
