package org.utwente.cs.ds.semi.lod.ieee.model.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorTerms {

    @JsonProperty("terms")
    List<String> authorTerms;

    public List<String> getAuthorTerms() {
        return authorTerms;
    }

    public void setAuthorTerms(List<String> authorTerms) {
        this.authorTerms = authorTerms;
    }
}
