package com.quintus.labs.grocerystore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class OtpVarificationActivity extends AppCompatActivity{

    public static final String TAG = "VarificationActivity==>";
    Button submit;
    String _otp,_phone,firebaseToken;
    LocalStorage localStorage;
    Gson gson = new Gson();
    private static EditText otpcode;
    User user;
    int _otpSuccess = 1;
    CountDownTimer cTimer = null;
    int counter = 30;
    TextView otp;
    EditText otp_box_1,otp_box_2,otp_box_3,otp_box_4,otp_box_5,otp_box_6;
    TextView resend_otp,otp_timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        otpcode = findViewById(R.id.otp_text);
        localStorage = new LocalStorage(getApplicationContext());
        firebaseToken = localStorage.getFirebaseToken();
        submit = findViewById(R.id.submit);
        _phone = getIntent().getStringExtra("mobile");



        otp = findViewById(R.id.otp);
        resend_otp = findViewById(R.id.resend_otp);
        otp_timer = findViewById(R.id.otp_timer);
        otp_box_1 = findViewById(R.id.otp_box_1);
        otp_box_2 = findViewById(R.id.otp_box_2);
        otp_box_3 = findViewById(R.id.otp_box_3);
        otp_box_4 = findViewById(R.id.otp_box_4);
        otp_box_5 = findViewById(R.id.otp_box_5);
        otp_box_6 = findViewById(R.id.otp_box_6);
        otp.setText(Html.fromHtml(getResources().getString(R.string.otp1)));
        otp_box_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable!=null){
                    if(editable.length()==1)
                        otp_box_2.requestFocus();
                }
            }
        });
        otp_box_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable!=null){
                    if(editable.length()==1)
                        otp_box_3.requestFocus();
                }
            }
        });
        otp_box_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable!=null){
                    if(editable.length()==1)
                        otp_box_4.requestFocus();
                }
            }
        });
        otp_box_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable!=null){
                    if(editable.length()==1)
                        otp_box_5.requestFocus();
                }
            }
        });
        otp_box_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable!=null){
                    if(editable.length()==1)
                        otp_box_6.requestFocus();
                }
            }
        });

    }


    public void onVerifyMobileOtpClicked(View view) {
        _otp = otp_box_1.getText().toString().trim()+otp_box_2.getText().toString().trim()+otp_box_3.getText().toString().trim()+otp_box_4.getText().toString().trim()+otp_box_5.getText().toString().trim()+otp_box_6.getText().toString().trim();

       if (_otp.length() != 6) {
           Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
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

        showTimer();

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

    private void showTimer() {
       otp_timer.setVisibility(View.VISIBLE);
       resend_otp.setVisibility(View.GONE);
       cTimer =  new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(counter>0){
                    otp_timer.setText("Please Wait : "+counter);
                    counter--;
                }

            }
            @Override
            public void onFinish() {
                resend_otp.setVisibility(View.VISIBLE);
                otp_timer.setVisibility(View.GONE);

            }
        }.start();
    }


}