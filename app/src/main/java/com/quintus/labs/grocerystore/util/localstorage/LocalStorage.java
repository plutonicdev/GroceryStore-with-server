package com.quintus.labs.grocerystore.util.localstorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class LocalStorage {

    public static final String KEY_USER = "User";
    public static final String KEY_USER_ADDRESS = "user_address";
    private static final String KEY_FIREBASE_TOKEN = "firebaseToken";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String CART = "CART";
    private static final String ORDER = "ORDER";


    private static LocalStorage instance = null;
    SharedPreferences sharedPreferences;
    Editor editor;
    int PRIVATE_MODE = 0;
    Context _context;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("Preferences", 0);
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalStorage.class) {
                if (instance == null) {
                    instance = new LocalStorage(context);
                }
            }
        }
        return instance;
    }

    public void createUserLoginSession(String user) {
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USER, user);
        editor.commit();
    }

    public String getUserLogin() {
        return sharedPreferences.getString(KEY_USER, "");
    }


    public void logoutUser() {
        editor = sharedPreferences.edit();
        editor.remove(KEY_USER);
        editor.remove(KEY_USER_ADDRESS);
        editor.remove(IS_USER_LOGIN);
        editor.remove(CART);
        editor.remove(ORDER);
        editor.commit();
    }

    public boolean checkLogin() {
        // Check login status
        return !this.isUserLoggedIn();
    }


    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public String getUserAddress() {
        if (sharedPreferences.contains(KEY_USER_ADDRESS))
            return sharedPreferences.getString(KEY_USER_ADDRESS, null);
        else return null;
    }


    public void setUserAddress(String user_address) {
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ADDRESS, user_address);
        editor.commit();
    }

    public String getCart() {
        if (sharedPreferences.contains(CART))
            return sharedPreferences.getString(CART, null);
        else return null;
    }


    public void setCart(String cart) {
        Editor editor = sharedPreferences.edit();
        editor.putString(CART, cart);
        editor.commit();
    }

    public void deleteCart() {
        Editor editor = sharedPreferences.edit();
        editor.remove(CART);
        editor.commit();
    }


    public String getOrder() {
        if (sharedPreferences.contains(ORDER))
            return sharedPreferences.getString(ORDER, null);
        else return null;
    }


    public void setOrder(String order) {
        Editor editor = sharedPreferences.edit();
        editor.putString(ORDER, order);
        editor.commit();
    }

    public void deleteOrder() {
        Editor editor = sharedPreferences.edit();
        editor.remove(ORDER);
        editor.commit();
    }


    public String getFirebaseToken() {
        return sharedPreferences.getString(KEY_FIREBASE_TOKEN, null);
    }

    public void setFirebaseToken(String firebaseToken) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_FIREBASE_TOKEN, firebaseToken);
        editor.commit();
    }


}
