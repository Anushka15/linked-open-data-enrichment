package org.utwente.cs.ds.semi.lod.arxiv.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrimaryCategory {
    @XmlAttribute(name = "term", namespace = "http://www.w3.org/2005/Atom")
    private String term;

    @XmlAttribute(name = "scheme", namespace = "http://www.w3.org/2005/Atom")
    private String scheme;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}