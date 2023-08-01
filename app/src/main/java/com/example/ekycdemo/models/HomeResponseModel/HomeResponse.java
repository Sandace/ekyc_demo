package com.example.ekycdemo.models.HomeResponseModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeResponse implements Serializable {
    public boolean status;
    @SerializedName("data")
    public ArrayList<Object> homeDataList;
}
