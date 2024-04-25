package org.utwente.cs.ds.semi.lod.ieee.scraping.service;

import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;
import org.utwente.cs.ds.semi.lod.ieee.scraping.request.ApiRequest;
import org.utwente.cs.ds.semi.lod.ieee.scraping.response.IEEEAuthenticationToken;
import org.utwente.cs.ds.semi.lod.ieee.scraping.util.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryManager
{
	private final static Logger LOGGER = Logger.getLogger(QueryManager.class.getName());
	private static String API_KEY;
	private static String DEFAULT_API_URL;
	private static String OPEN_ACCESS_URL;
	private static String AUTHOR_URL;

	private static String USAGE_URL;

	private static String CUSTOMER_ID;

	private static String AUTH_TOKEN_URL;
	private static String AUTH_TOKEN;
	private static Integer AUTH_TOKEN_ALIVE;

	static
	{
		Properties appProps = PropertiesUtils.loadProperties();
		API_KEY = appProps.getProperty("api.key");
		DEFAULT_API_URL = appProps.getProperty("api.endpoint");
		OPEN_ACCESS_URL = appProps.getProperty("open.access.endpoint");
		CUSTOMER_ID = appProps.getProperty("customer.id");
		USAGE_URL = appProps.getProperty("usage.endpoint") + CUSTOMER_ID
			+ appProps.getProperty("usage.endpoint.service");
		AUTHOR_URL = appProps.getProperty("author.endpoint");
		AUTH_TOKEN_URL = appProps.getProperty("auth.token.endpoint");
		AUTH_TOKEN = appProps.getProperty("auth.token");
		AUTH_TOKEN_ALIVE = Integer.parseInt(appProps.getProperty("auth.token.alive"));
	}

	public static String buildOpenAccessQuery(ApiRequest apiRequest)
	{
		String params =
			new StringBuilder(apiRequest.getArticleTitle()).append("/fulltext")
				.append("?apikey=").append(API_KEY)
				.append("&format=").append(apiRequest.getDataFormat())
				.toString();
		String url = OPEN_ACCESS_URL + params;
		return url;
	}

	public static String buildUsageRequestQuery(ApiRequest apiRequest,
	IEEEAuthenticationToken token)
	{
		String params =
			new StringBuilder("?apikey=").append(API_KEY)
				.append("&includeTerms=true")
				.append("&startDate=").append(apiRequest.getStartDate())
				.append("&endDate=").append(apiRequest.getEndDate())
				.append("&cltoken=").append(token.getToken().trim())
				.toString();
		String url = USAGE_URL + params;
		return url;
	}

	public static String buildFullTextRequestQuery(
		ApiRequest apiRequest,
		IEEEAuthenticationToken token
	)
	{
		String params =
			new StringBuilder(apiRequest.getArticleNumber()).append("/fulltext")
				.append("?apikey=").append(API_KEY)
				.append("&format=").append(apiRequest.getDataFormat())
				.append("&cltoken=").append(token.getToken().trim())
				.toString();
		String url = OPEN_ACCESS_URL + params;
		return url;
	}

	public static String buildCitationsQuery(ApiRequest apiRequest)
	{
		String params =
			new StringBuilder(apiRequest.getArticleNumber()).append("/citation")
				.append("?apikey=").append(API_KEY)
				.append("&format=").append(apiRequest.getDataFormat())
				.append("&type=").append(apiRequest.getCitationType())
				.append("&max_records=").append(apiRequest.getMaxRecords())
				.append("&start_record=").append(apiRequest.getStartRecord())
				.toString();
		String url = OPEN_ACCESS_URL + params;
		return url;
	}

	public static String buildBioEndpointQuery(ApiRequest apiRequest)
	{
		String params =
			new StringBuilder(apiRequest.getAuthorNumber())
				.append("?apikey=").append(API_KEY)
				.append("&format=").append(apiRequest.getDataFormat())
				.toString();
		String url = AUTHOR_URL + params;
		return url;
	}

	public static String buildDefaultQuery(ApiRequest apiRequest)
	{
		Map<String, String> queryParams = new HashMap<>();
		apiRequest.appendQueryParams(queryParams);
		appendSortingFilteringAndStartRecordsAsParameters(queryParams, apiRequest);
		StringBuilder builder =
			new StringBuilder()
				.append("?apikey=").append(API_KEY);
		appendQueryParametersToWebTarget(builder, queryParams, false);
		String params = builder.toString();
		String url = DEFAULT_API_URL + params;
		return url;
	}

	private static void appendSortingFilteringAndStartRecordsAsParameters(
		Map<String, String> queryParams,
		ApiRequest request
	)
	{
		if (!request.getResultsFilter().isEmpty())
		{
			for (String key : request.getResultsFilter().keySet())
			{
				String value = request.getResultsFilter().get(key);
				queryParams.put(key.trim().toLowerCase(), value);
			}
		}
		Map<String, String> dataFormatParams = request.buildDataFormatParameters();
		if (!dataFormatParams.isEmpty())
		{
			for (String key : dataFormatParams.keySet())
			{
				String value = dataFormatParams.get(key);
				queryParams.put(key.trim().toLowerCase(), value);
			}
		}
		//queryParams.put("max_records", request.getMaxRecords() + "");
		//queryParams.put("sort_field", request.getSortByField());
		//queryParams.put("sort_order", request.getSortOrder());
//		queryParams.put(
//			"start_record", request.getStartRecord() + ""
//		);
		if (request.isUsingBoolean())
		{
			queryParams.put("querytext", "[" + request.getBooleanText() + "]");
		}
		applyFacets(queryParams, request);
		for (String key : queryParams.keySet())
		{
			String value = queryParams.get(key);
			LOGGER.log(Level.INFO, "[Param:{0}, Value:{1}]", new Object[]{key, value});
		}
	}

	private static void applyFacets(Map<String, String> queryParams, ApiRequest request)
	{
		boolean facetApplied = false;
		if (request.isUsingFacet())
		{
			if (request.specified(request.getAuthorFacetText()))
			{
				if (!facetApplied)
				{
					queryParams.put("facet", "d-au");
					queryParams.put("querytext", request.getAuthorFacetText());
					facetApplied = true;
				}
				else
				{
					queryParams.put("d-au", request.getAuthorFacetText());
				}
			}
			if (request.specified(request.getContentTypeFacetText()))
			{
				if (!facetApplied)
				{
					queryParams.put("facet", "d-pubtype");
					queryParams.put("querytext", request.getContentTypeFacetText());
					facetApplied = true;
				}
				else
				{
					queryParams.put("d-pubtype", request.getContentTypeFacetText());
				}
			}
			if (request.specified(request.getFacetText()))
			{
				if (!facetApplied)
				{
					queryParams.put("facet", "facet");
					queryParams.put("querytext", request.getFacetText());
					facetApplied = true;
				}
				else
				{
					queryParams.put("facet", request.getFacetText());
				}
			}
			if (request.specified(request.getPublicationFacetText()))
			{
				if (!facetApplied)
				{
					queryParams.put("facet", "d-year");
					queryParams.put("querytext", request.getPublicationFacetText());
					facetApplied = true;
				}
				else
				{
					queryParams.put("d-year", request.getPublicationFacetText());
				}
			}
			if (request.specified(request.getPublisherFacetText()))
			{
				if (!facetApplied)
				{
					queryParams.put("facet", "d-publisher");
					queryParams.put("querytext", request.getPublisherFacetText());
					facetApplied = true;
				}
				else
				{
					queryParams.put("d-publisher", request.getPublisherFacetText());
				}
			}
		}
	}

	private static StringBuilder appendQueryParametersToWebTarget(
		StringBuilder builder,
		Map<String, String> searchParams,
		boolean makeKeyLowerCase
	)
	{
		if (searchParams == null || searchParams.isEmpty())
		{
			throw new ApplicationException("One or more search parameters are expected to query the API");
		}
		for (String key : searchParams.keySet())
		{
			String value = searchParams.get(key);
			builder.append("&")
				.append(makeKeyLowerCase ? key.trim().toLowerCase() : key.trim())
				.append("=").append(value);
		}
		return builder;
	}

}
