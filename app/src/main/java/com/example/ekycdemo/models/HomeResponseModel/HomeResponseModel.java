package com.example.ekycdemo.models.HomeResponseModel;

import com.example.ekycdemo.models.HomeResponseModel.QuickSend.QuickSend;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeResponseModel implements Serializable {
    public UserData userDetails;
    public ArrayList<Services> services;
    public ArrayList<QuickSend> quickSends;
    public ArrayList<Object> transactions;

    public HomeResponseModel(UserData userDetails, ArrayList<Services> services, ArrayList<QuickSend> quickSends, ArrayList<Object> transactions) {
        this.userDetails = userDetails;
        this.services = services;
        this.quickSends = quickSends;
        this.transactions = transactions;
    }

    public UserData getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserData userDetails) {
        this.userDetails = userDetails;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public ArrayList<QuickSend> getQuickSends() {
        return quickSends;
    }

    public void setQuickSends(ArrayList<QuickSend> quickSends) {
        this.quickSends = quickSends;
    }

    public ArrayList<Object> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Object> transactions) {
        this.transactions = transactions;
    }
}
