package com.example.ekycdemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CountryList implements Serializable {
    public boolean status;
    @SerializedName("data")
    public List<Country> countries;
}
