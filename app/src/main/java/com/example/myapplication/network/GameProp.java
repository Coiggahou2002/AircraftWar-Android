package com.example.myapplication.network;

public class GameProp {
    private String id;
    private String name;
    private Integer worth;
    private Integer stock;

    public GameProp(String id, String name, Integer worth, Integer stock) {
        this.id = id;
        this.name = name;
        this.worth = worth;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorth() {
        return worth;
    }

    public void setWorth(Integer worth) {
        this.worth = worth;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
