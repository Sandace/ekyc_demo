package com.example.ekycdemo.models.HomeResponseModel.QuickSend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banks implements Serializable {
    public String image;
    public String name;
    @SerializedName("account_number")
    public String accountNumber;
    public String type;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
