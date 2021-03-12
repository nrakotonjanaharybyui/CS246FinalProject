package com.example.androidfinalproject;

public class SearchResultItem {
    private String name;
    private String description;
    private String id;
    private String category;

    public SearchResultItem() {
    }

    public SearchResultItem(String name, String description, String id, String category) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
