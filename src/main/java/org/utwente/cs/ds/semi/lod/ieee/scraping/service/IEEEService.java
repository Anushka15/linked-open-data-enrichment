package org.utwente.cs.ds.semi.lod.ieee.scraping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.utwente.cs.ds.semi.lod.ieee.model.IeeeData;
import org.utwente.cs.ds.semi.lod.ieee.model.support.IeeeResponse;
import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;
import org.utwente.cs.ds.semi.lod.ieee.scraping.request.ApiRequest;
import org.utwente.cs.ds.semi.lod.ieee.scraping.request.Defaults;
import org.utwente.cs.ds.semi.lod.ieee.scraping.response.IEEEAuthenticationToken;
import org.utwente.cs.ds.semi.lod.ieee.scraping.util.PropertiesUtils;
import org.utwente.cs.ds.semi.lod.ieee.scraping.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class IEEEService implements Defaults
{
	private final static Logger LOGGER = Logger.getLogger(IEEEService.class.getName());
	private static String API_KEY;
	private static String DEFAULT_API_URL;
	private static String OPEN_ACCESS_URL;
	private static String AUTHOR_URL;

	private static String USAGE_URL;

	private static String CUSTOMER_ID;

	private static String AUTH_TOKEN_URL;
	private static String AUTH_TOKEN;
	private static Integer AUTH_TOKEN_ALIVE;
	static ObjectMapper objectMapper = new ObjectMapper();
	public static HttpClient client = HttpClient.newHttpClient();

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

	private static IEEEAuthenticationToken authToken() throws ApplicationException
	{
		IEEEAuthenticationToken token = null;
		try
		{
			HttpRequest request = HttpRequest.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.uri(URI.create(AUTH_TOKEN_URL + "?apikey=" + API_KEY + "&auth-token=" + AUTH_TOKEN))
				.POST(HttpRequest.BodyPublishers.ofString("Post Request"))
				.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			int responseStatusCode = response.statusCode();
			if(responseStatusCode != 200){
				throw new ApplicationException(responseBody);
			}
			token = objectMapper.readValue(responseBody, IEEEAuthenticationToken.class);
			LOGGER.info("*** httpGetRequest: " + responseBody);
			LOGGER.info( "*** httpGetRequest status code: " + responseStatusCode);
		}
		catch (IOException | InterruptedException e)
		{
			throw new ApplicationException(e.getMessage());
		}
		return token;
	}

	public static String callApi(ApiRequest apiRequest) throws ApplicationException
	{
		String url = null;
		apiRequest.validateResultsFilters();
		apiRequest.validateResultsSorting();
		apiRequest.buildDataFormatParameters();
		try
		{
			if (apiRequest.isUsingOpenAccess())
			{
				url = QueryManager.buildOpenAccessQuery(apiRequest);
			}
			else if (apiRequest.isRequestingUsage())
			{
				IEEEAuthenticationToken token = retrieveAuthToken();
				url = QueryManager.buildUsageRequestQuery(apiRequest, token);
			}
			else if (apiRequest.isRequestingFullText())
			{
				IEEEAuthenticationToken token = retrieveAuthToken();
				url = QueryManager.buildFullTextRequestQuery(apiRequest, token);
			}
			else if (apiRequest.isCitationLookup())
			{
				url = QueryManager.buildCitationsQuery(apiRequest);
			}
			else if (apiRequest.isRequestingBio())
			{
				url = QueryManager.buildBioEndpointQuery(apiRequest);
			}
			else
			{
				url = QueryManager.buildDefaultQuery(apiRequest);
			}
			LOGGER.info("Url generated:" + url);
			HttpRequest request = HttpRequest.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.uri(URI.create(url))
				.GET()
				.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			int responseStatusCode = response.statusCode();
			LOGGER.info("Response Status Code:" + responseStatusCode);
			if(responseStatusCode != 200){
				throw new ApplicationException(responseBody);
			}
			return responseBody;
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}

	public static IEEEAuthenticationToken retrieveAuthToken()
	{
		if (!StringUtils.specified(API_KEY) || !StringUtils.specified(AUTH_TOKEN))
		{
			throw new ApplicationException("Authorization key or token not provided");
		}
		IEEEAuthenticationToken token = null;
		if (!FileManager.doesFileExist(API_KEY) || FileManager.isFileExpired(API_KEY))
		{
			token = authToken();
			FileManager.saveTokenFile(API_KEY, token.getToken());
		}
		else
		{
			token = new IEEEAuthenticationToken();
			token.setToken(FileManager.getTokenFromFile(API_KEY));
		}
		return token;
	}


	public static String findByAbstractText(String abstractText)
	{
		ApiRequest request = new ApiRequest();
		request.setAbstractText(abstractText);
		return callApi(request);
	}


	public static String findByAffiliationText(String affiliationText)
	{
		ApiRequest request = new ApiRequest();
		request.setAffiliation(affiliationText);
		return callApi(request);
	}

	public static String findByArticleTitle(String articleTitle)
	{
		ApiRequest request = new ApiRequest();
		request.setArticleTitle(articleTitle);
		return callApi(request);
	}

	public static String findByAuthorFacetText(String authorFacetText)
	{
		ApiRequest request = new ApiRequest();
		request.setAuthorFacetText(authorFacetText);
		return callApi(request);
	}

	public static String findByAuthorText(String authorText)
	{
		ApiRequest request = new ApiRequest();
		request.setAuthorText(authorText);
		return callApi(request);
	}

	public static String findByBooleanText(String booleanQuery)
	{
		ApiRequest request = new ApiRequest();
		request.setBooleanText(booleanQuery);
		return callApi(request);
	}


	public static String findByContentTypeFacetText(String contentTypeFacetText)
	{
		ApiRequest request = new ApiRequest();
		request.setContentTypeFacetText(contentTypeFacetText);
		return callApi(request);
	}


	public static String findByDigitalObjectIdentifier(String doi)
	{
		ApiRequest request = new ApiRequest();
		request.setDigitalObjectIdentifier(doi);
		return callApi(request);
	}


	public static String findByFacetText(String facetText)
	{
		ApiRequest request = new ApiRequest();
		request.setFacetText(facetText);
		return callApi(request);
	}

	public static String findByIndexTerms(String indexTermsText)
	{
		ApiRequest request = new ApiRequest();
		request.setIndexTerms(indexTermsText);
		return callApi(request);
	}

	public static String findByIsbn(String isbn)
	{
		ApiRequest request = new ApiRequest();
		request.setIsbn(isbn);
		return callApi(request);
	}

	public static String findByIssn(String issn)
	{
		ApiRequest request = new ApiRequest();
		request.setIssn(issn);
		return callApi(request);
	}

	public static String findByIssueNumber(String issueNumber)
	{
		ApiRequest request = new ApiRequest();
		request.setIssueNumber(issueNumber);
		return callApi(request);
	}

	public static String findByMetaDataText(String metaDataText)
	{
		ApiRequest request = new ApiRequest();
		request.setMetaDataText(metaDataText);
		return callApi(request);
	}

	public static String findByPublicationFacetText(String publicationFacetText)
	{
		ApiRequest request = new ApiRequest();
		request.setPublicationFacetText(publicationFacetText);
		return callApi(request);
	}

	public static String findByPublisherFacetText(String publisherFacetText)
	{
		ApiRequest request = new ApiRequest();
		request.setPublisherFacetText(publisherFacetText);
		return callApi(request);
	}

	public static String findByPublicationTitle(String publicationTitle)
	{
		ApiRequest request = new ApiRequest();
		request.setPublicationTitle(publicationTitle);
		return callApi(request);
	}

	public static String findByPublicationYear(String publicationYear)
	{
		ApiRequest request = new ApiRequest();
		request.setPublicationYear(publicationYear);
		return callApi(request);
	}

	public static String findByThesaurusTerms(String thesaurusTerms)
	{
		ApiRequest request = new ApiRequest();
		request.setThesaurusTerms(thesaurusTerms);
		return callApi(request);
	}

	public static String findByPublicationNumber(String publicationNumber)
	{
		ApiRequest request = new ApiRequest();
		request.setPublicationNumber(publicationNumber);
		return callApi(request);
	}

	public static String findByWildcard(String wildcard)
	{
		ApiRequest request = new ApiRequest();
		if (request.specified(wildcard))
		{
			String[] words = wildcard.split(" ");
			if (words.length > 2)
			{
				throw new ApplicationException("No more than two words are allowed for a wildcard search");
			}
			else
			{
				for (String word : words)
				{
					if (word.indexOf('*') < 3)
					{
						throw new ApplicationException(
							"Each wildcard should have a minimum of 3 characters preceeding a wilcard");
					}
				}
			}
			request.setQueryText(wildcard);
			return callApi(request);
		}
		return null;
	}

	public static String findByArticleNumber(String articleNumber)
	{
		ApiRequest request = new ApiRequest();
		request.setArticleNumber(articleNumber);
		return callApi(request);
	}

	/**
	 * Returns citations of a given type (ieee, non-ieee, patent) for the indicated article number.
	 * @param articleNumber - article number, example 6762843
	 * @param citationType - allowed values are: ieee, non-ieee, patent
	 * @return
	 */
	public static String citations(String articleNumber, String citationType)
	{
		if(!StringUtils.specified(OPEN_ACCESS_URL))
		{
			throw new ApplicationException("Open access URL is missing in application.properties");
		}
		ApiRequest request = new ApiRequest();
		request.setCitationLookup(true);
		request.setArticleNumber(articleNumber);
		request.setCitationType(citationType);
		return callApi(request);
	}

	/**
	 * SAML Usage
	 * @param startDate - Lesser than End Date, MM-DD-YYYY format
	 * @param endDate - Greater than Start Date, MM-DD-YYYY format
	 * @return
	 */
	public static String usageRequests(String startDate, String endDate)
	{
		if(!StringUtils.specified(CUSTOMER_ID))
		{
			throw new ApplicationException("Customer ID is missing in application.properties");
		}
		if(!StringUtils.specified(startDate) || !StringUtils.specified(endDate)){
			throw new ApplicationException("Start and End Dates are required and in MM-DD-YYYY format");
		}
		ApiRequest request = new ApiRequest();
		request.setRequestingUsage(true);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		return callApi(request);
	}


}
