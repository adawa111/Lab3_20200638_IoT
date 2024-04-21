package com.example.lab3_20200638;

import com.google.gson.annotations.SerializedName;

public class Numeros {


    @SerializedName("number")
    private int number;


    @SerializedName("order")
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
