package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("@pid")
    private String pid;

    @SerializedName("text")
    private String text;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
