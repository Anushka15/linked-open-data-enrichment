package org.utwente.cs.ds.semi.lod.dblp.model;

public class Query {

    private String query;
    private Status status;
    private Time time;
    private Completions completions;

    private Hits hits;

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Completions getCompletions() {
        return completions;
    }

    public void setCompletions(Completions completions) {
        this.completions = completions;
    }
}
