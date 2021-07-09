package com.quintus.labs.grocerystore.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.LoginRegisterActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResult;
import com.quintus.labs.grocerystore.util.CustomToast;
import com.quintus.labs.grocerystore.util.NotificationHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
    private static View view;

    private static EditText mobile, password, confirm_password, code;
    private static TextView submit, back, reset, resend;
    FrameLayout forgot_password_FL, reset_password_FL;
    View progress;
    Gson gson = new Gson();
    User user;

    public ForgotPassword_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        mobile = view.findViewById(R.id.registered_mobile);
        submit = view.findViewById(R.id.forgot_button);
        back = view.findViewById(R.id.backToLoginBtn);
        forgot_password_FL = view.findViewById(R.id.forgot_passwordFL);
        reset_password_FL = view.findViewById(R.id.reset_password_FL);
        password = view.findViewById(R.id.passsword_text);
        confirm_password = view.findViewById(R.id.conf_passsword_text);
        code = view.findViewById(R.id.otp_text);
        reset = view.findViewById(R.id.reset_button);
        resend = view.findViewById(R.id.resend_button);
        reset_password_FL = view.findViewById(R.id.reset_password_FL);
        progress = view.findViewById(R.id.progress_bar);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            back.setTextColor(csl);
            submit.setTextColor(csl);

        } catch (Exception e) {
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        reset.setOnClickListener(this);
        resend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new LoginRegisterActivity().replaceLoginFragment();
                break;

            case R.id.forgot_button:
                // Call Submit button task
                submitButtonTask();
                break;
            case R.id.reset_button:
                // Call Submit button task
                resetButtonTask();
                break;
            case R.id.resend_button:
                // Call Submit button task
                forgotPassword();
                break;

        }

    }

    private void resetButtonTask() {
        String getOtp = code.getText().toString();
        String getPassword = password.getText().toString();
        String getConfPassword = confirm_password.getText().toString();
        if (getOtp.equalsIgnoreCase("") || getOtp.length() == 0 || getOtp.length() < 6) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your valid Code");
        } else if (getPassword.equalsIgnoreCase("") || getPassword.length() == 0 || getPassword.length() < 8) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your valid password");
        } else if (getConfPassword.equalsIgnoreCase("") || getConfPassword.length() == 0 || getConfPassword.length() < 8) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your valid confirm password");
        } else if (!getConfPassword.equalsIgnoreCase(getPassword)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Password and confirm password doesn't match");
        } else {
            user.setReset_code(getOtp);
            user.setPassword(getPassword);
            resetPassword();
        }

    }

    private void resetPassword() {
        showProgressDialog();
        Call<UserResult> call = RestClient.getRestService(getContext()).resetPassword(user);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    UserResult userResult = response.body();
                    if (userResult != null && userResult.getCode() == 200) {
                        new LoginRegisterActivity().replaceLoginFragment();
                    } else {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Please try again");
                    }
                } else {
                    new CustomToast().Show_Toast(getActivity(), view,
                            "Please enter your valid Code");
                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                hideProgressDialog();
            }
        });

    }


    private void submitButtonTask() {
        String getMobile = mobile.getText().toString();
        if (getMobile.equals("") || getMobile.length() == 0 || getMobile.length() < 10) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your Mobile Number");
        } else {
            user = new User();
            user.setMobile(getMobile);
            forgotPassword();
        }
    }

    private void forgotPassword() {
        showProgressDialog();
        Call<UserResult> call = RestClient.getRestService(getContext()).forgotPassword(user);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    UserResult userResult = response.body();
                    if (userResult != null && userResult.getCode() == 200) {
                        String userString = gson.toJson(userResult.getUser());
                        NotificationHelper notificationHelper = new NotificationHelper(getContext());
                        notificationHelper.createNotification("Reset password Code", userResult.getUser().getReset_code());
                        reset_password_FL.setVisibility(View.VISIBLE);
                        forgot_password_FL.setVisibility(View.GONE);
                    } else {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Please enter your valid mobile number");
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                hideProgressDialog();
            }
        });
    }


    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }
}