package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.annotations.SerializedName;

public class Hit {

    @SerializedName("@score")
    private String score;
    @SerializedName("@id")
    private String id;
    private Info info;
    @SerializedName("url")
    private String url;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
