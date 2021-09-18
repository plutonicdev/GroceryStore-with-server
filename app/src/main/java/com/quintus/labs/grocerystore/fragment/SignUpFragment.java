package com.quintus.labs.grocerystore.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.LoginRegisterActivity;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.activity.OtpVarificationActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.model.UserResult;
import com.quintus.labs.grocerystore.util.CustomToast;
import com.quintus.labs.grocerystore.util.ErrorUtils;
import com.quintus.labs.grocerystore.util.NetworkCheck;
import com.quintus.labs.grocerystore.util.Utils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.quintus.labs.grocerystore.activity.BaseActivity.TAG;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class SignUpFragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, mobileNumber, emailId, password;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    User user;
    LocalStorage localStorage;
    Gson gson = new Gson();
    UserResponse userResponse;
    ErrorUtils errorUtils;
    View progress;
    String firebaseToken;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        localStorage = new LocalStorage(getContext());
        firebaseToken = localStorage.getFirebaseToken();
        errorUtils = new ErrorUtils(getContext());
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = view.findViewById(R.id.fullName);
        progress = view.findViewById(R.id.progress_bar);
        emailId = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);

        password = view.findViewById(R.id.password);
        signUpButton = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
        NetworkCheck.isNetworkAvailable(getContext());

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:
                // Replace login fragment
                new LoginRegisterActivity().replaceLoginFragment();
                break;
        }

    }


    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (getFullName.length() == 0) {
            fullName.setError("Enter Your Name");
            fullName.requestFocus();
        } else if (getEmailId.length() == 0) {
            emailId.setError("Enter Your Email");
            emailId.requestFocus();
        } else if (!getEmailId.matches(emailPattern)) {
            emailId.setError("Enter Correct Email");
            emailId.requestFocus();
        } else if (getMobileNumber.length() == 0) {
            mobileNumber.setError("Enter Your Mobile Number");
            mobileNumber.requestFocus();
        } else if (getMobileNumber.length() < 10) {
            mobileNumber.setError("Enter Correct Mobile Number");
            mobileNumber.requestFocus();
        } else if (getPassword.length() == 0) {
            password.setError("Enter Password");
            password.requestFocus();
        } else if (getPassword.length() < 8) {
            password.setError("Enter 8 digit Password");
            password.requestFocus();
        } else if (!terms_conditions.isChecked()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Accept Term & Conditions");
        } else {
            user = new User(getFullName, getEmailId, getMobileNumber, getPassword, firebaseToken);
            registerUser(user);

        }

    }

    private void registerUser(User userString) {
        showProgressDialog();
        Call<UserResponse> call = RestClient.getRestService(getContext()).register(userString);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    userResponse = response.body();
                    if (userResponse != null && response.code() == 201) {
                        String userJson = gson.toJson(userResponse);
                        localStorage.createUserLoginSession(userJson);
                        Toast.makeText(getContext(), getResources().getString(R.string.registered_successfull), Toast.LENGTH_LONG).show();

                        String masterToken = "Bearer " + response.headers().get("X-AUTH-TOKEN");
                        Log.d("masterToken", masterToken);
                        localStorage.setApiKey(masterToken);
                        if (userResponse.isPhone_verified()) {
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            startActivity(new Intent(getContext(), OtpVarificationActivity.class));
                            getActivity().finish();
                        }


                    } else {
                        errorUtils.checkUserError(response);

                    }


                } else {

                    errorUtils.checkUserError(response);
                }

                hideProgressDialog();

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                new CustomToast().Show_Toast(getActivity(), view,
                        "Server Error Please try after sometime");
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