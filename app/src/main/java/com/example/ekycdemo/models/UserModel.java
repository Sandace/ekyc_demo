package com.example.ekycdemo.models;


public class UserModel{
    String nationality;

    public UserModel(String nationality){
    this.nationality = nationality;
    }
    public String getNationality(){
        return  nationality;
   }
    public void setNationality(){
        this.nationality = nationality;
   }
}