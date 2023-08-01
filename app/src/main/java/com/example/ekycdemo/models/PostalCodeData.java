package com.example.ekycdemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostalCodeData implements Serializable {
    @SerializedName("postal_code")
    public String postalCode;
    @SerializedName("prefecture")
    public String prefecture;
    @SerializedName("prefecture_jp")
    public String prefectureJp;
    @SerializedName("city")
    public String city;
    @SerializedName("city_jp")
    public String cityJp;
    @SerializedName("street")
    public String street;
    @SerializedName("street_jp")
    public String streetJp;

}
