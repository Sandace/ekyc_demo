package com.example.ekycdemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {
    @SerializedName("country")
    public String countryNameEn;
    @SerializedName("country_jp")
    public String countryNameJp;
    public String flag;
    public String currency;
    @SerializedName("country_code")
    public String countryCode;
}
