package com.quintus.labs.grocerystore.util;

import android.content.Context;
import android.widget.Toast;


import com.google.gson.Gson;
import com.quintus.labs.grocerystore.model.APIError;
import com.quintus.labs.grocerystore.model.UserResponse;

import org.json.JSONObject;

import retrofit2.Response;

public class ErrorUtils {
    Context context;
    Gson gson = new Gson();

    public ErrorUtils(Context context) {
        this.context = context;
    }

    public void checkUserError(Response<UserResponse> response) {
        try {
            APIError apiError = new APIError();
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            apiError = gson.fromJson(jObjError.getJSONObject("message").toString(), APIError.class);
            if (apiError.getPhone() != null) {
                Toast.makeText(context, apiError.getPhone(), Toast.LENGTH_LONG).show();
            } else if (apiError.getEmail() != null) {
                Toast.makeText(context, apiError.getEmail(), Toast.LENGTH_LONG).show();
            } else if (apiError.getNon_field_errors() != null) {
                Toast.makeText(context, apiError.getNon_field_errors(), Toast.LENGTH_LONG).show();
            } else if (apiError.getOtp() != null) {
                Toast.makeText(context, apiError.getOtp(), Toast.LENGTH_LONG).show();
            } else if (apiError.getName() != null) {
                Toast.makeText(context, apiError.getName(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Please provide correct information", Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            Toast.makeText(context, "Please try after sometime", Toast.LENGTH_LONG).show();
        }
    }

}
