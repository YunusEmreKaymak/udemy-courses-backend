package com.yunus.udemycoursesbackend.dto;

public class CourseDto {
    private String name;
    private String ownerName;
    private Double price;
    private String imageUrl;
    private Double rate;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CourseDto(String name, String ownerName, Double price, String imageUrl, Double rate, String category) {
        this.name = name;
        this.ownerName = ownerName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rate = rate;
        this.category = category;
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
