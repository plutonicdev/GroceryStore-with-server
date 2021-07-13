package com.quintus.labs.grocerystore.payment.instamojo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.instamojo.android.Instamojo;
import com.instamojo.android.helpers.Constants;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.payment.instamojo.interfaces.MyBackendService;
import com.quintus.labs.grocerystore.payment.instamojo.model.GatewayOrderStatus;
import com.quintus.labs.grocerystore.payment.instamojo.model.GetOrderIDRequest;
import com.quintus.labs.grocerystore.payment.instamojo.model.GetOrderIDResponse;
import com.quintus.labs.grocerystore.payment.instamojo.model.PaymentStatus;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstamojoPaymentActivity extends AppCompatActivity  implements Instamojo.InstamojoPaymentCallback {
   public String TAG = "InstamojoPaymentActivity==>";

    private static final HashMap<Instamojo.Environment, String> env_options = new HashMap<>();

    static {
        env_options.put(Instamojo.Environment.TEST, "https://test.instamojo.com/");
        env_options.put(Instamojo.Environment.PRODUCTION, "https://api.instamojo.com/");
    }

    private AlertDialog dialog;
    Intent intent;
    LocalStorage localStorage;
    private Instamojo.Environment mCurrentEnv = Instamojo.Environment.TEST;
    private boolean mCustomUIFlow = false;
    Gson gson = new Gson();
    ProgressDialog progressDialog;
    private TextView nameBox, emailBox, phoneBox, amountBox, descriptionBox;
    private MyBackendService myBackendService;
    private String _name, _email, _phone, _amount, _description, userJson, _subscriptionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor("#284D68");
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Payment");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        Button button = findViewById(R.id.pay);
        nameBox = findViewById(R.id.name);
        emailBox = findViewById(R.id.email);
        phoneBox = findViewById(R.id.phone);
        amountBox = findViewById(R.id.amount);
        descriptionBox = findViewById(R.id.description);
        progressDialog = new ProgressDialog(InstamojoPaymentActivity.this);

        intent = getIntent();

        localStorage = new LocalStorage(getApplicationContext());
        userJson = localStorage.getUserLogin();

        User user = gson.fromJson(userJson, User.class);
        _name = user.getName();
        _email = user.getEmail();
        _phone = user.getPhone();
        _amount = intent.getStringExtra("amount");
        _description = intent.getStringExtra("name") + " payment";
        _subscriptionId = intent.getStringExtra("id");

        nameBox.setText(_name);
        emailBox.setText(_email);
        phoneBox.setText(_phone);
        amountBox.setText(_amount);
        descriptionBox.setText("Payment For Stock Mind");

        mCurrentEnv = Instamojo.Environment.PRODUCTION;
        mCustomUIFlow = false;
        Instamojo.getInstance().initialize(InstamojoPaymentActivity.this, mCurrentEnv);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        // Initialize the backend service client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myBackendService = retrofit.create(MyBackendService.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderOnServer();
            }
        });
    }

    private void createOrderOnServer() {
        progressDialog.setMessage("Creating Order....");
        progressDialog.show();

        GetOrderIDRequest request = new GetOrderIDRequest();
        request.setBuyerName(nameBox.getText().toString());
        request.setBuyerEmail(emailBox.getText().toString());
        request.setBuyerPhone(phoneBox.getText().toString());
        request.setDescription(descriptionBox.getText().toString());
        request.setAmount(amountBox.getText().toString());

        Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder(localStorage.getApiKey(), request);
        getOrderIDCall.enqueue(new retrofit2.Callback<GetOrderIDResponse>() {
            @Override
            public void onResponse(Call<GetOrderIDResponse> call, Response<GetOrderIDResponse> response) {
                Log.d(TAG, response.code() + "");
                Log.d(TAG, response.body() + "");
                if (response.isSuccessful()) {

                    Log.d(TAG, response.code() + "");
                    Log.d(TAG, response.body() + "");

                    String orderId = response.body().getOrderID();
                    mCurrentEnv = Instamojo.Environment.valueOf(response.body().getEnvironment());
                    Instamojo.getInstance().initialize(InstamojoPaymentActivity.this, mCurrentEnv);

                    if (!mCustomUIFlow) {
                        // Initiate the default SDK-provided payment activity
                        initiateSDKPayment(orderId);

                    } else {
                        // OR initiate a custom UI activity
                        initiateSDKPayment(orderId);
                    }

                } else {
                    // Handle api errors
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG, "Error in response" + jObjError.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetOrderIDResponse> call, Throwable t) {
                // Handle call failure
                Log.d(TAG, "Failure");
                progressDialog.dismiss();
            }
        });
    }

    private void initiateSDKPayment(String orderID) {
        try {
            Instamojo.getInstance().initiatePayment(InstamojoPaymentActivity.this, orderID, InstamojoPaymentActivity.this);
        } catch (Exception e) {
            Log.d("ERROR==>", e.getMessage());
        }

    }

/*
    private void initiateCustomPayment(String orderID) {
        Intent intent = new Intent(getBaseContext(), CustomUIActivity.class);
        intent.putExtra(Constants.ORDER_ID, orderID);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }
*/

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Will check for the transaction status of a particular Transaction
     *
     * @param transactionID Unique identifier of a transaction ID
     */
    private void checkPaymentStatus(final String transactionID, final String orderID) {
        if (transactionID == null && orderID == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Checking transaction status");
        Call<GatewayOrderStatus> getOrderStatusCall = myBackendService.orderStatus(localStorage.getApiKey(), mCurrentEnv.name().toLowerCase(),
                orderID, transactionID);
        getOrderStatusCall.enqueue(new retrofit2.Callback<GatewayOrderStatus>() {
            @Override
            public void onResponse(Call<GatewayOrderStatus> call, final Response<GatewayOrderStatus> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    GatewayOrderStatus orderStatus = response.body();
                    if (orderStatus.getStatus().equalsIgnoreCase("successful")) {
                        showToast("Transaction still pending");
                        return;
                    }

                    showToast("Transaction successful for id - " + orderStatus.getPaymentID());
                    refundTheAmount(transactionID, orderStatus.getAmount());

                } else {
                    showToast("Error occurred while fetching transaction status");
                }
            }

            @Override
            public void onFailure(Call<GatewayOrderStatus> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        showToast("Failed to fetch the transaction status");
                    }
                });
            }
        });
    }

    /**
     * Will initiate a refund for a given transaction with given amount
     *
     * @param transactionID Unique identifier for the transaction
     * @param amount        amount to be refunded
     */
    private void refundTheAmount(String transactionID, String amount) {
        if (transactionID == null || amount == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Initiating a refund for - " + amount);
        Call<ResponseBody> refundCall = myBackendService.refundAmount(localStorage.getApiKey(),
                mCurrentEnv.name().toLowerCase(),
                transactionID, amount);
        refundCall.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    showToast("Refund initiated successfully");

                } else {
                    showToast("Failed to initiate a refund");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                showToast("Failed to Initiate a refund");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(Constants.ORDER_ID);
            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
                checkPaymentStatus(transactionID, orderID);
            } else {
                showToast("Oops!! Payment was cancelled");
            }
        }
    }

    @Override
    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Log.d(TAG, "Payment complete");
        String paymentResult = "Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus;
        showToast("Payment processing done");
        try {
           /* Answers.getInstance().logCustom(new CustomEvent("Payment Complete")
                    .putCustomAttribute("Result", paymentResult)
                    .putCustomAttribute("Status", paymentStatus)
                    .putCustomAttribute("User Name", _name)
                    .putCustomAttribute("User Phone", _phone));
*/
        }
        catch (Exception e){
        }
        PaymentStatus pgStatus = new PaymentStatus();
        pgStatus.setOrderID(orderID);
        pgStatus.setPaymentID(paymentID);
        pgStatus.setTransactionID(transactionID);
        pgStatus.setPaymentStatus(paymentStatus);
        completePayment(pgStatus);
    }

    private void completePayment(final PaymentStatus pgStatus) {

        Call<PaymentStatus> refundCall = myBackendService.completePayment(localStorage.getApiKey(), pgStatus);
        refundCall.enqueue(new retrofit2.Callback<PaymentStatus>() {
            @Override
            public void onResponse(Call<PaymentStatus> call, Response<PaymentStatus> response) {
                Log.d(TAG, "Response code for complete payment : " + response.code());
                Log.d(TAG, "Response : " + response.body());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.code() != 200) {
                    Log.d(TAG, "Response : " + response.errorBody());
                    showToast("Failed to complete transaction, contact support");
                    try {
                       /* Answers.getInstance().logCustom(new CustomEvent("Payment Failure")
                                .putCustomAttribute("Result", "onResponse: Failed to complete transaction , Error : " + response.errorBody().string())
                                .putCustomAttribute("User Name", _name)
                                .putCustomAttribute("User Phone", _phone)
                                .putCustomAttribute("Order ID", pgStatus.getOrderID())
                                .putCustomAttribute("Transaction ID", pgStatus.getTransactionID())
                                .putCustomAttribute("Payment ID", pgStatus.getPaymentID())
                                .putCustomAttribute("Payment Status", pgStatus.getPaymentStatus()));*/
                    }
                    catch (Exception e){
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), InstamojoPaymentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    try {
                       /* Answers.getInstance().logPurchase(new PurchaseEvent()
                                .putItemPrice(BigDecimal.valueOf(Long.parseLong(_amount)))
                                .putCurrency(Currency.getInstance("INR"))
                                .putSuccess((pgStatus.getPaymentStatus() != null && pgStatus.getPaymentStatus().equalsIgnoreCase("credit")))
                                .putCustomAttribute("User Name", _name)
                                .putCustomAttribute("User Phone", _phone)
                                .putCustomAttribute("Order ID", pgStatus.getOrderID())
                                .putCustomAttribute("Transaction ID", pgStatus.getTransactionID())
                                .putCustomAttribute("Payment ID", pgStatus.getPaymentID())
                                .putCustomAttribute("Payment Status", pgStatus.getPaymentStatus()));*/
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentStatus> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Log.d(TAG, "onFailure : Failed to complete transaction, Error : " + t.getMessage());
                showToast("Failed to complete transaction, contact support");
                try {
                  /*  Answers.getInstance().logCustom(new CustomEvent("Payment Failure")
                            .putCustomAttribute("Result", "onFailure: Failed to complete transaction , Error : " + t.getMessage())
                            .putCustomAttribute("User Name", _name)
                            .putCustomAttribute("User Phone", _phone)
                            .putCustomAttribute("Order ID", pgStatus.getOrderID())
                            .putCustomAttribute("Transaction ID", pgStatus.getTransactionID())
                            .putCustomAttribute("Payment ID", pgStatus.getPaymentID())
                            .putCustomAttribute("Payment Status", pgStatus.getPaymentStatus()));*/
                }
                catch (Exception e){
                }
            }
        });
    }

    @Override
    public void onPaymentCancelled() {
        Log.d(TAG, "Payment cancelled");
        showToast("Payment cancelled by user");
        try {
           /* Answers.getInstance().logCustom(new CustomEvent("Payment Cancelled")
                    .putCustomAttribute("Result", "Payment cancelled by user")
                    .putCustomAttribute("User Name", _name)
                    .putCustomAttribute("User Phone", _phone));*/
        }
        catch (Exception e){
        }
    }

    @Override
    public void onInitiatePaymentFailure(String errorMessage) {
        Log.d(TAG, "Initiate payment failed");
        showToast("Initiating payment failed. Error: " + errorMessage);
        try {
           /* Answers.getInstance().logCustom(new CustomEvent("Payment Not Initiated")
                    .putCustomAttribute("Result", "Initiating payment failed. Error: " + errorMessage)
                    .putCustomAttribute("User Name", _name)
                    .putCustomAttribute("User Phone", _phone));*/
        }
        catch (Exception e){
        }
    }

    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
}
