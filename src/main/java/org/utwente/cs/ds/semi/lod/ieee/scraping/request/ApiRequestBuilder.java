package org.utwente.cs.ds.semi.lod.ieee.scraping.request;

import java.util.Map;

public final class ApiRequestBuilder
{
	private String abstractText;
	private String affiliation;
	private String articleNumber;
	private String articleTitle;
	private String authorFacetText;
	private String authorText;
	private String booleanText;
	private String contentTypeFacetText;
	private String digitalObjectIdentifier;
	private String facetText;
	private String indexTerms;
	private String isbn;
	private String issn;
	private String issueNumber;
	private String metaDataText;
	private String publicationFacetText;
	private String publisherFacetText;
	private String publicationTitle;
	private String publicationYear;
	private String queryText;
	private String thesaurusTerms;
	private String sortOrder;
	private String dataFormat;
	private int maxRecords;
	private String sortByField;
	private Map<String, String> resultsFilter;
	private Integer startRecord;
	private boolean facetApplied;
	private boolean usingOpenAccess;

	private String publicationNumber;

	private ApiRequestBuilder()
	{
	}

	public static ApiRequestBuilder anApiRequest()
	{
		return new ApiRequestBuilder();
	}

	public ApiRequestBuilder withAbstractText(String abstractText)
	{
		this.abstractText = abstractText;
		return this;
	}

	public ApiRequestBuilder withAffiliation(String affiliation)
	{
		this.affiliation = affiliation;
		return this;
	}

	public ApiRequestBuilder withArticleNumber(String articleNumber)
	{
		this.articleNumber = articleNumber;
		return this;
	}

	public ApiRequestBuilder withArticleTitle(String articleTitle)
	{
		this.articleTitle = articleTitle;
		return this;
	}

	public ApiRequestBuilder withAuthorFacetText(String authorFacetText)
	{
		this.authorFacetText = authorFacetText;
		return this;
	}

	public ApiRequestBuilder withAuthorText(String authorText)
	{
		this.authorText = authorText;
		return this;
	}

	public ApiRequestBuilder withBooleanText(String booleanText)
	{
		this.booleanText = booleanText;
		return this;
	}

	public ApiRequestBuilder withContentTypeFacetText(String contentTypeFacetText)
	{
		this.contentTypeFacetText = contentTypeFacetText;
		return this;
	}

	public ApiRequestBuilder withDigitalObjectIdentifier(String digitalObjectIdentifier)
	{
		this.digitalObjectIdentifier = digitalObjectIdentifier;
		return this;
	}

	public ApiRequestBuilder withFacetText(String facetText)
	{
		this.facetText = facetText;
		return this;
	}

	public ApiRequestBuilder withIndexTerms(String indexTerms)
	{
		this.indexTerms = indexTerms;
		return this;
	}

	public ApiRequestBuilder withIsbn(String isbn)
	{
		this.isbn = isbn;
		return this;
	}

	public ApiRequestBuilder withIssn(String issn)
	{
		this.issn = issn;
		return this;
	}

	public ApiRequestBuilder withIssueNumber(String issueNumber)
	{
		this.issueNumber = issueNumber;
		return this;
	}

	public ApiRequestBuilder withMetaDataText(String metaDataText)
	{
		this.metaDataText = metaDataText;
		return this;
	}

	public ApiRequestBuilder withPublicationFacetText(String publicationFacetText)
	{
		this.publicationFacetText = publicationFacetText;
		return this;
	}

	public ApiRequestBuilder withPublisherFacetText(String publisherFacetText)
	{
		this.publisherFacetText = publisherFacetText;
		return this;
	}

	public ApiRequestBuilder withPublicationTitle(String publicationTitle)
	{
		this.publicationTitle = publicationTitle;
		return this;
	}

	public ApiRequestBuilder withPublicationYear(String publicationYear)
	{
		this.publicationYear = publicationYear;
		return this;
	}

	public ApiRequestBuilder withQueryText(String queryText)
	{
		this.queryText = queryText;
		return this;
	}

	public ApiRequestBuilder withThesaurusTerms(String thesaurusTerms)
	{
		this.thesaurusTerms = thesaurusTerms;
		return this;
	}

	public ApiRequestBuilder withSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
		return this;
	}

	public ApiRequestBuilder withDataFormat(String dataFormat)
	{
		this.dataFormat = dataFormat;
		return this;
	}

	public ApiRequestBuilder withMaxRecords(int maxRecords)
	{
		this.maxRecords = maxRecords;
		return this;
	}

	public ApiRequestBuilder withSortByField(String sortByField)
	{
		this.sortByField = sortByField;
		return this;
	}

	public ApiRequestBuilder withResultsFilter(Map<String, String> resultsFilter)
	{
		this.resultsFilter = resultsFilter;
		return this;
	}

	public ApiRequestBuilder withStartRecord(Integer startRecord)
	{
		this.startRecord = startRecord;
		return this;
	}

	public ApiRequestBuilder withFacetApplied(boolean facetApplied)
	{
		this.facetApplied = facetApplied;
		return this;
	}

	public ApiRequestBuilder withUsingOpenAccess(boolean usingOpenAccess)
	{
		this.usingOpenAccess = usingOpenAccess;
		return this;
	}

	public ApiRequestBuilder withPublicationNumber(String publicationNumber)
	{
		this.publicationNumber = publicationNumber;
		return this;
	}

	public ApiRequest build()
	{
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setAbstractText(abstractText);
		apiRequest.setAffiliation(affiliation);
		apiRequest.setArticleNumber(articleNumber);
		apiRequest.setArticleTitle(articleTitle);
		apiRequest.setAuthorFacetText(authorFacetText);
		apiRequest.setAuthorText(authorText);
		apiRequest.setBooleanText(booleanText);
		apiRequest.setContentTypeFacetText(contentTypeFacetText);
		apiRequest.setDigitalObjectIdentifier(digitalObjectIdentifier);
		apiRequest.setFacetText(facetText);
		apiRequest.setIndexTerms(indexTerms);
		apiRequest.setIsbn(isbn);
		apiRequest.setIssn(issn);
		apiRequest.setIssueNumber(issueNumber);
		apiRequest.setMetaDataText(metaDataText);
		apiRequest.setPublicationFacetText(publicationFacetText);
		apiRequest.setPublisherFacetText(publisherFacetText);
		apiRequest.setPublicationTitle(publicationTitle);
		apiRequest.setPublicationYear(publicationYear);
		apiRequest.setQueryText(queryText);
		apiRequest.setThesaurusTerms(thesaurusTerms);
		apiRequest.setSortOrder(sortOrder);
		apiRequest.setDataFormat(dataFormat);
		apiRequest.setMaxRecords(maxRecords);
		apiRequest.setSortByField(sortByField);
		apiRequest.setResultsFilter(resultsFilter);
		apiRequest.setStartRecord(startRecord);
		apiRequest.setFacetApplied(facetApplied);
		apiRequest.setUsingOpenAccess(usingOpenAccess);
		apiRequest.setPublicationNumber(publicationNumber);
		return apiRequest;
	}
}
