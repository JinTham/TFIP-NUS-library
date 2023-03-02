package tfip.ssf.library.Models;

import java.util.LinkedList;
import java.util.List;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class searchResult {
    
    private String searchTerm;
    private List<Book> bookList = new LinkedList<>();

    //Constructor
    public searchResult(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    //Creation method
    public void addJson(JsonObject json) {
        JsonArray jsonArray = json.getJsonArray("docs");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jo = jsonArray.getJsonObject(i);
            this.bookList.add(new Book(jo.getString("key").substring(7), jo.getString("title")));
        }
    }

    //Getters & Setters
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Book> getBookList() {
        return this.bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
    
}
