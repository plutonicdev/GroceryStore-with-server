package com.quintus.labs.grocerystore.payment.payu;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.BaseActivity;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class PaymentActivity extends BaseActivity {

    public static final String TAG = "PaymentActivity : ";
    private boolean isDisableExitConfirmation = false;
    private String userMobile, userEmail;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    LocalStorage localStorage;
    private EditText email_et, mobile_et, amount_et;
    private TextInputLayout email_til, mobile_til;
    private RadioGroup radioGroup_color_theme, radioGroup_select_env;
    private SwitchCompat switch_disable_wallet, switch_disable_netBanks, switch_disable_cards, switch_disable_ThirdPartyWallets, switch_disable_ExitConfirmation;
    private TextView logoutBtn;
    private CardView selectThemeSection, selectEnvironmentSection;
    private AppCompatRadioButton radio_btn_default;
    private AppPreference mAppPreference;
    private AppCompatRadioButton radio_btn_theme_purple, radio_btn_theme_pink, radio_btn_theme_green, radio_btn_theme_grey;
    double amount = 0.00;
    Map<String, Double> lateFeeAmountMap = new HashMap<>();
    private Button payNowButton;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    Gson gson;
    boolean doubleBackToExitPressedOnce = false;


    public static String hashCal(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setErrorInputLayout(EditText editText, String msg, TextInputLayout textInputLayout) {
        textInputLayout.setError(msg);
        editText.requestFocus();
    }

    public static boolean isValidEmail(String strEmail) {
        return strEmail != null && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(AppPreference.PHONE_PATTERN);

        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        setContentView(R.layout.activity_payment);
/*        localStorage = new LocalStorage(this);
        mAppPreference = new AppPreference();
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.app_name));
        settings = getSharedPreferences("settings", MODE_PRIVATE);
        logoutBtn = findViewById(R.id.logout_button);
        email_et = findViewById(R.id.email_et);
        mobile_et = findViewById(R.id.mobile_et);
        amount_et = findViewById(R.id.amount_et);
        amount_et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
        email_til = findViewById(R.id.email_til);
        mobile_til = findViewById(R.id.mobile_til);
        radioGroup_color_theme = findViewById(R.id.radio_grp_color_theme);
        radio_btn_default = findViewById(R.id.radio_btn_theme_default);
        radio_btn_theme_pink = findViewById(R.id.radio_btn_theme_pink);
        radio_btn_theme_purple = findViewById(R.id.radio_btn_theme_purple);
        radio_btn_theme_green = findViewById(R.id.radio_btn_theme_green);
        radio_btn_theme_grey = findViewById(R.id.radio_btn_theme_grey);
        radioGroup_color_theme.setVisibility(GONE);
        selectThemeSection = findViewById(R.id.select_theme_section);
        selectEnvironmentSection = findViewById(R.id.select_environment_detail);
        selectEnvironmentSection.setVisibility(GONE);
        selectThemeSection.setVisibility(GONE);
        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(GONE);
        }

        logoutBtn.setOnClickListener(this);
        AppCompatRadioButton radio_btn_sandbox = findViewById(R.id.radio_btn_sandbox);
        AppCompatRadioButton radio_btn_production = findViewById(R.id.radio_btn_production);
        radioGroup_select_env = findViewById(R.id.radio_grp_env);

        payNowButton = findViewById(R.id.pay_now_button);
        payNowButton.setOnClickListener(this);

        Intent intent = this.getIntent();
        amount = intent.getDoubleExtra("Amount", 0.00);
        String lateFeesStr = intent.getStringExtra("LateFeeAmount");
        String paymentDueStr = intent.getStringExtra("Heads");
        Type listType = new TypeToken<List<PeriodicCharge>>() {
        }.getType();
        Type mapType = new TypeToken<Map<String, Double>>() {
        }.getType();
        paymentHeads = gson.fromJson(paymentDueStr, listType);
        lateFeeAmountMap = gson.fromJson(lateFeesStr, mapType);
        initListeners();
        ((MyApplication) getApplication()).setAppEnvironment(getEnvironment());
        //Set Up SharedPref
        setUpUserDetails();
        setupCitrusConfigs();
        //selectSandBoxEnv();
        AppPreference.selectedTheme = R.style.AppTheme_blue;*/
    }

/*    private AppEnvironment getEnvironment() {
        String configStr = localStorage.getServerConfig();
        ServerConfig config = gson.fromJson(configStr, ServerConfig.class);
        AppEnvironment environment = new AppEnvironment();
        environment.setSalt(config.getPayuSalt());
        environment.setMerchantID(config.getPayUMerchantId().toString());
        environment.setMerchantKey(config.getPayuMerchantKey());
        environment.setBaseUrl(config.getPayuBaseUrl());
        if (config.getPayuProdEnvironment()) {
            environment.setDebug(false);
            environment.setMode(AppEnvironment.Type.PRODUCTION);
        } else {
            environment.setDebug(true);
            environment.setMode(AppEnvironment.Type.SANDBOX);
        }
        return environment;
    }

    private void setUpUserDetails() {
        String loginInfoStr = localStorage.getMemberTenantInfo();
        LoginInfo _info = null;
        if (loginInfoStr != null && loginInfoStr.trim().length() > 0) {
            _info = gson.fromJson(loginInfoStr, LoginInfo.class);
        }
        email_et.setText(_info.getEmail());
        mobile_et.setText(_info.getMobile());
        amount_et.setText(String.valueOf(amount));
        restoreAppPref();
    }

    private void restoreAppPref() {


        //Set Up saved theme pref
        switch (AppPreference.selectedTheme) {
            case -1:
                radio_btn_default.setChecked(true);
                break;
            case R.style.AppTheme_pink:
                radio_btn_theme_pink.setChecked(true);
                break;
            case R.style.AppTheme_Grey:
                radio_btn_theme_grey.setChecked(true);
                break;
            case R.style.AppTheme_purple:
                radio_btn_theme_purple.setChecked(true);
                break;
            case R.style.AppTheme_Green:
                radio_btn_theme_green.setChecked(true);
                break;
            default:
                radio_btn_default.setChecked(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        payNowButton.setEnabled(true);

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(GONE);
        }
    }

    *//**
     * This function sets the mode to PRODUCTION in Shared Preference
     *//*
    private void selectProdEnv() {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) getApplication()).setAppEnvironment(getEnvironment());
                editor = settings.edit();
                editor.putBoolean("is_prod_env", true);
                editor.apply();

                if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
                    logoutBtn.setVisibility(View.VISIBLE);
                } else {
                    logoutBtn.setVisibility(GONE);
                }

                //setupCitrusConfigs();
            }
        }, AppPreference.MENU_DELAY);
    }

    *//**
     * This function sets the mode to SANDBOX in Shared Preference
     *//*
    private void selectSandBoxEnv() {
        ((MyApplication) getApplication()).setAppEnvironment(getEnvironment());
        editor = settings.edit();
        editor.putBoolean("is_prod_env", false);
        editor.apply();

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(GONE);

        }
        //setupCitrusConfigs();
    }

    private void setupCitrusConfigs() {
        AppEnvironment appEnvironment = ((MyApplication) getApplication()).getAppEnvironment();
        if (appEnvironment.getMode() == AppEnvironment.Type.PRODUCTION) {
            selectProdEnv();
            Toast.makeText(PaymentActivity.this, "Environment Set to Production", Toast.LENGTH_SHORT).show();
        } else {
            selectSandBoxEnv();
            Toast.makeText(PaymentActivity.this, "Environment Set to SandBox", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("PaymentActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
            addPaymentStatus(transactionResponse, resultModel);
        }
    }

    private void addPaymentStatus(TransactionResponse transactionResponse, ResultModel resultModel) {
        PaymentResponseStatus paymentResponseStatus = null;
        boolean isSuccess = false;
        boolean isCancelled = false;
        Gson gson = new Gson();
        String loginInfoStr = localStorage.getMemberTenantInfo();
        LoginInfo _info = gson.fromJson(loginInfoStr, LoginInfo.class);
        Receipt receipt = new Receipt();
        receipt.setByGateway(String.valueOf(amount));
        receipt.setThroughGateway(true);
        receipt.setParty(_info.getFlatNo());
        if (_info.getType().equals(LoginInfo.Type.Owner)) {
            receipt.setMemberid(String.valueOf(_info.getId()));
        } else if (_info.getType().equals(LoginInfo.Type.Tenant)) {
            receipt.setTID(String.valueOf(_info.getTID()));
        }
        receipt.setSocietyId(String.valueOf(_info.getSocietyId()));

        // Check which object is non-null
        if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

            receipt.setPaymentStatus(transactionResponse.getTransactionStatus().name());
            if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                //Success Transaction
                isSuccess = true;
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Transaction successful, you can download receipt from Reports menu")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
            } else if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.CANCELLED)) {
                isCancelled = true;
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Transaction was cancelled by user")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
            } else {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Transaction failed, please retry")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
            }

            // Response from Payumoney
            String payuResponse = transactionResponse.getPayuResponse();

            paymentResponseStatus = gson.fromJson(payuResponse, PaymentResponseStatus.class);

            // Response from SURl and FURL
            String merchantResponse = transactionResponse.getTransactionDetails();

            Log.d(TAG, "Payu's Data : " + payuResponse);
            Log.d(TAG, "Merchant's Data : " + merchantResponse);

        } else if (resultModel != null && resultModel.getError() != null) {
            Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
        } else {
            Log.d(TAG, "Both objects are null!");
        }

        receipt.setIsActive(isSuccess);
        receipt.setCancelled(isCancelled);

        receipt.setPayableAmount(String.valueOf(amount));
        List<ReceiptDetail> receiptDetails = new ArrayList<>();
        if (null != paymentHeads && paymentHeads.size() > 0) {
            for (PeriodicCharge due : paymentHeads) {
                if (due.getHead().equalsIgnoreCase("Previous Balance")) {
                    receipt.setPBalamt(due.getRate());
                    continue;
                }

                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setHead(due.getHead());
                receiptDetail.setAmount(due.getDueAmount());
                receiptDetail.setDiscount(due.getDiscountAmount());
                receiptDetail.setDueFrom(due.getAppliedFromDate());
                receiptDetail.setDueto(due.getAppliedToDate());
                receiptDetail.setPenalty(lateFeeAmountMap.get(due.getHead()));
                receiptDetail.setParty(_info.getFlatNo());
                if (_info.getType().equals(LoginInfo.Type.Owner)) {
                    receiptDetail.setMemberId(_info.getId());
                } else if (_info.getType().equals(LoginInfo.Type.Tenant)) {
                    receiptDetail.setTID(String.valueOf(_info.getId()));
                }
                receiptDetails.add(receiptDetail);
            }
        }
        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setReceipt(receipt);
        paymentReceipt.setReceiptDetails(receiptDetails);
        if (paymentResponseStatus != null && paymentResponseStatus.getResult() != null) {
            paymentReceipt.setTransactionId(paymentResponseStatus.getResult().getPayuMoneyId());
            paymentReceipt.setModeOfPayment(paymentResponseStatus.getResult().getMode());
            paymentReceipt.setPgType(paymentResponseStatus.getResult().getPgTYPE());

        }

        Log.d(TAG, gson.toJson(paymentReceipt));
        Call<Void> call = RestClient.getRestService(this).addReceipts(localStorage.getToken(), paymentReceipt);
        boolean finalIsSuccess = isSuccess;
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, response.code() + "");
                if (response.code() == 200) {

                    if (finalIsSuccess) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("backIntent", "report");
                        startActivity(intent);
                        finish();
                    }
                } else if (response.code() == 401 || response.code() == 403) {
                    localStorage.logoutUser();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "Receipts addition failed ");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "Receipts addition failed " + t.getMessage());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        userEmail = email_et.getText().toString().trim();
        userMobile = mobile_et.getText().toString().trim();
        if (validateDetails(userEmail, userMobile)) {
            showConfirmDialog(v);
        }
    }

    private void proceedToPayment(View v) {
        userEmail = email_et.getText().toString().trim();
        userMobile = mobile_et.getText().toString().trim();
        switch (v.getId()) {
            case R.id.pay_now_button:
                payNowButton.setEnabled(false);
                launchPayUMoneyFlow();
                break;
            case R.id.logout_button:
                PayUmoneyFlowManager.logoutUser(getApplicationContext());
                logoutBtn.setVisibility(GONE);
                break;
        }

    }

    private void showConfirmDialog(View view) {
        final Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.closing_dialog);
        dialog.setTitle("Confirm Payment!");

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.text_message);
        text.setText("Convenience fee applicable on online payments by payment gateway, please check the final amount before paying through Credit/Debit Cards, NetBanking etc.");


        Button okButton = dialog.findViewById(R.id.dialogButtonOK);
        Button cancelButton = dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                proceedToPayment(view);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void initListeners() {
        email_et.addTextChangedListener(new EditTextInputWatcher(email_til));
        mobile_et.addTextChangedListener(new EditTextInputWatcher(mobile_til));


        radioGroup_color_theme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                mAppPreference.setOverrideResultScreen(true);

                switch (i) {
                    case R.id.radio_btn_theme_default:
                        AppPreference.selectedTheme = -1;
                        break;
                    case R.id.radio_btn_theme_pink:
                        AppPreference.selectedTheme = R.style.AppTheme_pink;
                        break;
                    case R.id.radio_btn_theme_grey:
                        AppPreference.selectedTheme = R.style.AppTheme_Grey;
                        break;
                    case R.id.radio_btn_theme_purple:
                        AppPreference.selectedTheme = R.style.AppTheme_purple;
                        break;
                    case R.id.radio_btn_theme_green:
                        AppPreference.selectedTheme = R.style.AppTheme_Green;
                        break;
                    default:
                        AppPreference.selectedTheme = -1;
                        break;
                }
            }
        });

        radioGroup_select_env.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radio_btn_sandbox:
                        selectSandBoxEnv();
                        break;
                    case R.id.radio_btn_production:
                        selectProdEnv();
                        break;
                    default:
                        selectSandBoxEnv();
                        break;
                }
            }
        });
    }

    *//**
     * This fucntion checks if email and mobile number are valid or not.
     *
     * @param email  email id entered in edit text
     * @param mobile mobile number entered in edit text
     * @return boolean value
     *//*
    public boolean validateDetails(String email, String mobile) {
        email = email.trim();
        mobile = mobile.trim();

        if (TextUtils.isEmpty(mobile)) {
            setErrorInputLayout(mobile_et, getString(R.string.err_phone_empty), mobile_til);
            return false;
        } else if (!isValidPhone(mobile)) {
            setErrorInputLayout(mobile_et, getString(R.string.err_phone_not_valid), mobile_til);
            return false;
        } else if (TextUtils.isEmpty(email)) {
            setErrorInputLayout(email_et, getString(R.string.err_email_empty), email_til);
            return false;
        } else if (!isValidEmail(email)) {
            setErrorInputLayout(email_et, getString(R.string.email_not_valid), email_til);
            return false;
        } else {
            return true;
        }
    }

    *//**
     * This function prepares the data for payment and launches payumoney plug n play sdk
     *//*
    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Thanks for your payment");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Smartway Payment");

        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0.00;
        try {
            amount = Double.parseDouble(amount_et.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        Gson gson = new Gson();
        String loginInfoStr = localStorage.getMemberTenantInfo();
        LoginInfo _info = gson.fromJson(loginInfoStr, LoginInfo.class);
        String txnId = UUID.randomUUID().toString();
        String phone = mobile_til.getEditText().getText().toString().trim();
        String productName = "Payment for Flat no " + _info.getFlatNo();
        Profile profile = new Gson().fromJson(localStorage.getProfile(), Profile.class);
        String firstName = _info.getUserName();
        if (profile != null) {
            firstName = profile.getName();
        }
        String email = email_til.getEditText().getText().toString().trim();
        AppEnvironment appEnvironment = ((MyApplication) getApplication()).getAppEnvironment();
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        builder.setAmount(df2.format(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentActivity.this, AppPreference.selectedTheme, mAppPreference.isOverrideResultScreen());
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentActivity.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
            }

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            payNowButton.setEnabled(true);
        }
    }

    *//**
     * Thus function calculates the hash for transaction
     *
     * @param paymentParam payment params of transaction
     * @return payment params along with calculated merchant hash
     *//*
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {
        AppEnvironment appEnvironment = ((MyApplication) getApplication()).getAppEnvironment();
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");
        stringBuilder.append(appEnvironment.salt());
        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    public static class EditTextInputWatcher implements TextWatcher {

        private TextInputLayout textInputLayout;

        EditTextInputWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }


    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.closing_dialog);
        dialog.setTitle("Confirm Closing App !");

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.text_message);
        text.setText("You are about to close your app. Do you really want to proceed ?");


        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        Button cancelButton = dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showExitDialog();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Use Back button on top to go back", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();


    }

    private void showExitDialog() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
        System.exit(0);
    }*/
}