package com.projeto.bibliotroca.models;

public class SellerDTO {
    private String name;
    private String surname;
    private String location;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private double averageRating;
    private int avaliationsNumber;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {return surname;}

    public void setSurname(String surname) {this.surname = surname;}

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

