package com.example.ekycdemo.models.HomeResponseModel.QuickSend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuickSend implements Serializable {
    public String name;
    @SerializedName("name_jp")
    public String nameJp;
    public String image;
    public String slug;
    @SerializedName("place_holder")
    public String placeHolder;
    @SerializedName("nationality")
    public String nationalityFlag;
    @SerializedName("receiving_methods")
    public ReceivingMethods receivingMethods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameJp() {
        return nameJp;
    }

    public void setNameJp(String nameJp) {
        this.nameJp = nameJp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String getNationalityFlag() {
        return nationalityFlag;
    }

    public void setNationalityFlag(String nationalityFlag) {
        this.nationalityFlag = nationalityFlag;
    }

    public ReceivingMethods getReceivingMethods() {
        return receivingMethods;
    }

    public void setReceivingMethods(ReceivingMethods receivingMethods) {
        this.receivingMethods = receivingMethods;
    }
}
