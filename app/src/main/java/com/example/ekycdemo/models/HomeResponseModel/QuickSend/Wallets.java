package com.example.ekycdemo.models.HomeResponseModel.QuickSend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wallets implements Serializable {
    public String image;
    public String name;
    @SerializedName("wallet_id")
    public String walletId;
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

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
