package com.quintus.labs.grocerystore.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.BaseActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.AddAddress;
import com.quintus.labs.grocerystore.model.InitiatePayment;
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
   String payment_type="cod";
   int payment_id;
   String payment_ref_No;

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
        progress = view.findViewById(R.id.progress_bar);
        totalAmount=localStorage.getTotalAmount();
      //  Double amount = ((BaseActivity) getActivity()).getTotalPrice();
       pay.append(totalAmount + "");

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
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
//                    ft.replace(R.id.content_frame, new PaymentFragment());
//                    ft.commit();
                    payment_type="cod";

                }else{
                    payment_type="online";
                }
                // Toast.makeText(getContext(),radioButton.getText()+"",Toast.LENGTH_LONG).show();
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
        Call<InitiatePayment> call = RestClient.getRestService(getContext()).initatePayment(token,totalAmount);
        call.enqueue(new Callback<InitiatePayment>() {
            @Override
            public void onResponse(Call<InitiatePayment> call, Response<InitiatePayment> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 201) {
                        payment_id=response.body().getId();
                        payment_ref_No=response.body().getPaymentRefNo();

//                        if(payment_type.equalsIgnoreCase("cod")){
//                            String status = "success";
//                            String payment_response_id = "";
//                            String payment_type = "cod";
//                           updatePayment(status,payment_response_id,payment_type) ;
//                        }

                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<InitiatePayment> call, Throwable t) {

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
