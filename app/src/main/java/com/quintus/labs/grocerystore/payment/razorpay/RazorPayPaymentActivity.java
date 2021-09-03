package com.quintus.labs.grocerystore.payment.razorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.CartActivity;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.fragment.HomeFragment;
import com.quintus.labs.grocerystore.model.CheckoutDetails;
import com.quintus.labs.grocerystore.model.UpdatePayment;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RazorPayPaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "RazorPay==>";

    LocalStorage localStorage;
    Gson gson;
    String token, address_id, totalAmount;
    String name, email, mobile;
    String key;
    int payment_id;
    View progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        progress = findViewById(R.id.progress_bar);
        localStorage = new LocalStorage(getApplicationContext());
        gson = new Gson();
        token = localStorage.getApiKey();
        address_id = localStorage.getAddressId();
        totalAmount = localStorage.getTotalAmount();
        User user = gson.fromJson(localStorage.getUserLogin(), User.class);
        name = user.getName();
        email = user.getEmail();
        mobile = user.getPhone();
        key = getIntent().getStringExtra("key");
        payment_id = getIntent().getIntExtra("payment_id", 0);


        Checkout.preload(getApplicationContext());


        startPayment();


    }


    public void startPayment() {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID(key);
        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            options.put("theme.color", "#6167F3");
            options.put("name", name);
            // options.put("description", description);
            Float payableAmount = Float.parseFloat(totalAmount) * 100;
            options.put("currency", "INR");
            options.put("amount", payableAmount);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", mobile);


            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", false);
            retryObj.put("max_count", 2);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {

        try {
            String status = "success";
            String payment_response_id = s;
            String payment_type = "online";
            UpdatePayment updatePayment = new UpdatePayment(status, payment_response_id, payment_type);
            updatePaymentData(updatePayment);

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }


        //  Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {

        if (s.contains("Payment processing cancelled by user")) {
            try {
                String status = "failure";
                String payment_response_id = "denied_payment";
                String payment_type = "online";
                UpdatePayment updatePayment = new UpdatePayment(status, payment_response_id, payment_type);
                updatePaymentData(updatePayment);
            } catch (Exception e) {
                Log.e(TAG, "Exception in onPaymentError", e);
            }
        } else {
            try {
                String str = s;
                String[] arrOfStr = str.split("\"payment_id\":\"", 2);
                String newstring = arrOfStr[1];
                String failed_id = newstring.replace("\"}}", " ");
                String status = "failure";
                String payment_response_id = failed_id;
                String payment_type = "online";
                UpdatePayment updatePayment = new UpdatePayment(status, payment_response_id, payment_type);
                updatePaymentData(updatePayment);

            } catch (Exception e) {
                Log.e(TAG, "Exception in onPaymentError", e);
            }
        }
        //   Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }


    private void updatePaymentData(UpdatePayment updatePayment) {
        showProgressDialog();
        Call<UpdatePayment> call = RestClient.getRestService(getApplicationContext()).updatePayment(token, String.valueOf(payment_id), updatePayment);
        call.enqueue(new Callback<UpdatePayment>() {
            @Override
            public void onResponse(Call<UpdatePayment> call, Response<UpdatePayment> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 200) {
                        CheckoutDetails checkout = new CheckoutDetails(String.valueOf(response.body().getId()), response.body().getStatus(), address_id, totalAmount, "", "", "");
                        checkoutData(checkout);


                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<UpdatePayment> call, Throwable t) {

            }
        });

    }

    private void checkoutData(CheckoutDetails checkoutDetails) {
        showProgressDialog();
        Call<CheckoutDetails> call = RestClient.getRestService(getApplicationContext()).createOrder(token, checkoutDetails);
        call.enqueue(new Callback<CheckoutDetails>() {
            @Override
            public void onResponse(Call<CheckoutDetails> call, Response<CheckoutDetails> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 201) {

                        if (response.body().getPaymentStatus().equalsIgnoreCase("failed")) {
                            Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CartActivity.class));
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Order placed Successfully !", Toast.LENGTH_SHORT).show();
                            localStorage.deleteCart();
                            //       showCustomDialog();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            finish();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        finish();
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<CheckoutDetails> call, Throwable t) {

            }
        });

    }

    private void showCustomDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        // Include dialog.xml file
        dialog.setContentView(R.layout.success_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        // Set dialog title

        dialog.show();
    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }
}