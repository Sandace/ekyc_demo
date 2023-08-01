package com.example.ekycdemo.controllers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "UserTokenPreferences";
    private static final String PREF_NAME_FOR_CARD = "CardDetailPreferences";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_CARD_NAME = "cardName";

    public static void saveAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.apply();
    }
    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }
    public static void saveCardName(Context context, String keys){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME_FOR_CARD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CARD_NAME,keys);
        editor.apply();
    }
    public static String getCardName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME_FOR_CARD, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CARD_NAME, "Resident Card");
    }
}
