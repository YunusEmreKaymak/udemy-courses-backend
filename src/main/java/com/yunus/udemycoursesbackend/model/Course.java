package com.yunus.udemycoursesbackend.model;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String name;
    @Column
    private String ownerName;
    @Column
    private Double price;
    @Column
    private String imageUrl;
    @Column
    private Double rate;
    @Column
    private String category;


    public Course(String id, String name, String ownerName, Double price, String imageUrl, Double rate, String category) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rate = rate;
        this.category = category;
    }

    public Course() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", rate=" + rate +
                '}';
    }
}
