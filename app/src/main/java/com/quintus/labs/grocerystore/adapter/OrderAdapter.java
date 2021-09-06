package com.quintus.labs.grocerystore.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.AddressDetails;
import com.quintus.labs.grocerystore.model.DelExecDetails;
import com.quintus.labs.grocerystore.model.OrderDetails;
import com.quintus.labs.grocerystore.model.OrderDetailsData;
import com.quintus.labs.grocerystore.model.OrdersResult;
import com.quintus.labs.grocerystore.model.OrderItem;
import com.quintus.labs.grocerystore.model.OrdersResult;
import com.quintus.labs.grocerystore.model.ProductsData;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.VoucherList;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.quintus.labs.grocerystore.activity.BaseActivity.TAG;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    List<OrdersResult> orderList;
    OrderDetailsData orderDetails;
    DelExecDetails delExecDetails;
    List<ProductsData> productsData;
    AddressDetails addressDetails;
    OrderItemAdapter orderItemAdapter;
    Context context;
    String Tag;
    RecyclerView recyclerView;
    LocalStorage localStorage;
    Gson gson;
    String token;
    View changeProgressBar;
    TextView total,payable,payable_price_dec;
    LinearLayout ll_total;

    public OrderAdapter(List<OrdersResult> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        localStorage = new LocalStorage(context);
        gson = new Gson();
        token = localStorage.getApiKey();

        final OrdersResult order = orderList.get(position);
        holder.order_no.setText("#" + order.getOrderNo());
        holder.order_date.setText(order.getCreatedDate());
        holder.total_price.setText(order.getTotal());
        holder.status.setText(order.getStatus());
        holder.delivery_date.setText(order.getDeliveryDate());
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderItemModal(order.getId());
            }
        });


   }

    private void openOrderItemModal(int id) {
        final Dialog dialog = new Dialog(context, R.style.FullScreenDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.orderdetails_dialog);


        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        recyclerView = dialog.findViewById(R.id.order_list);
        total = dialog.findViewById(R.id.total_price);
        payable = dialog.findViewById(R.id.payable_price);
        payable_price_dec = dialog.findViewById(R.id.payable_price_dec);
        ll_total = dialog.findViewById(R.id.ll_total);

        showProgressDialog();
        Call<OrderDetails> call = RestClient.getRestService(context).getSingleOrderDetails(token,id);
        call.enqueue(new Callback<OrderDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                orderDetails = response.body().getOrderDetails();
                delExecDetails=response.body().getDelExecDetails();
                productsData=response.body().getProducts();
                addressDetails=response.body().getAddressDetails();
                total.setText(orderDetails.getPaymentMode());
                if(orderDetails.getPaymentMode().equalsIgnoreCase("online")){
                    payable_price_dec.setText("Total Price Paid : ");
                }else{
                    payable_price_dec.setText("Total Payable Price : ");
                }
                if(orderDetails.getPaymentStatus().equalsIgnoreCase("failure")){
                    ll_total.setVisibility(View.GONE);
                }
                payable.setText(productsData.get(0).getProduct().getCurrency().getSymbol()+" "+ orderDetails.getPayableAmount());
                orderItemAdapter = new OrderItemAdapter(orderDetails,delExecDetails,productsData,addressDetails, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(orderItemAdapter);

                hideProgressDialog();

            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Log.d(TAG, "errorResponse:==>" + t.getMessage());
                hideProgressDialog();
            }
        });


        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {

        return orderList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  order_no,total_price,status,delivery_date,order_date;
        Button viewDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            order_no = itemView.findViewById(R.id.order_no);
            total_price = itemView.findViewById(R.id.total_price);
            delivery_date = itemView.findViewById(R.id.delivery_date);
            status = itemView.findViewById(R.id.status);
            order_date = itemView.findViewById(R.id.order_date);
            viewDetails = itemView.findViewById(R.id.view_details);
            changeProgressBar = itemView.findViewById(R.id.progress_bar);


        }
    }

    private void hideProgressDialog() {
        changeProgressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        changeProgressBar.setVisibility(View.VISIBLE);
    }
}
