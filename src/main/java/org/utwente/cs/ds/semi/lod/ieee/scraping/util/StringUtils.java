package org.utwente.cs.ds.semi.lod.ieee.scraping.util;

public class StringUtils
{
	public static boolean specified(String arg){
		return (arg != null && !arg.trim().isEmpty()) ? true : false;
	}
}
