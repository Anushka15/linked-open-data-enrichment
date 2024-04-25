package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.annotations.SerializedName;

public class Time {

    @SerializedName("@unit")
    private String unit;
    private String text;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
