package com.quintus.labs.grocerystore.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.CartActivity;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.CheckoutDetails;
import com.quintus.labs.grocerystore.model.InitiatePayment;
import com.quintus.labs.grocerystore.model.Total;
import com.quintus.labs.grocerystore.model.UpdatePayment;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.payment.payu.PayuPaymentActivity;
import com.quintus.labs.grocerystore.payment.razorpay.RazorPayPaymentActivity;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    LocalStorage localStorage;
    Gson gson;
    String token;
    View progress;
    RadioGroup paymentGroup;
    FrameLayout cardFrame;
    RadioButton card, cash;
    LinearLayout payll;
    TextView pay;
    String totalAmount;
    String payment_type = "cod";
    int payment_id;
    String payment_ref_No;
    String address_id;
    String name, email, mobile;



    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_payment, container, false);
        paymentGroup = view.findViewById(R.id.payment_group);
        card = view.findViewById(R.id.card_payment);
        cash = view.findViewById(R.id.cash_on_delivery);
        cardFrame = view.findViewById(R.id.card_frame);
        payll = view.findViewById(R.id.pay_ll);
        pay = view.findViewById(R.id.total_pay);
        localStorage = new LocalStorage(getContext());
        gson = new Gson();
        token = localStorage.getApiKey();
        address_id = localStorage.getAddressId();
        progress = view.findViewById(R.id.progress_bar);
        totalAmount = localStorage.getTotalAmount();
        //  Double amount = ((BaseActivity) getActivity()).getTotalPrice();
        pay.append(totalAmount + "");
        User user = gson.fromJson(localStorage.getUserLogin(), User.class);
        name = user.getName();
        email = user.getEmail();
        mobile = user.getPhone();


        payll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePayment();
            }
        });
        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                if (radioButton.getId() == R.id.cash_on_delivery) {

                    payment_type = "cod";

                } else {
                    payment_type = "online";
                }

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Payment");
    }


    private void initiatePayment() {
        showProgressDialog();
        Total total = new Total(totalAmount,payment_type);
        Call<InitiatePayment> call = RestClient.getRestService(getContext()).initiatePayment(token, total);
        call.enqueue(new Callback<InitiatePayment>() {
            @Override
            public void onResponse(Call<InitiatePayment> call, Response<InitiatePayment> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 201) {
                        InitiatePayment initiatePayment = response.body();
                        payment_id = initiatePayment.getServerResp().getId();
                        payment_ref_No = initiatePayment.getServerResp().getPaymentRefNo();

                        if (payment_type.equalsIgnoreCase("cod")) {
                            String status = "success";
                            String payment_response_id = "";
                            String payment_type = "cod";
                            UpdatePayment updatePayment = new UpdatePayment(status, payment_response_id, payment_type);
                            updatePaymentData(updatePayment);
                        } else{
                            if(initiatePayment.getPgDetails().getName().equalsIgnoreCase("RazorPay")) {

                                Intent intent = new Intent(getContext(), RazorPayPaymentActivity.class);
                                intent.putExtra("payment_id", payment_id);
                                intent.putExtra("key", initiatePayment.getPgDetails().getKey());
                                startActivity(intent);
                                getActivity().finish();
                            } else if(initiatePayment.getPgDetails().getName().equalsIgnoreCase("PayU")){
                                String serverCalculatedHash= initiatePayment.getPayuResp().getHashh();
                                String  amount= initiatePayment.getPayuResp().getAmount();
                                String txnid=initiatePayment.getPayuResp().getTxnid();
                                String productinfo=initiatePayment.getPayuResp().getProductinfo();
                                String merchant_key =initiatePayment.getPayuResp().getMerchantKey();
                                String  mode= initiatePayment.getPgDetails().getMode();

                                Intent intent = new Intent(getContext(), PayuPaymentActivity.class);
                                intent.putExtra("payment_id", payment_id);
                                intent.putExtra("serverCalculatedHash", serverCalculatedHash);
                                intent.putExtra("amount", amount);
                                intent.putExtra("txnid", txnid);
                                intent.putExtra("productinfo", productinfo);
                                intent.putExtra("merchant_key", merchant_key);
                                intent.putExtra("mode", mode);
                                startActivity(intent);
                                getActivity().finish();



                            }



                        }

                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), CartActivity.class));
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<InitiatePayment> call, Throwable t) {
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), CartActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

    }

    private void updatePaymentData(UpdatePayment updatePayment) {
        showProgressDialog();
        Call<UpdatePayment> call = RestClient.getRestService(getContext()).updatePayment(token, String.valueOf(payment_id), updatePayment);
        call.enqueue(new Callback<UpdatePayment>() {
            @Override
            public void onResponse(Call<UpdatePayment> call, Response<UpdatePayment> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 200) {
                        CheckoutDetails checkout = new CheckoutDetails(String.valueOf(response.body().getId()), response.body().getStatus(), address_id, totalAmount, "", "", "");
                        checkoutData(checkout);


                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), CartActivity.class));
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<UpdatePayment> call, Throwable t) {
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), CartActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

    }

    private void checkoutData(CheckoutDetails checkoutDetails) {
        showProgressDialog();
        Call<CheckoutDetails> call = RestClient.getRestService(getContext()).createOrder(token, checkoutDetails);
        call.enqueue(new Callback<CheckoutDetails>() {
            @Override
            public void onResponse(Call<CheckoutDetails> call, Response<CheckoutDetails> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 201) {
                        Toast.makeText(getContext(), "Order placed Successfully !", Toast.LENGTH_SHORT).show();
                        localStorage.deleteCart();
                        showCustomDialog();


                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), CartActivity.class));
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<CheckoutDetails> call, Throwable t) {
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), CartActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

    }


    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }


    private void showCustomDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        // Include dialog.xml file
        dialog.setContentView(R.layout.success_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        // Set dialog title

        dialog.show();
    }


}
