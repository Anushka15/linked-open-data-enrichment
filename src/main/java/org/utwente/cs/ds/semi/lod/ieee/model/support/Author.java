package org.utwente.cs.ds.semi.lod.ieee.model.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {


    @JsonProperty("affiliation")
    private String affiliation;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("author_order")
    private int authorOrder;
    @JsonProperty("authorAffiliations")
    private AuthorAffiliations authorAffiliations;

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAuthorOrder() {
        return authorOrder;
    }

    public void setAuthorOrder(int authorOrder) {
        this.authorOrder = authorOrder;
    }

    public AuthorAffiliations getAuthorAffiliations() {
        return authorAffiliations;
    }

    public void setAuthorAffiliations(AuthorAffiliations authorAffiliations) {
        this.authorAffiliations = authorAffiliations;
    }
}
