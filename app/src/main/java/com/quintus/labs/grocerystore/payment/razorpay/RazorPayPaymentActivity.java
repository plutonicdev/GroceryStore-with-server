package com.quintus.labs.grocerystore.payment.razorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quintus.labs.grocerystore.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class RazorPayPaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "RazorPay==>";
    Checkout checkout;

    EditText name, email, phNo,amount;
    Button submit;
    private String razorpayKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phNo=findViewById(R.id.phNo);
        amount=findViewById(R.id.amount);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals(null) || name.getText().toString().equals("")){
                    name.setError("Please Fillup");
                }else if(email.getText().toString().equals(null) || email.getText().toString().equals("")){
                    email.setError("Please Fillup");
                }else if(phNo.getText().toString().equals(null) || phNo.getText().toString().equals("")){
                    phNo.setError("Please Fillup");
                }else if(phNo.getText().toString().length()!=10 ){
                    phNo.setError("Please Enter 10 digit phone number");
                }else if(amount.getText().toString().equals(null) || amount.getText().toString().equals("")){
                    amount.setError("Please Fillup");
                }else if(Integer.parseInt(amount.getText().toString())==0){
                    amount.setError("Amount should be greater than 0"); //Razorpay min amount is 1 Rs.
                }else{
                    //you have to convert Rs. to Paisa using multiplication of 100
                    String convertedAmount=String.valueOf(Integer.parseInt(amount.getText().toString())*100);

                    startPayment(name.getText().toString(),email.getText().toString(),phNo.getText().toString(),convertedAmount);
                }
            }
        });



    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }


    public void startPayment(String name, String email, String phNo, String convertedAmount){
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        razorpayKey="rzp_test_IouKPyYlXmQEK3"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id
        checkout = new Checkout();
        checkout.setKeyID(razorpayKey);
        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Razorpay Payment Test");
            options.put("currency", "INR");
            options.put("amount", convertedAmount);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phNo);
            options.put("prefill", preFill);

            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}