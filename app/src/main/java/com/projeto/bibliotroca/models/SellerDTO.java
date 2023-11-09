package com.projeto.bibliotroca.models;

public class SellerDTO {
    private String name;
    private String location;
    private double averageRating;
    private int avaliationsNumber;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getAvaliationsNumber() {
        return avaliationsNumber;
    }

    public void setAvaliationsNumber(int avaliationsNumber) {
        this.avaliationsNumber = avaliationsNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

