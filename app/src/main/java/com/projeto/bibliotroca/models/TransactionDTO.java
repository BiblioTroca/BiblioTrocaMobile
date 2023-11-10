package com.projeto.bibliotroca.models;

public class TransactionDTO {
    private String id;
    private String status;
    private String type;
    private String createdAt;
    private String endedAt;
    private BookCompleteDTO bookDetails;
    private BuyerDTO buyer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public BookCompleteDTO getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookCompleteDTO bookDetails) {
        this.bookDetails = bookDetails;
    }

    public BuyerDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerDTO buyer) {
        this.buyer = buyer;
    }
}

