package org.utwente.cs.ds.semi.lod.arxiv.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class Link {
    @XmlAttribute(name = "href", namespace = "http://www.w3.org/2005/Atom")
    private String href;

    @XmlAttribute(name = "rel", namespace = "http://www.w3.org/2005/Atom")
    private String rel;

    @XmlAttribute(name = "type", namespace = "http://www.w3.org/2005/Atom")
    private String type;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}