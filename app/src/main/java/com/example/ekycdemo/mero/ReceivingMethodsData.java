package com.example.ekycdemo.mero;

import java.util.List;

public class ReceivingMethodsData {
    private List<BankData> banks;
    private List<WalletData> wallet;

    // Getter and setter methods


    public List<BankData> getBanks() {
        return banks;
    }

    public void setBanks(List<BankData> banks) {
        this.banks = banks;
    }

    public List<WalletData> getWallet() {
        return wallet;
    }

    public void setWallet(List<WalletData> wallet) {
        this.wallet = wallet;
    }
}
