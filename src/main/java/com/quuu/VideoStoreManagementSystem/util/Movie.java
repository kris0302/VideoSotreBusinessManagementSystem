package com.quuu.VideoStoreManagementSystem.util;

public class Movie {
    private String cat;
    private String title;
    private String actors;
    private String director;
    private String description;
    private double price;
    private int avail;

    public Movie(String t, String c, String d, String a, String des, double p, int av){
        cat = c;
        title = t;
        actors = a;
        director = d;
        description = des;
        price = p;
        avail = av;
    }

    public Movie(){}

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

}
