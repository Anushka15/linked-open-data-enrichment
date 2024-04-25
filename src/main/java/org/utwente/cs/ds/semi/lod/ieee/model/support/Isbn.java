package org.utwente.cs.ds.semi.lod.ieee.model.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Isbn {

    @JsonProperty("format")
    private String format;

    @JsonProperty("value")
    private String value;
    @JsonProperty("isbnType")
    private String isbnType;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsbnType() {
        return isbnType;
    }

    public void setIsbnType(String isbnType) {
        this.isbnType = isbnType;
    }
}
