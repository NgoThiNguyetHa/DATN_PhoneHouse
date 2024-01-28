package com.example.appcuahang.untils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String PREF_NAME = "UserData";
    private static final String KEY_USER_ID = "_id";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PASSWORD= "password";


    private SharedPreferences sharedPreferences;

    public MySharedPreferences(Context context) {
        // Initialize SharedPreferences
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save user data
    public void saveUserData(String userId, String userName, String email ,String passWood) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PASSWORD, passWood);
        editor.apply();
    }

    // Retrieve user ID
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    // Retrieve user name
    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }


    public String getMail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    // Clear user data
    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_PASSWORD);
        editor.apply();
    }
}
