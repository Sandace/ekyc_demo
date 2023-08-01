package com.example.ekycdemo.models;


import java.io.Serializable;

public class TermsAndConditionModel implements Serializable {
    public boolean status;
    public DataModel2 data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataModel2 getData() {
        return data;
    }

    public void setData(DataModel2 data) {
        this.data = data;
    }
}
