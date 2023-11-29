package com.projeto.bibliotroca.models;

public class BookCompleteDTO {
    private String id;
    private String name;
    private String author;
    private String category;
    private String language;
    private String year;
    private String publishingCompany;
    private String state;
    private String description;

    private String shortDescription;

    private String createdAt;
    private SellerDTO user;


    private String title;
    private String field;
    private String edition;
    private String userCpf;
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getField() {return field;}
    public void setField(String field) {this.field = field;}
    public String getEdition() {return edition;}
    public void setEdition(String edition) {this.edition = edition;}
    public String getUserCpf() {return userCpf;}
    public void setUserCpf(String userCpf) {this.userCpf = userCpf;}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublishingCompany() {
        return publishingCompany;
    }

    public void setPublishingCompany(String publishingCompany) {
        this.publishingCompany = publishingCompany;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt(String format) {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public SellerDTO getSeller() {
        return user;
    }

    public void setSeller(SellerDTO seller) {
        this.user = seller;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}
