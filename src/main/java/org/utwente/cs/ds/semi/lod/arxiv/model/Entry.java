package org.utwente.cs.ds.semi.lod.arxiv.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {
    @XmlElement(name = "id", namespace = "http://www.w3.org/2005/Atom")
    private String id;

    @XmlElement(name = "updated", namespace = "http://www.w3.org/2005/Atom")
    private String updated;

    @XmlElement(name = "published", namespace = "http://www.w3.org/2005/Atom")
    private String published;

    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    private String title;

    @XmlElement(name = "summary", namespace = "http://www.w3.org/2005/Atom")
    private String summary;

    @XmlElement(name = "author", namespace = "http://www.w3.org/2005/Atom")
    private List<Author> authors;

    @XmlElement(name = "comment", namespace = "http://arxiv.org/schemas/atom")
    private String comment;

    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<Link> links;

    @XmlElement(name = "primary_category", namespace = "http://arxiv.org/schemas/atom")
    private PrimaryCategory primaryCategory;

    @XmlElement(name = "category", namespace = "http://www.w3.org/2005/Atom")
    private List<Category> categories;

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

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public PrimaryCategory getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(PrimaryCategory primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}