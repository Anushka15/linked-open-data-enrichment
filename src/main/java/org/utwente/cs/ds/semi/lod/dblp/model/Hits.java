package org.utwente.cs.ds.semi.lod.dblp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hits {


    @SerializedName("@total")
    private String total;
    @SerializedName("@computed")
    private String computed;
    @SerializedName("@sent")
    private String sent;
    @SerializedName("@first")
    private String first;
    private List<Hit> hit;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getComputed() {
        return computed;
    }

    public void setComputed(String computed) {
        this.computed = computed;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public List<Hit> getHit() {
        return hit;
    }

    public void setHit(List<Hit> hit) {
        this.hit = hit;
    }
}
