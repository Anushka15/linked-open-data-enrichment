package org.utwente.cs.ds.semi.lod.ieee.scraping.service;

import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;

import java.io.*;

public class FileManager
{
	public static void saveTokenFile(final String apiKey, final String token)
		throws ApplicationException
	{
		if (apiKey == null)
		{
			throw new ApplicationException("Apikey is required");
		}
		try
		{
			FileOutputStream fos = new FileOutputStream(generateAbsolutePath(apiKey));
			BufferedWriter writer = new BufferedWriter(new FileWriter(generateAbsolutePath(apiKey)));
			writer.write(token);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}

	private static String readAllLines(BufferedReader reader) throws IOException
	{
		StringBuilder content = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null)
		{
			content.append(line);
			content.append(System.lineSeparator());
		}
		return content.toString();
	}

	public static String getTokenFromFile(final String apiKey) throws ApplicationException
	{
		if (apiKey == null)
		{
			throw new ApplicationException("Apikey is required");
		}
		try (
			BufferedReader reader =
				new BufferedReader(new FileReader(generateAbsolutePath(apiKey)))
		)
		{
			return readAllLines(reader);
		}
		catch (IOException e)
		{
			throw new ApplicationException(e.getMessage());
		}
	}

	private static String generateAbsolutePath(final String apiKey)
	{
		String fileSeparator = System.getProperty("file.separator");
		String homeDirectory = System.getProperty("user.home");
		String absoluteFilePath = homeDirectory + fileSeparator + apiKey + ".txt";
		return absoluteFilePath;
	}

	public static boolean doesFileExist(final String apiKey) throws ApplicationException
	{
		if (apiKey == null)
		{
			throw new ApplicationException("Apikey is required");
		}
		File file = new File(generateAbsolutePath(apiKey));
		return file.exists();
	}

	public static boolean isFileExpired(final String apiKey) throws ApplicationException
	{
		if (apiKey == null)
		{
			throw new ApplicationException("Apikey is required");
		}
		File file = new File(generateAbsolutePath(apiKey));
		boolean fileExpired = false;
		long lastModified = file.lastModified();
		long current = System.currentTimeMillis();
		long timelapsed = current - lastModified;
		// 15 minutes
		if (timelapsed / 1000 >= 900)
		{
			fileExpired = true;
		}
		return fileExpired;
	}
}
