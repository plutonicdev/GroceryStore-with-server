package com.quintus.labs.grocerystore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.CartActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.AddToCart;
import com.quintus.labs.grocerystore.model.Cart;
import com.quintus.labs.grocerystore.model.ProductDetail;
import com.quintus.labs.grocerystore.util.Utils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    List<ProductDetail> cartList;
    Context context;
    int pQuantity = 1;
    String _subtotal, _price, _quantity;
    LocalStorage localStorage;
    Gson gson;
    View changeProgressBar;
    String token;
    TextView quantity;

    public CartAdapter(List<ProductDetail> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final ProductDetail cart = cartList.get(position);
        localStorage = new LocalStorage(context);
        gson = new Gson();
        token = localStorage.getApiKey();
        holder.title.setText(cart.getProduct().getName());
        //  holder.attribute.setText(cart.getAttribute());
        _price = cart.getProduct().getPrice();
        _quantity = String.valueOf(cart.getCount());

        quantity.setText(_quantity);
        holder.price.setText(_price);
        holder.currency.setText(cart.getProduct().getCurrency().getSymbol());
        _subtotal = String.valueOf(Double.parseDouble(_price) * Integer.parseInt(_quantity));
        holder.subTotal.setText(_subtotal);
        Log.d("Cart Image==>", cart.getProduct().getImages().get(0).getImage());
        Picasso.get()
                .load(Utils.ProductImage + cart.getProduct().getImages().get(0).getImage())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Error : ", e.getMessage());
                    }
                });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pQuantity = Integer.parseInt(quantity.getText().toString());
                if (pQuantity >= 1) {
                    int prouct_id = cart.getProduct().getId();
                    String price=cart.getProduct().getPrice();
                    AddToCart addtoCart = new AddToCart(1, prouct_id, null, true);
                    addingToCart(addtoCart, "plus",price,position);


//                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
//                    total_item++;
//                    holder.quantity.setText(total_item + "");


//                    for (int i = 0; i < cartList.size(); i++) {
//
//                        if (cartList.get(i).getId().equalsIgnoreCase(cart.getId())) {
//
//                            // Log.d("totalItem", total_item + "");
//
//                            _subtotal = String.valueOf(Double.parseDouble(holder.price.getText().toString()) * total_item);
//                            cartList.get(i).setQuantity(holder.quantity.getText().toString());
//                            cartList.get(i).setSubTotal(_subtotal);
//                            holder.subTotal.setText(_subtotal);
//                            String cartStr = gson.toJson(cartList);
//                            //Log.d("CART", cartStr);
//                            localStorage.setCart(cartStr);
//                            ((CartActivity) context).updateTotalPrice();
//                        }
//                    }
                }


            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pQuantity = Integer.parseInt(quantity.getText().toString());
                if (pQuantity != 1) {

                    int prouct_id = cart.getProduct().getId();
                    String price=cart.getProduct().getPrice();
                    AddToCart addtoCart = new AddToCart(1, prouct_id, null, false);
                    addingToCart(addtoCart, "minus",price,position);


//                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
//                    total_item--;
//                    holder.quantity.setText(total_item + "");


//                    for (int i = 0; i < cartList.size(); i++) {
//                        if (cartList.get(i).getId().equalsIgnoreCase(cart.getId())) {
//
//                            //holder.quantity.setText(total_item + "");
//                            //Log.d("totalItem", total_item + "");
//                            _subtotal = String.valueOf(Double.parseDouble(holder.price.getText().toString()) * total_item);
//                            cartList.get(i).setQuantity(holder.quantity.getText().toString());
//                            cartList.get(i).setSubTotal(_subtotal);
//                            holder.subTotal.setText(_subtotal);
//                            String cartStr = gson.toJson(cartList);
//                            //Log.d("CART", cartStr);
//                            localStorage.setCart(cartStr);
//                            ((CartActivity) context).updateTotalPrice();
//
//                        }
//                    }

                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prouct_id = cart.getProduct().getId();
                AddToCart addtoCart = new AddToCart(cart.getCount(), prouct_id, null, false);

                removefromCart(addtoCart, position);


//                cartList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, cartList.size());
//                Gson gson = new Gson();
//                String cartStr = gson.toJson(cartList);
//                Log.d("CART", cartStr);
//                localStorage.setCart(cartStr);
//                ((CartActivity) context).updateTotalPrice();


            }
        });


    }

    @Override
    public int getItemCount() {

        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView;
        TextView offer, currency, price, attribute, addToCart, subTotal;
        Button plus, minus, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            quantity = itemView.findViewById(R.id.quantity);
            currency = itemView.findViewById(R.id.product_currency);
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            delete = itemView.findViewById(R.id.cart_delete);
            subTotal = itemView.findViewById(R.id.sub_total);
            price = itemView.findViewById(R.id.product_price);
            changeProgressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    private void removefromCart(AddToCart addtoCart, final int position) {
        showProgressDialog();

        Call<AddToCart> call = RestClient.getRestService(context).addToCart(token, addtoCart);
        call.enqueue(new retrofit2.Callback<AddToCart>() {
            @Override
            public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {

                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    AddToCart addToCartResponse = response.body();
                    if (response.code() == 200) {

                        cartList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartList.size());
                        Gson gson = new Gson();
                        String cartStr = gson.toJson(cartList);
                        Log.d("CART", cartStr);
                        localStorage.setCart(cartStr);
                        ((CartActivity) context).updateTotalPrice();


                    } else {
                        Toast.makeText(context, "please try after sometime", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "please try after sometime", Toast.LENGTH_LONG).show();
                }


                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<AddToCart> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                hideProgressDialog();
            }
        });

    }

    private void hideProgressDialog() {
        changeProgressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        changeProgressBar.setVisibility(View.VISIBLE);
    }


    private void addingToCart(AddToCart addtoCart, final String plus,final String price,final int position) {


        showProgressDialog();
        Call<AddToCart> call = RestClient.getRestService(context).addToCart(token, addtoCart);
        call.enqueue(new retrofit2.Callback<AddToCart>() {
            @Override
            public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {

                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    AddToCart addToCartResponse = response.body();
                    if (response.code() == 200) {
                        if (plus.equalsIgnoreCase("plus")) {
                            Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show();
                            int total_item = Integer.parseInt(quantity.getText().toString());
                            total_item++;
                            quantity.setText(total_item + "");
                            _subtotal = String.valueOf(Double.parseDouble(price) * total_item);
                        } else {
                            Toast.makeText(context, "Successfully removed", Toast.LENGTH_LONG).show();
                            int total_item = Integer.parseInt(quantity.getText().toString());
                            total_item--;
                            quantity.setText(total_item + "");
                            _subtotal = String.valueOf(Double.parseDouble(price) * total_item);
                        }

//                        cartList.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, cartList.size());
//                        Gson gson = new Gson();
//                        String cartStr = gson.toJson(cartList);
//                        Log.d("CART", cartStr);
//                        localStorage.setCart(cartStr);
                        ((CartActivity) context).updateTotalPrice();


                    } else {
                        Toast.makeText(context, "please try after sometime", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "please try after sometime", Toast.LENGTH_LONG).show();
                }


                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<AddToCart> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                hideProgressDialog();
            }
        });


    }
}

