package com.example.ekycdemo.controllers;

import com.example.ekycdemo.models.UserModel;
import com.example.ekycdemo.views.MainActivity;

public class MainController {
    private MainActivity mainActivity;
    UserModel userModel;

    public MainController( ){
        userModel = new UserModel("No nationality Selected");
    }
    public UserModel getUser(){
      return userModel;
    }

    public void saveUserNationality(UserModel userModel){
         this.userModel  =  userModel;
    }
}
