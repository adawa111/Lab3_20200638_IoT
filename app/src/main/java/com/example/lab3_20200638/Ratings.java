package com.example.lab3_20200638;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ratings implements Serializable {


    private String Source;


    private String Value;

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
