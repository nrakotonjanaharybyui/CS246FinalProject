package com.example.androidfinalproject;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private String id;
    private String category;
    private String imageDataEncoded;

    //No param constructor
    public Product() {
    }

    //Param constructor
    public Product(String name, String description, String id, String category, String imageDataEncoded) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.category = category;
        this.imageDataEncoded = imageDataEncoded;
    }

    //Setters
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

    public void setImageDataEncoded(String imageDataEncoded) {
        this.imageDataEncoded = imageDataEncoded;
    }

    //Getters
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

    public String getImageDataEncoded() {
        return imageDataEncoded;
    }
}
