package com.quintus.labs.grocerystore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.fragment.LoginFragment;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.util.CustomToast;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.quintus.labs.grocerystore.activity.BaseActivity.TAG;


public class OtpVarificationActivity extends AppCompatActivity {

    public static final String TAG = "ForgotPassword==>";
    Button submit;
    String _otp,_phone,firebaseToken;
    LocalStorage localStorage;
    Gson gson = new Gson();
    private static EditText otpcode;
    User user;
    int _otpSuccess = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        otpcode = findViewById(R.id.otp_text);
        localStorage = new LocalStorage(getApplicationContext());
        firebaseToken = localStorage.getFirebaseToken();
        submit = findViewById(R.id.submit);
        _phone = getIntent().getStringExtra("mobile");



    }

    public void onVerifyMobileOtpClicked(View view) {
        _otp = otpcode.getText().toString().trim();

        if (_otp.length() == 0) {
            otpcode.setError(getResources().getString(R.string.enter_otp));
            otpcode.requestFocus();
        } else if (_otp.length() != 6) {
            otpcode.setError(getResources().getString(R.string.enter_valid_otp));
            otpcode.requestFocus();
        } else {
            user = new User(_phone, _otp, firebaseToken);
            submitOtp(user);
        }

    }


    private void submitOtp(User user) {
        Call<UserResponse> call = RestClient.getRestService(getApplicationContext()).otpVarification(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                Log.d("Response :=>", response.body() + "");
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    if (response.code() == 200) {
                        startActivity(new Intent(getApplicationContext(), LoginFragment.class));
                        Toast.makeText(getApplicationContext(), "Mobile verified Successfully", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Correct OTP", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_try_after_sometime), Toast.LENGTH_LONG).show();

            }
        });

    }


    public void onResendOTPClicked(View view) {
        User user = gson.fromJson(localStorage.getUserLogin(), User.class);
        Call<UserResponse> call = RestClient.getRestService(getApplicationContext()).resendOTP(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), "Otp sent to your mobile number", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Correct OTP", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_try_after_sometime), Toast.LENGTH_LONG).show();
            }
        });


    }
}