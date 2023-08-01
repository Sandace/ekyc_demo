package com.example.ekycdemo.mero;

import java.util.List;

public class ApiResponse {
    private boolean status;
    private List<ApiData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ApiData> getData() {
        return data;
    }

    public void setData(List<ApiData> data) {
        this.data = data;
    }
}
