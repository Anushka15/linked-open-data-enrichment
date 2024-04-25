package org.utwente.cs.ds.semi.lod.ieee.model.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexTerms {

    @JsonProperty("terms")
    List<String> indexTerms;

    public List<String> getIndexTerms() {
        return indexTerms;
    }

    public void setIndexTerms(List<String> indexTerms) {
        this.indexTerms = indexTerms;
    }
}
