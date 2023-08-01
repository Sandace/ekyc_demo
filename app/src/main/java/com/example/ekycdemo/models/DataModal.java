package com.example.ekycdemo.models;

public class DataModal {
    public String profile_image;
    public String tilted_image;
    public String front_image;
    public String back_image;
    public String card_number;
    public String kyc_type;
    public String blink_image;

    public DataModal(String profile_image, String tilted_image, String front_image, String back_image, String card_number, String kyc_type, String blink_image) {
        this.profile_image = profile_image;
        this.tilted_image = tilted_image;
        this.front_image = front_image;
        this.back_image = back_image;
        this.card_number = card_number;
        this.kyc_type = kyc_type;
        this.blink_image = blink_image;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getTilted_image() {
        return tilted_image;
    }

    public void setTilted_image(String tilted_image) {
        this.tilted_image = tilted_image;
    }

    public String getFront_image() {
        return front_image;
    }

    public void setFront_image(String front_image) {
        this.front_image = front_image;
    }

    public String getBack_image() {
        return back_image;
    }

    public void setBack_image(String back_image) {
        this.back_image = back_image;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getKyc_type() {
        return kyc_type;
    }

    public void setKyc_type(String kyc_type) {
        this.kyc_type = kyc_type;
    }

    public String getBlink_image() {
        return blink_image;
    }

    public void setBlink_image(String blink_image) {
        this.blink_image = blink_image;
    }
}
