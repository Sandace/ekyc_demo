package com.example.ekycdemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IntendOfDataItem implements Serializable {
    @SerializedName("name_en")
    public String nameEn;
    @SerializedName("name_jp")
    public String nameJp;
}
