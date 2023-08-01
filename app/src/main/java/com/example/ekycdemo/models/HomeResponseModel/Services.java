package com.example.ekycdemo.models.HomeResponseModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Services implements Serializable {
    public String name;
    @SerializedName("name_jp")
    public String nameJp;
    public String image;
    public String slug;

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
}
