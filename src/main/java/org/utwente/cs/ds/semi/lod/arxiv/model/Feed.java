package org.utwente.cs.ds.semi.lod.arxiv.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "feed", namespace = "http://www.w3.org/2005/Atom")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private Link link;

    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    private Title title;

    @XmlElement(name = "id", namespace = "http://www.w3.org/2005/Atom")
    private String id;

    @XmlElement(name = "updated", namespace = "http://www.w3.org/2005/Atom")
    private String updated;

    @XmlElement(name = "totalResults", namespace = "http://a9.com/-/spec/opensearch/1.1/")
    private int totalResults;

    @XmlElement(name = "startIndex", namespace = "http://a9.com/-/spec/opensearch/1.1/")
    private int startIndex;

    @XmlElement(name = "itemsPerPage", namespace = "http://a9.com/-/spec/opensearch/1.1/")
    private int itemsPerPage;

    @XmlElement(name = "entry", namespace = "http://www.w3.org/2005/Atom")
    private List<Entry> entries;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}