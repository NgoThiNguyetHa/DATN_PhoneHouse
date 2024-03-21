package com.example.appkhachhang.untils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String PREF_NAME = "UserData";
    private static final String KEY_USER_ID = "_id";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_Phone = "phone";
    private static final String KEY_USER_Address = "address";
    private static final String KEY_USER_PASSWORD= "password";


    private SharedPreferences sharedPreferences;

    public MySharedPreferences(Context context) {
        // Initialize SharedPreferences
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save user data
    public void saveUserData(String userId , String userName , String email , String passWord , String phone , String address ) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PASSWORD, passWord);
        editor.putString(KEY_USER_Phone,phone);
        editor.putString(KEY_USER_Address, address);
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


    public String getPassword() {
        return sharedPreferences.getString(KEY_USER_PASSWORD, null);
    }
    public String getEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getPhone() {
        return sharedPreferences.getString(KEY_USER_Phone, null);
    }
    public String getAddress() {
        return sharedPreferences.getString(KEY_USER_Address, null);
    }


    // Clear user data
    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_PASSWORD);
        editor.remove(KEY_USER_Phone);
        editor.remove(KEY_USER_Address);
        editor.apply();
    }
}
