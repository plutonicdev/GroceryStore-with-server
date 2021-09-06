package com.quintus.labs.grocerystore.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.BaseActivity;
import com.quintus.labs.grocerystore.activity.CartActivity;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.adapter.CheckoutCartAdapter;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.Cart;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.Order;
import com.quintus.labs.grocerystore.model.OrderItem;
import com.quintus.labs.grocerystore.model.OrdersResult;
import com.quintus.labs.grocerystore.model.PlaceOrder;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.Voucher;
import com.quintus.labs.grocerystore.model.VoucherResult;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.util.ArrayList;
import java.util.List;

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
public class ConfirmFragment extends Fragment {
    LocalStorage localStorage;
    List<Cart> cartList = new ArrayList<>();
    Gson gson;
    RecyclerView recyclerView;
    CheckoutCartAdapter adapter;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    TextView back, order;
    TextView total, shipping, totalAmount, discount, payable;
    Double _total, _shipping, _totalAmount;
    ProgressDialog progressDialog;
    List<Order> orderList = new ArrayList<>();
    List<OrderItem> orderItemList = new ArrayList<>();
    PlaceOrder confirmOrder;
    String orderNo, address;
    String id;
    OrderItem orderItem = new OrderItem();
    String token;
    User user;
    View progress;
    Double totalPrice,originalPrice;
    EditText et_code;
    Button code_button,code_button_disabled;
    String voucher_code;
    TextView remove_coupon;


    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        localStorage = new LocalStorage(getContext());
        recyclerView = view.findViewById(R.id.cart_rv);
        totalAmount = view.findViewById(R.id.total_amount);
        total = view.findViewById(R.id.total);
        discount = view.findViewById(R.id.discount_amount);
        payable = view.findViewById(R.id.payable_amount);
        shipping = view.findViewById(R.id.shipping_amount);
        back = view.findViewById(R.id.back);
        order = view.findViewById(R.id.place_order);
        et_code = view.findViewById(R.id.et_code);
        remove_coupon = view.findViewById(R.id.remove_coupon);
        code_button = view.findViewById(R.id.code_button);
        code_button_disabled = view.findViewById(R.id.code_button_disabled);
        progressDialog = new ProgressDialog(getContext());
        gson = new Gson();
        token = localStorage.getApiKey();
        progress = view.findViewById(R.id.progress_bar);

        remove_coupon.setVisibility(View.GONE);

        getCartDetails();

        remove_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code_button.setVisibility(View.VISIBLE);
                code_button_disabled.setVisibility(View.GONE);
                et_code.setText("");
                Toast.makeText(getContext(), "Coupon Removed Successfully.", Toast.LENGTH_SHORT).show();
                payable.setText("\u20B9" + " " +originalPrice);
                discount.setText("\u20B9" + " " + "0.0");
                totalPrice=originalPrice;
                remove_coupon.setVisibility(View.GONE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // placeUserOrder();
                localStorage.setTotalAmount(String.valueOf(totalPrice));
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                ft.replace(R.id.content_frame, new PaymentFragment());
                ft.commit();
            }
        });

        code_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voucher_code = et_code.getText().toString().trim();
                if (voucher_code != null) {
                    Voucher voucher = new Voucher(String.valueOf(totalPrice), voucher_code);
                    checkCoupon(voucher);

                } else {
                    Toast.makeText(getContext(), "Please Enter a Coupon Code.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setUpCartRecyclerview();
        return view;
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

    private void setUpCartRecyclerview() {
//        recyclerView.setHasFixedSize(true);
//        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(recyclerViewlayoutManager);
//        adapter = new CheckoutCartAdapter(cartList, getContext());
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Confirm");
    }

    private void getCartDetails() {

        showProgressDialog();
        Call<CartDetails> call = RestClient.getRestService(getContext()).getCartList(token);
        call.enqueue(new Callback<CartDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CartDetails> call, Response<CartDetails> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    CartDetails cartDetails = response.body();
                    if (response.code() == 200) {
                        total.setText(String.valueOf(cartDetails.getTotalItems()));
                        totalAmount.setText("\u20B9" + " " + cartDetails.getProductTotalPrice());
                        totalPrice = cartDetails.getProductTotalPrice();
                        originalPrice = cartDetails.getProductTotalPrice();
                        payable.setText("\u20B9" + " " + cartDetails.getProductTotalPrice());
                        discount.setText("\u20B9" + " " + "0.0");


                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<CartDetails> call, Throwable t) {
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

    private void checkCoupon(Voucher voucher) {
        showProgressDialog();
        Call<VoucherResult> call = RestClient.getRestService(getContext()).checkVoucher(token, voucher);
        call.enqueue(new Callback<VoucherResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<VoucherResult> call, Response<VoucherResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    VoucherResult cartDetails = response.body();
                    if (response.code() == 200) {
                        code_button.setVisibility(View.GONE);
                        code_button_disabled.setVisibility(View.VISIBLE);
                        remove_coupon.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Coupon Applied Successfully.", Toast.LENGTH_SHORT).show();
                        totalPrice = Double.parseDouble(String.valueOf(cartDetails.getCartPrice()));
                        payable.setText("\u20B9" + " " + cartDetails.getCartPrice());
                        discount.setText("\u20B9" + " " + cartDetails.getDiscountAmount());

                    } else if (response.code() == 400) {
                        Toast.makeText(getContext(), "Invalid Coupon", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<VoucherResult> call, Throwable t) {
                Toast.makeText(getContext(), "Please Try After Sometime.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }
}
