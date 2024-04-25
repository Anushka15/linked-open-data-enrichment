package org.utwente.cs.ds.semi.lod.ieee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.utwente.cs.ds.semi.lod.ieee.model.support.AuthorTerms;
import org.utwente.cs.ds.semi.lod.ieee.model.support.Authors;
import org.utwente.cs.ds.semi.lod.ieee.model.support.IndexTerms;
import org.utwente.cs.ds.semi.lod.ieee.model.support.Isbns;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IeeeData {

    @JsonProperty("title")
    private String title;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("authors")
    private Authors authors;
    @JsonProperty("access_type")
    private String accessType;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("abstract")
    private String abstractText;
    @JsonProperty("article_number")
    private String articleNumber;
    @JsonProperty("pdf_url")
    private String pdfUrl;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("abstract_url")
    private String abstractUrl;
    @JsonProperty("publication_title")
    private String publicationTitle;
    @JsonProperty("publication_number")
    private Integer publicationNumber;
    @JsonProperty("publication_year")
    private Integer publicationYear;
    @JsonProperty("start_page")
    private String startPage;
    @JsonProperty("end_page")
    private String endPage;
    @JsonProperty("citing_paper_count")
    private Integer citingPaperCount;
    @JsonProperty("citing_patent_count")
    private Integer citingPatentCount;
    @JsonProperty("download_count")
    private Integer downloadCount;
    @JsonProperty("insert_date")
    private String insertDate;
    @JsonProperty("index_terms")
    private IndexTerms indexTerms;

    @JsonProperty("author_terms")
    private AuthorTerms authorTerms;

    @JsonProperty("isbn_formats")
    private Isbns isbns;

    @JsonProperty("conference_location")
    private String conferenceLocation;

    @JsonProperty("conference_date")
    private String conferenceDate;

    @JsonProperty("doi")
    private String doi;

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAbstractUrl() {
        return abstractUrl;
    }

    public void setAbstractUrl(String abstractUrl) {
        this.abstractUrl = abstractUrl;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public Integer getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(Integer publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getEndPage() {
        return endPage;
    }

    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    public Integer getCitingPaperCount() {
        return citingPaperCount;
    }

    public void setCitingPaperCount(Integer citingPaperCount) {
        this.citingPaperCount = citingPaperCount;
    }

    public Integer getCitingPatentCount() {
        return citingPatentCount;
    }

    public void setCitingPatentCount(Integer citingPatentCount) {
        this.citingPatentCount = citingPatentCount;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public IndexTerms getIndexTerms() {
        return indexTerms;
    }

    public void setIndexTerms(IndexTerms indexTerms) {
        this.indexTerms = indexTerms;
    }

    public AuthorTerms getAuthorTerms() {
        return authorTerms;
    }

    public void setAuthorTerms(AuthorTerms authorTerms) {
        this.authorTerms = authorTerms;
    }

    public Isbns getIsbns() {
        return isbns;
    }

    public void setIsbns(Isbns isbns) {
        this.isbns = isbns;
    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public String getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(String conferenceDate) {
        this.conferenceDate = conferenceDate;
    }
}
