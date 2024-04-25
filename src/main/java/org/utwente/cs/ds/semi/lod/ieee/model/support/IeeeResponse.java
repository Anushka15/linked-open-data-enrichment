package org.utwente.cs.ds.semi.lod.ieee.model.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.utwente.cs.ds.semi.lod.ieee.model.IeeeData;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IeeeResponse {

    @JsonProperty("total_records")
    private Integer totalRecords;

    @JsonProperty("total_searched")
    private Integer totalSearched;

    @JsonProperty("articles")
    private List<IeeeData> ieeeDataObjects;

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalSearched() {
        return totalSearched;
    }

    public void setTotalSearched(Integer totalSearched) {
        this.totalSearched = totalSearched;
    }

    public List<IeeeData> getIeeeDataObjects() {
        return ieeeDataObjects;
    }

    public void setIeeeDataObjects(List<IeeeData> ieeeDataObjects) {
        this.ieeeDataObjects = ieeeDataObjects;
    }
}
