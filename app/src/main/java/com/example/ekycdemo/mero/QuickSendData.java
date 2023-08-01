package com.example.ekycdemo.mero;

public class QuickSendData {
    private String name;
    private String name_jp;
    private String image;
    private String slug;
    private String place_holder;
    private String nationality;
    private ReceivingMethodsData receiving_methods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_jp() {
        return name_jp;
    }

    public void setName_jp(String name_jp) {
        this.name_jp = name_jp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPlace_holder() {
        return place_holder;
    }

    public void setPlace_holder(String place_holder) {
        this.place_holder = place_holder;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public ReceivingMethodsData getReceiving_methods() {
        return receiving_methods;
    }

    public void setReceiving_methods(ReceivingMethodsData receiving_methods) {
        this.receiving_methods = receiving_methods;
    }
}
