package com.example.ekycdemo.mero;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ApiData implements Parcelable {
    private String type;
    private Object data;

    protected ApiData(Parcel in) {
        type = in.readString();
    }

    public static final Creator<ApiData> CREATOR = new Creator<ApiData>() {
        @Override
        public ApiData createFromParcel(Parcel in) {
            return new ApiData(in);
        }

        @Override
        public ApiData[] newArray(int size) {
            return new ApiData[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(type);
    }
}
