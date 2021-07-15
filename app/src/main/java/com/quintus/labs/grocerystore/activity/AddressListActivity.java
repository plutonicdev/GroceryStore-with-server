package com.quintus.labs.grocerystore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.AddAddressListResponse;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.util.ErrorUtils;
import com.quintus.labs.grocerystore.util.NetworkCheck;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListActivity extends AppCompatActivity {
    TextView address_name_display,address_phone_display,address_email_display,address_display,address_country_display,address_state_display,address_city_display,address_pincode_display,address_type_display;
    String spinnerStateValue, spinnerCountryValue, _city, _name, _email, _phone, _address, _state, _zip, _address_type, userString;
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        address_name_display = findViewById(R.id.address_name_display);
        address_phone_display = findViewById(R.id.address_phone_display);
        address_email_display = findViewById(R.id.address_email_display);
        address_display =findViewById(R.id.address_display);
        address_country_display =findViewById(R.id.address_country_display);
        address_state_display =findViewById(R.id.address_state_display);
        address_city_display =findViewById(R.id.address_city_display);
        address_pincode_display =findViewById(R.id.address_pincode_display);
        address_type_display =findViewById(R.id.address_type_display);

        localStorage = new LocalStorage(getApplicationContext());
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);
//        token = "Bearer " + user.getToken();

        if (NetworkCheck.isNetworkAvailable(getApplicationContext())) {
            AddressDataList();
        }
    }

    public void  AddressDataList(){

        Call<AddAddressListResponse> call = RestClient.getRestService(getApplicationContext()).getAddressList(user);
        call.enqueue(new Callback<AddAddressListResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<AddAddressListResponse> call, Response<AddAddressListResponse> response) {
                AddAddressListResponse addressListResponse = response.body();
                if (addressListResponse != null) {
//                    Log.d(TAG, "onResponse: " + response.body());
                    if (response.code() == 200) {
//                        if (addressListResponse.getAddressListData().getAddressCount() > 0) {
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//                            address = addressListResponse.getAddressListData().getAddresses();
//
//                            showAddressData();
//                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.label_something_went_wrong), Toast.LENGTH_LONG).show();

                }
            }



            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<AddAddressListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_try_after_sometime), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAddressData() {

    }
}