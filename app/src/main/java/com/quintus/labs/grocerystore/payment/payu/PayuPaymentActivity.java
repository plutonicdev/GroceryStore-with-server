package com.quintus.labs.grocerystore.payment.payu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.quintus.labs.grocerystore.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PayuPaymentActivity extends AppCompatActivity {

    private static final String TAG ="PayuPaymentActivity==>" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payu_payment);
        String hashSequence = "BFAtqq|1234ABCSDE|100|Grocery Store|Santosh Kumar Dash|santosh@gmail.com|||||||||||CfpTwY3g";
        String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount("100")                          // Payment amount
                .setTxnId("1234ABCSDE")                                             // Transaction ID
                .setPhone("9778178337")                                           // User Phone number
                .setProductName("Grocery Store")                   // Product Name or description
                .setFirstName("Santosh Dash")                              // User First name
                .setEmail("santosh@gmail.com")                                            // User Email ID
                .setsUrl("https://megagrocerystore.000webhostapp.com/admin")                    // Success URL (surl)
                .setfUrl("https://megagrocerystore.000webhostapp.com/admin")                     //Failure URL (furl)
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
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey("BFAtqq")                        // Merchant key
                .setMerchantId("CfpTwY3g");             // Merchant ID


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
        // Invoke the following function to open the checkout page.
        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam,
                this, R.style.AppTheme_default, isOverrideResultScreen);
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
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
                    //Success Transaction
                } else{
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            }  else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }
}