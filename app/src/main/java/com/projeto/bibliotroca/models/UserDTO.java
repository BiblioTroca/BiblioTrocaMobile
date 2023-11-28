package com.projeto.bibliotroca.models;

public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String location;
    private Number averageRating;
    private Number avaliationsNumber;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Number getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(Number averageRating) {
        this.averageRating = averageRating;
    }

    public Number getAvaliationsNumber() {
        return avaliationsNumber;
    }
    public void setAvaliationsNumber(Number avaliationsNumber) {
        this.avaliationsNumber = avaliationsNumber;
    }
}

