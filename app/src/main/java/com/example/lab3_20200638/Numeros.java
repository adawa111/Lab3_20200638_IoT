package com.example.lab3_20200638;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Numeros implements Serializable {


    private int number;



    private int order;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
