package com.example.ekycdemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public static Retrofit getVersion1Adapter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rpsremit.truestreamz.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
