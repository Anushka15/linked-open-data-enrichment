package org.utwente.cs.ds.semi.lod.ieee.scraping.request;

import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This request object has to be passed in as an argument to the API client service. When implementing the service
 * methods to query the api based on individual fields, this request object is constructed to ensure the defaults 
 * for fields like max records, output etc are populated.
 * 
 * @author rtadi
 *
 */
public class ApiRequest implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Queries text in abstracts.
	 * "Mapped Field: abstract"
	 * */
	private String abstractText;
	
	/**
	 * Queries text in affiliation data field.
	 * "Mapped Field: affiliation"
	 * */
	private String affiliation;
	
	/**
	 * Creates a query using IEEE’s unique article identifier.
	 * Note: When used all other query methods are ignored.
	 * "Mapped Field: article_number"
	 * */
	private String articleNumber;
	
	/**
	 * Queries based on the title of an individual document.
	 * "Mapped Field: article_title"
	 * */
	private String articleTitle;

	private String authorNumber;

	/** Queries text in Open Author Facet data field.  */
	private String authorFacetText;
	
	/** Queries text in author data field. */
	private String authorText;
	
	/** Creates a boolean query against all configured metadata fields, abstract, and document text.  Do not include parentheses. */
	private String booleanText;
	
	/** Queries text in Open Content Type Facet data field. */
	private String contentTypeFacetText;
	
	/**
	 * Creates a query using IEEE’s unique digital object identifier.
	 * Note: when used all other query methods are ignored, except for articleNumber,
	 * which overrides the digital object identifier.
	 */
	private String digitalObjectIdentifier;
	
	/** Queries text in Open/Facet data field. */
	private String facetText;
	
	/** 
	 * Queries text in Author Keywords, IEEE Terms, and Mesh Terms. 
	 * Note: include no more than two wildcard words. Each wildcard word must have a minimum of three characters preceding the wild card (*).
	 */
	private String indexTerms;
	
	/** Queries based on International Standard Book Number (ISBN) */
	private String isbn;
	
	/** Queries based on International Standard Serial Number (ISSN) */
	private String issn;
	
	/** Queries based on Journal Issue Number. */
	private String issueNumber;
	
	/** 
	 * Queries text in configured metadata fields and abstracts. 
	 * Note: include no more than two wildcard words. Each wildcard word must have a minimum of three characters preceding the wildcard (*).
 	 */
	private String metaDataText;

	/** Queries text in Open Publication Facet data field. */
	private String publicationFacetText;
	
	/** Queries text in Open Publisher’s Facet data field. */
	private String publisherFacetText;
	
	/** Queries text in the title of a publication (Journal, Conference, or Standard). */
	private String publicationTitle;
	
	/** Queries against the publication year data field. Note: the format of this data varies by publication. */
	private String publicationYear;
	
	/** 
	 * Queries against all configured metadata fields, abstract, and document text. 
	 * Note: include no more than two wildcard words. Each wildcard word must have a minimum of three characters preceding the wildcard (*).
	 */
	private String queryText;
	
	/** 
	 * Queries keywords assigned to IEEE journal articles and conference papers from a controlled vocabulary created by the IEEE. 
	 * Note: include no more than two wildcard words. Each wildcard word must have a minimum of three characters preceding the wildcard (*).
	 */
	private String thesaurusTerms;


	/**
	 * Queries based on insert date and includes results inserted on this date or later.
	 * Must be paired with an insertionEndDate
	 * YYYYMMDD format
	 */
	private String startDate;

	/**
	 * Queries based on insert date and includes results inserted on this date or before.
	 * Must be paired with an insertionStartDate
	 * YYYYMMDD format.
	 */
	private String endDate;
	
	/** Acceptable values are asc or desc, default is asc */
	private String sortOrder;
	
	/** Acceptable values are json or xml */
	private String dataFormat;
	
	/** Defaulted to 25 */
	private int maxRecords = 200;
	
	/**
	 * Sort By Field, Valid values are,
	 * author, article_number, article_title, publication_title, publication_year, start_date,
	 * end_date
	 * @see Defaults
	 **/
	private String sortByField;
	
	/** @See Defaults for acceptable values */
	private Map<String, String> resultsFilter;

	/** Queries against the publication number data field.  */
	private String publicationNumber;

	private int startRecord = 1;
	
	private boolean usingBoolean = false;
	
	private boolean usingFacet = false;
	
	private boolean facetApplied = false;
	
	private String mediaType;

	private String outputType;

	private boolean usingOpenAccess = false;
	private boolean requestingBio = false;
	private boolean requestingUsage = false;
	private boolean citationLookup = false;
	private boolean requestingFullText = false;
	private String citationType;


	public ApiRequest(){
		if(resultsFilter == null){
			resultsFilter = new HashMap<String, String>();
		}
	}
	
	public boolean specified(String arg){
		return (arg != null && !arg.trim().isEmpty()) ? true : false;
	}
	
	private void updateUsingFacetStatus(){
		if(specified(getAuthorFacetText()) || specified(getContentTypeFacetText()) 
				|| specified(getFacetText()) || specified(getPublicationFacetText()) || specified(getPublisherFacetText())){
			usingFacet = true;
		}
	}
	
	/**
	 * 
	 */
	public void validateResultsFilters(){
		List<String> validFilterFields = Arrays.asList(Defaults.ALLOWED_RESULTS_FILTER);
		for(String filterField: resultsFilter.keySet()){
			String lowerFilterField = filterField.trim().toLowerCase();
			if(!validFilterFields.contains(lowerFilterField)){
				throw new ApplicationException("Filter field " + lowerFilterField + " is not valid. Valid filter fields are: " + Arrays.toString(Defaults.ALLOWED_RESULTS_FILTER));
			}else{
				//Standards do not have article titles, so switch to sorting by article number
				if("content_type".equals(lowerFilterField)){
					if("Standards".equals(resultsFilter.get(filterField))){
						this.sortByField = "publication_year";
						this.sortOrder = Defaults.DEFAULT_SORT_ORDER;
					}
				}
			}
		}		
	}
	
	/**
	 * 
	 */
	public void validateResultsSorting(){
		if(sortByField != null && !sortByField.trim().isEmpty()){
			sortByField = sortByField.trim().toLowerCase();
			List<String> validSortingFields = Arrays.asList(Defaults.ALLOWED_SORT_FIELDS);
			if(!validSortingFields.contains(sortByField)){
				throw new ApplicationException("Sorting field " + sortByField + " is not valid. Valid sorting fields are: " + Arrays.toString(Defaults.ALLOWED_SORT_FIELDS));
			}
		}else{
			this.sortByField = Defaults.DEFAULT_SORT_BY_FIELD;
			this.sortOrder = Defaults.DEFAULT_SORT_ORDER;			
		}
	}	
	
	/**
	 * 
	 * @param queryParamMap
	 * @return
	 */
	public Map<String, String> appendQueryParams(Map<String, String> queryParamMap){
		// If Article Number is provided, all other search parameters are ignored.
		if(specified(getArticleNumber())){
			queryParamMap.put("article_number", this.getArticleNumber().trim());
			return queryParamMap;
		}
		updateUsingFacetStatus();
		if(specified(getBooleanText())){
			usingBoolean = true;
			//queryParamMap.put("boolean_text", this.getBooleanText().trim());
		}	
		if(!usingBoolean){
			// If DOI is provided, all other search parameters are ignored except Article Number (takes primary precedence).
			if(specified(getDigitalObjectIdentifier())){
				queryParamMap.put("doi", this.getDigitalObjectIdentifier().trim());
				return queryParamMap;
			}
			if(specified(getAbstractText())){
				queryParamMap.put("abstract", this.getAbstractText().trim());
			}
			if(specified(getAffiliation())){
				queryParamMap.put("affiliation", this.getAffiliation().trim());
			}
			if(specified(getArticleTitle())){
				queryParamMap.put("article_title", this.getArticleTitle().trim());
			}	
			if(specified(getAuthorText())){
				queryParamMap.put("author", this.getAuthorText().trim());
			}
			if(specified(getIndexTerms())){
				queryParamMap.put("index_terms", this.getIndexTerms().trim());
			}
			if(specified(getIsbn())){
				queryParamMap.put("isbn", this.getIsbn().trim());
			}
			if(specified(getIssn())){
				queryParamMap.put("issn", this.getIssn().trim());
			}
			if(specified(getIssueNumber())){
				queryParamMap.put("is_number", this.getIssueNumber().trim());
			}
			if(specified(getMetaDataText())){
				queryParamMap.put("meta_data", this.getMetaDataText().trim());
			}	
			if(specified(getPublicationTitle())){
				queryParamMap.put("publication_title", this.getPublicationTitle().trim());
			}
			if(specified(getPublicationYear())){
				queryParamMap.put("publication_year", this.getPublicationYear().trim());
			}
			if(specified(getQueryText() )){
				queryParamMap.put("querytext", this.getQueryText().trim());
			}
			if(specified(getThesaurusTerms())){
				queryParamMap.put("thesaurus_terms", this.getThesaurusTerms().trim());
			}
			if(specified(getStartDate())){
				queryParamMap.put("start_date", this.getStartDate().trim());
			}
			if(specified(getThesaurusTerms())){
				queryParamMap.put("end_date", this.getEndDate().trim());
			}
			if(specified(getPublicationNumber())){
				queryParamMap.put("publication_number", this.getPublicationNumber().trim());
				return queryParamMap;
			}
		}
		return queryParamMap;
	}
	
	public Map<String, String> buildDataFormatParameters(){
		Map<String, String> formatTypeMap = new HashMap<>();
		String data = Defaults.DEFAULT_DATA_FORMAT;
		if(specified(dataFormat)){
			dataFormat = dataFormat.trim().toLowerCase(); 
			switch(dataFormat){
				case "xml":
					data = dataFormat;
					mediaType = "application/xml";
					break;
				default:
					data = Defaults.DEFAULT_DATA_FORMAT;
					mediaType = "application/json";					
			}
		}
		if(this.mediaType == null){
			mediaType = Defaults.DEFAULT_MEDIA_TYPE;
		}
		formatTypeMap.put("format", data);
		return formatTypeMap;
	}

	// -- Getters and Setters. These can removed if a dependency like lombok is added to the source. 
	
	public String getAbstractText() {
		return abstractText;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public String getArticleNumber() {
		return articleNumber;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public String getAuthorFacetText() {
		return authorFacetText;
	}

	public String getAuthorText() {
		return authorText;
	}

	public String getBooleanText() {
		return booleanText;
	}

	public String getContentTypeFacetText() {
		return contentTypeFacetText;
	}

	public String getDigitalObjectIdentifier() {
		return digitalObjectIdentifier;
	}

	public String getFacetText() {
		return facetText;
	}

	public String getIndexTerms() {
		return indexTerms;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getIssn() {
		return issn;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public String getMetaDataText() {
		return metaDataText;
	}

	public String getPublicationFacetText() {
		return publicationFacetText;
	}

	public String getPublisherFacetText() {
		return publisherFacetText;
	}

	public String getPublicationTitle() {
		return publicationTitle;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public String getQueryText() {
		return queryText;
	}

	public String getThesaurusTerms() {
		return thesaurusTerms;
	}

	public Map<String, String> getResultsFilter() {
		return resultsFilter;
	}

	public String getSortByField() {
		return sortByField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getDataFormat() {
		return dataFormat != null ? dataFormat : "json";
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public void setAuthorFacetText(String authorFacetText) {
		this.authorFacetText = authorFacetText;
	}

	public void setAuthorText(String authorText) {
		this.authorText = authorText;
	}

	public void setBooleanText(String booleanText) {
		this.booleanText = booleanText;
	}

	public void setContentTypeFacetText(String contentTypeFacetText) {
		this.contentTypeFacetText = contentTypeFacetText;
	}

	public void setDigitalObjectIdentifier(String digitalObjectIdentifier) {
		this.digitalObjectIdentifier = digitalObjectIdentifier;
	}

	public void setFacetText(String facetText) {
		this.facetText = facetText;
	}

	public void setIndexTerms(String indexTerms) {
		this.indexTerms = indexTerms;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public void setMetaDataText(String metaDataText) {
		this.metaDataText = metaDataText;
	}

	public void setPublicationFacetText(String publicationFacetText) {
		this.publicationFacetText = publicationFacetText;
	}

	public void setPublisherFacetText(String publisherFacetText) {
		this.publisherFacetText = publisherFacetText;
	}

	public void setPublicationTitle(String publicationTitle) {
		this.publicationTitle = publicationTitle;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public void setThesaurusTerms(String thesaurusTerms) {
		this.thesaurusTerms = thesaurusTerms;
	}

	public void setResultsFilter(Map<String, String> resultsFilter) {
		this.resultsFilter = resultsFilter;
	}

	public void setSortByField(String sortByField) {
		this.sortByField = sortByField;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public boolean isUsingBoolean() {
		return usingBoolean;
	}

	public boolean isUsingFacet() {
		return usingFacet;
	}

	public String getMediaType() {
		return mediaType;
	}

	public int getMaxRecords() {
		if(maxRecords != 25){
			maxRecords = maxRecords > 200 ? Defaults.MAX_RECORDS_ALLOWED : maxRecords;
		}
		return maxRecords != 25 ? maxRecords : Defaults.MAX_RECORDS;
	}

	public void setMaxRecords(int maxRecords) {
		this.maxRecords = maxRecords;
	}

	public int getStartRecord() {
		return this.startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public boolean isFacetApplied() {
		return facetApplied;
	}

	public void setFacetApplied(boolean facetApplied) {
		this.facetApplied = facetApplied;
	}

	public boolean isUsingOpenAccess() {
		return usingOpenAccess;
	}

	public void setUsingOpenAccess(boolean usingOpenAccess) {
		this.usingOpenAccess = usingOpenAccess;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public void setUsingBoolean(boolean usingBoolean)
	{
		this.usingBoolean = usingBoolean;
	}

	public void setUsingFacet(boolean usingFacet)
	{
		this.usingFacet = usingFacet;
	}

	public void setMediaType(String mediaType)
	{
		this.mediaType = mediaType;
	}

	public String getOutputType()
	{
		return outputType == null ? Defaults.DEFAULT_DATA_FORMAT : outputType;
	}

	public void setOutputType(String outputType)
	{
		this.outputType = outputType;
	}

	public boolean isRequestingBio()
	{
		return requestingBio;
	}

	public void setRequestingBio(boolean requestingBio)
	{
		this.requestingBio = requestingBio;
	}

	public boolean isRequestingUsage()
	{
		return requestingUsage;
	}

	public void setRequestingUsage(boolean requestingUsage)
	{
		this.requestingUsage = requestingUsage;
	}

	public boolean isCitationLookup()
	{
		return citationLookup;
	}

	public void setCitationLookup(boolean citationLookup)
	{
		this.citationLookup = citationLookup;
	}

	public boolean isRequestingFullText()
	{
		return requestingFullText;
	}

	public void setRequestingFullText(boolean requestingFullText)
	{
		this.requestingFullText = requestingFullText;
	}

	public String getCitationType()
	{
		return citationType;
	}

	public void setCitationType(String cType)
	{
		if(cType != null){
			cType = cType.toLowerCase();
			cType.replace(" ", "");
			cType.replace("-", "_");
		}
		this.citationType = cType;
	}

	public String getAuthorNumber()
	{
		return authorNumber;
	}

	public void setAuthorNumber(String authorNumber)
	{
		this.authorNumber = authorNumber;
	}

	public String getPublicationNumber()
	{
		return publicationNumber;
	}

	public void setPublicationNumber(String publicationNumber)
	{
		this.publicationNumber = publicationNumber;
	}
}
