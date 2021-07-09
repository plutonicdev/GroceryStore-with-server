package com.quintus.labs.grocerystore.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quintus.labs.grocerystore.interfaces.AddorRemoveCallbacks;
import com.quintus.labs.grocerystore.model.Cart;
import com.quintus.labs.grocerystore.model.Order;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class BaseActivity extends AppCompatActivity implements AddorRemoveCallbacks {
    public static final String TAG = "BaseActivity===>";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 10;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 20;
    List<Cart> cartList = new ArrayList<Cart>();
    List<Order> orderList = new ArrayList<Order>();
    Gson gson;
    LocalStorage localStorage;
    String userJson;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = new LocalStorage(getApplicationContext());
        gson = new Gson();
        userJson = localStorage.getUserLogin();
        progressDialog = new ProgressDialog(BaseActivity.this);
        //user = gson.fromJson(userJson, UserResult.class);
        //  NetworkCheck.isNetworkAvailable(getApplicationContext());
        cartCount();

    }

    public int cartCount() {

        gson = new Gson();
        if (localStorage.getCart() != null) {
            String jsonCart = localStorage.getCart();
            Log.d("CART : ", jsonCart);
            Type type = new TypeToken<List<Cart>>() {
            }.getType();
            cartList = gson.fromJson(jsonCart, type);


            //Toast.makeText(getContext(),remedyList.size()+"",Toast.LENGTH_LONG).show();
            return cartList.size();
        }
        return 0;
    }

    public List<Cart> getCartList() {
        if (localStorage.getCart() != null) {
            String jsonCart = localStorage.getCart();
            //Log.d("CART : ", jsonCart);
            Type type = new TypeToken<List<Cart>>() {
            }.getType();
            cartList = gson.fromJson(jsonCart, type);
            return cartList;
        }
        return cartList;
    }

    public List<Order> getOrderList() {
        if (localStorage.getOrder() != null) {
            String jsonOrder = localStorage.getOrder();
            //Log.d("CART : ", jsonCart);
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            orderList = gson.fromJson(jsonOrder, type);
            return orderList;
        }
        return orderList;
    }

    public Double getTotalPrice() {
        cartList = getCartList();
        Double total = 0.0;
        if (cartCount() > 0) {
            for (int i = 0; i < cartList.size(); i++) {
                total = total + Double.valueOf(cartList.get(i).getSubTotal());
                Log.d(TAG, "Total :" + total + "");
            }
            Log.d(TAG, "Total :" + total + "");
            return total;
        }
        return total;
    }


    @Override
    public void onAddProduct() {

    }

    @Override
    public void onRemoveProduct() {

    }

    @Override
    public void updateTotalPrice() {

    }


    public void EnableRuntimePermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(BaseActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(BaseActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            }
            if (ContextCompat.checkSelfPermission(BaseActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                        Manifest.permission.CAMERA)) {

                } else {
                    ActivityCompat.requestPermissions(BaseActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }

    }

    public ProgressDialog showProgressDialog(String message) {

        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }
}
