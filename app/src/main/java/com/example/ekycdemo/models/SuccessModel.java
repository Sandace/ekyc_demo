package com.example.ekycdemo.models;

import java.io.Serializable;

public class SuccessModel implements Serializable {

    public boolean status;
    public String data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
