package com.quintus.labs.grocerystore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.AddressDetails;
import com.quintus.labs.grocerystore.model.DelExecDetails;
import com.quintus.labs.grocerystore.model.OrderDetails;
import com.quintus.labs.grocerystore.model.OrderDetailsData;
import com.quintus.labs.grocerystore.model.OrderItem;
import com.quintus.labs.grocerystore.model.ProductsData;
import com.quintus.labs.grocerystore.util.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
    OrderDetailsData orderItemList;
    DelExecDetails delExecDetails;
    List<ProductsData> productsData;
    AddressDetails addressDetails;
    Context context;


    public OrderItemAdapter(OrderDetailsData orderItemList, DelExecDetails delExecDetails,List<ProductsData> productsData,AddressDetails addressDetails,Context context) {
        this.orderItemList = orderItemList;
        this.delExecDetails = delExecDetails;
        this.productsData = productsData;
        this.addressDetails = addressDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderdetails_item, parent, false);


        return new OrderItemAdapter.MyViewHolder(itemView);


    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final OrderItemAdapter.MyViewHolder holder, final int position) {
        final OrderDetailsData order = orderItemList;
      final  DelExecDetails delExecDetailsdata = delExecDetails;
      final  ProductsData productsDataList= productsData.get(position);
      final   AddressDetails addressDetailsData=addressDetails;
        holder.order_id.setText("#" + order.getOrderNo());

            holder.order_title.setText(productsDataList.getProduct().getName());

        holder.order_quantity.setText(String.valueOf(productsDataList.getQuantity()));
        holder.order_unitprice.setText(productsDataList.getProduct().getCurrency().getSymbol()+" "+productsDataList.getProduct().getPrice());
        holder.total_price.setText(productsDataList.getProduct().getCurrency().getSymbol() + " " + productsDataList.getPrice());
        Picasso.get()
                .load(productsDataList.getProduct().getImages().get(0).getImage())
                .into(holder.order_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Error : ", e.getMessage());
                    }
                });


    }

    @Override
    public int getItemCount() {
        return productsData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_id, order_title, order_quantity, order_unitprice, total_price;
        ImageView order_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            order_id = itemView.findViewById(R.id.order_id);
            order_title = itemView.findViewById(R.id.order_title);
            order_quantity = itemView.findViewById(R.id.order_quantity);
            order_unitprice = itemView.findViewById(R.id.order_unitprice);
            total_price = itemView.findViewById(R.id.total_price);
            order_image = itemView.findViewById(R.id.order_image);

        }
    }
}

