package tfip.ssf.library.Models;

import java.io.Serializable;

import jakarta.json.JsonObject;

public class Book implements Serializable{
    private String workId;
    private String title;
    private String description;
    private Boolean cached = true;

    public Book(String workId) {
        this.workId = workId;
    }

    public Book(String workId, String title) {
        this.workId = workId;
        this.title = title;
    }

    public void addJson(JsonObject json) {
        if (json.containsKey("title") && !json.isNull("title")){
            this.setTitle(json.getString("title"));
        } else {
            this.setTitle("No title available");
        }
        if (json.containsKey("description") && !json.isNull("description")){
            this.setDescription(json.getString("description"));
        } else {
            this.setDescription("No description available");
        }
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCached() {
        return cached;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }   
 
}
