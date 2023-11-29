package com.projeto.bibliotroca.models;

public class BuyerDTO {
    private String name;
    private String surname;
    private String email;
    private double averageRating;
    private int avaliationsNumber;
    private String phoneNumber;

    public String getFirstName() {
        return name;
    }

    public void setFirstName(String firstName) {
        this.name = firstName;
    }

    public String getLastName() {
        return surname;
    }

    public void setLastName(String lastName) {
        this.surname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

