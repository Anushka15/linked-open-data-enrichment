package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.annotations.SerializedName;

public class Completion {

    @SerializedName("@sc")
    private String sc;
    @SerializedName("@dc")
    private String dc;
    @SerializedName("@oc")
    private String oc;
    @SerializedName("@id")
    private String id;
    private String text;

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
