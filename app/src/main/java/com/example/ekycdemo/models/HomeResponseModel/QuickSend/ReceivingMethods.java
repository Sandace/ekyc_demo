package com.example.ekycdemo.models.HomeResponseModel.QuickSend;

import java.io.Serializable;
import java.util.ArrayList;

public class ReceivingMethods implements Serializable {
    ArrayList<Banks> banks;
    ArrayList<Wallets> wallets;

    public ArrayList<Banks> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<Banks> banks) {
        this.banks = banks;
    }

    public ArrayList<Wallets> getWallets() {
        return wallets;
    }

    public void setWallets(ArrayList<Wallets> wallets) {
        this.wallets = wallets;
    }
}
