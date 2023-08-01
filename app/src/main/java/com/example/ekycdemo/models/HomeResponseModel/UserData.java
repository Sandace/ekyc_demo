package com.example.ekycdemo.models.HomeResponseModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserData implements Serializable {
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("avator")
    public String avatar;
    public int notification;
    @SerializedName("is_kyc_verified")
    public int isEKYCVerified;
    public String greeting;
    @SerializedName("place_holder")
    public String placeHolder;
    @SerializedName("residence_type")
    public String residenceType;
    @SerializedName("status_of_residence")
    public String statusOfResidence;
    @SerializedName("profile_type")
    public int profileType;
    @SerializedName("is_mpin_set")
    public boolean isMpinSet;
    @SerializedName("mpin_expired")
    public boolean mPinExpired;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public int getIsEKYCVerified() {
        return isEKYCVerified;
    }

    public void setIsEKYCVerified(int isEKYCVerified) {
        this.isEKYCVerified = isEKYCVerified;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public String getStatusOfResidence() {
        return statusOfResidence;
    }

    public void setStatusOfResidence(String statusOfResidence) {
        this.statusOfResidence = statusOfResidence;
    }

    public int getProfileType() {
        return profileType;
    }

    public void setProfileType(int profileType) {
        this.profileType = profileType;
    }

    public boolean isMpinSet() {
        return isMpinSet;
    }

    public void setMpinSet(boolean mpinSet) {
        isMpinSet = mpinSet;
    }

    public boolean ismPinExpired() {
        return mPinExpired;
    }

    public void setmPinExpired(boolean mPinExpired) {
        this.mPinExpired = mPinExpired;
    }
}
