package com.quintus.labs.grocerystore.payment.payu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.CartActivity;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.CheckoutDetails;
import com.quintus.labs.grocerystore.model.UpdatePayment;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayuPaymentActivity extends AppCompatActivity {

    private static final String TAG ="PayuPaymentActivity==>" ;

    LocalStorage localStorage;
    Gson gson;
    String token, address_id, totalAmount;
    String name, email, mobile;
    String key;
    int payment_id;
    View progress;
    String serverCalculatedHash,amount,txnid,productinfo,mode,merchant_key;
    boolean debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payu_payment);
        progress = findViewById(R.id.progress_bar);
        localStorage = new LocalStorage(getApplicationContext());
        gson = new Gson();
        token = localStorage.getApiKey();
        User user = gson.fromJson(localStorage.getUserLogin(), User.class);
        name = user.getName();
        email = user.getEmail();
        mobile = user.getPhone();
        totalAmount = localStorage.getTotalAmount();
        address_id = localStorage.getAddressId();
        payment_id = getIntent().getIntExtra("payment_id", 0);
        serverCalculatedHash=getIntent().getStringExtra("serverCalculatedHash");
        amount=getIntent().getStringExtra("amount");
        txnid=getIntent().getStringExtra("txnid");
        productinfo=getIntent().getStringExtra("productinfo");
        mode=getIntent().getStringExtra("mode");
        merchant_key=getIntent().getStringExtra("merchant_key");

        if(mode.equalsIgnoreCase("Test")){
            debug=true;
        }else{
            debug=false;
        }
        showProgressDialog();

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                                             // Transaction ID
                .setPhone(mobile)                                           // User Phone number
                .setProductName(productinfo)                   // Product Name or description
                .setFirstName(name)                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")                    // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")                     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(debug)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(merchant_key)                        // Merchant key
                .setMerchantId("5960507");             // Merchant ID


        //declare paymentParam object
        PayUmoneySdkInitializer.PaymentParam paymentParam = null;
        try {
            paymentParam = builder.build();

        } catch (Exception e) {
            e.printStackTrace();
        }
//set the hash

        paymentParam.setMerchantHash(serverCalculatedHash);

        boolean isOverrideResultScreen= false;

        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam,
                this, R.style.AppTheme_default, isOverrideResultScreen);


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){

                    String status = "success";
                    String payment_response_id =transactionResponse.getTransactionDetails() ;
                    String payment_type = "online";
                    UpdatePayment updatePayment = new UpdatePayment(status, payment_response_id, payment_type);
                    updatePaymentData(updatePayment);

                    //Success Transaction
                } else{

                    String status = "failure";
                    String payment_response_id = transactionResponse.getTransactionDetails() ;
                    String payment_type = "online";
                    UpdatePayment updatePayment = new UpdatePayment(status, payment_response_id, payment_type);
                    updatePaymentData(updatePayment);
                    //Failure Transaction
                }

                // Response from Payumoney
              //  String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
              //  String merchantResponse = transactionResponse.getTransactionDetails();
            }  else {
                Log.d(TAG, "Both objects are null!");

                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }
        }else{

            Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();

        }

        hideProgressDialog();
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

                        if (response.body().getPaymentStatus().equalsIgnoreCase("")) {
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
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
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