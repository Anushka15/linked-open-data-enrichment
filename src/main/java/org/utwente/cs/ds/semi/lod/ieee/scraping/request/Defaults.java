package org.utwente.cs.ds.semi.lod.ieee.scraping.request;


public interface Defaults {

	public static final String DEFAULT_DATA_FORMAT = "json";
	
	public static final String DEFAULT_MEDIA_TYPE = "application/json";
	
	public static final String DEFAULT_SORT_ORDER = "asc";
	
	public static final String DEFAULT_SORT_BY_FIELD = "article_title";
	
	public static final int MAX_RECORDS = 25;
	
	public static final int MAX_RECORDS_ALLOWED = 200;
	
	public static String[] ALLOWED_SORT_FIELDS =
		new String[]{"author", "article_number", "article_title", "publication_title",
			"publication_year", "start_date", "end_date"};

	public static String[] ALLOWED_RESULTS_FILTER =
		new String[]{"content_type","end_year", "open_access", "publication_number", "publisher",
			"start_year", "end_date", "start_date"};
}
