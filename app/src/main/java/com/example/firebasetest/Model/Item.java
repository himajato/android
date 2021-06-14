package com.example.firebasetest.Model;

import java.io.Serializable;

public class Item implements Serializable {
    private String tilte;
    private String money;
    private String date;
    public Item(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Item(String tilte, String money, String date){
        this.tilte = tilte;
        this.money = money;
        this.date = date;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
