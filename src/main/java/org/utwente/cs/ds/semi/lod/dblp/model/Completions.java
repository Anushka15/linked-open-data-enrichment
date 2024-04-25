package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Completions {

    @SerializedName("@total")
    private String total;
    @SerializedName("@computed")
    private String computed;
    @SerializedName("@sent")
    private String sent;
    private List<Completion> c;

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

    public List<Completion> getC() {
        return c;
    }

    public void setC(List<Completion> c) {
        this.c = c;
    }
}
