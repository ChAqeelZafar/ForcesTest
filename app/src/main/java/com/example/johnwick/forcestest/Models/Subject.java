package com.example.johnwick.forcestest.Models;

public class Subject {
    String name, imgUrl ;

    public Subject(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
