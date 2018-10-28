package com.example.abun.shoprite1;

import java.io.InputStream;

/**
 * Created by Abun on 6/14/2018.
 */

public class ProductPageRetrive {

    private int price;
    private int rating;
    private InputStream ism;
    private String description;
    private int Stock;
    private String name;
    private int prodid;


    public ProductPageRetrive(InputStream is,int productid, String name,int money,int stocks,String discript,int rate){
        this.ism=is;
        this.price=money;
        this.rating=rate;
        this.Stock=stocks;
        this.description=discript;
        this.name=name;
        this.setProdid(productid);

    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public InputStream getIsm() {
        return ism;
    }

    public void setIsm(InputStream ism) {
        this.ism = ism;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }
}
