package com.yunus.udemycourcesbackend.dto;

import jakarta.persistence.Column;

public class CourseDto {
    private String name;
    private String ownerName;
    private Double price;
    private String imageUrl;
    private Double rate;


    public CourseDto(String name, String ownerName, Double price, String imageUrl, Double rate) {
        this.name = name;
        this.ownerName = ownerName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
