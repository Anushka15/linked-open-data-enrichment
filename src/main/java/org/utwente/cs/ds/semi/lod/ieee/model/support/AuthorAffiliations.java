package org.utwente.cs.ds.semi.lod.ieee.model.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorAffiliations {

    @JsonProperty("authorAffiliation")
    private List<String> authorAffiliation;

    public List<String> getAuthorAffiliation() {
        return authorAffiliation;
    }

    public void setAuthorAffiliation(List<String> authorAffiliation) {
        this.authorAffiliation = authorAffiliation;
    }
}
