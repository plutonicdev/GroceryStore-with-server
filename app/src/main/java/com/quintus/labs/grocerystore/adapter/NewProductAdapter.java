package com.quintus.labs.grocerystore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.BaseActivity;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.activity.ProductViewActivity;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.interfaces.AddorRemoveCallbacks;
import com.quintus.labs.grocerystore.model.AddToCart;
import com.quintus.labs.grocerystore.model.Cart;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.PopularProductsResult;
import com.quintus.labs.grocerystore.model.ProductDetail;
import com.quintus.labs.grocerystore.util.Utils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.quintus.labs.grocerystore.activity.BaseActivity.TAG;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewHolder> {

    List<PopularProductsResult> productList;
    Context context;
    String Tag;

    LocalStorage localStorage;
    Gson gson;
    List<ProductDetail> cartList = new ArrayList<>();

    String _quantity, _price, _attribute, _subtotal;
    String token;
    View changeProgressBar;



    public NewProductAdapter(List<PopularProductsResult> productList, Context context, String tag) {
        this.productList = productList;
        this.context = context;
        Tag = tag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        if (Tag.equalsIgnoreCase("Home")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_new_home_products, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_new_products, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

     final   PopularProductsResult   product = productList.get(position);
        localStorage = new LocalStorage(context);
        gson = new Gson();
       cartList = ((BaseActivity) context).getCartList();
       holder.quantity.setText("0");
        token=localStorage.getApiKey();

        holder.title.setText(product.getName());
        if (Float.parseFloat(product.getPrice()) < Float.parseFloat(product.getMrp())) {
            holder.price.setText(product.getPrice());
            holder.org_price.setText(product.getMrp());
            holder.org_price.setPaintFlags(holder.org_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            holder.price.setText(product.getPrice());
            holder.org_price.setVisibility(View.GONE);
        }
        holder.currency.setText(product.getCurrency().getSymbol());

        Picasso.get().load(product.getImages().get(0).getImage()).error(R.drawable.no_image).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
            }
        });

        if (!cartList.isEmpty()) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getId().equals(product.getId())) {
                    holder.shopNow.setVisibility(View.GONE);
                    holder.quantity_ll.setVisibility(View.VISIBLE);
                    holder.quantity.setText(String.valueOf(cartList.get(i).getCount()));
                    Log.d("Tag : ", cartList.get(i).getId() + "-->" + product.getId());
                }
            }
        }

        if( holder.quantity.getText().toString().equalsIgnoreCase("0")){
            holder.shopNow.setVisibility(View.VISIBLE);
            holder.quantity_ll.setVisibility(View.GONE);
        }
        holder.shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shopNow.setVisibility(View.GONE);
                holder.quantity_ll.setVisibility(View.VISIBLE);
                _quantity = holder.quantity.getText().toString();
                int qty=Integer.parseInt(_quantity)+1;
                holder.quantity.setText(String.valueOf(qty));
                if (context instanceof MainActivity) {

                    int prouct_id = product.getId();
                    AddToCart addtoCart = new AddToCart(1,prouct_id,null,true);
                    addingToCart(addtoCart,position,"plus");

                }
            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prouct_id = product.getId();
                AddToCart addtoCart = new AddToCart(1,prouct_id,null,true);
                addingToCart(addtoCart,position,"plus");
                _quantity = holder.quantity.getText().toString();
                int qty=Integer.parseInt(_quantity)+1;
                holder.quantity.setText(String.valueOf(qty));



            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int prouct_id = product.getId();
                AddToCart addtoCart = new AddToCart(1,prouct_id,null,false);
                addingToCart(addtoCart,position,"minus");
                _quantity = holder.quantity.getText().toString();
                int qty=Integer.parseInt(_quantity)-1;
                if(qty<1){
                    holder.shopNow.setVisibility(View.VISIBLE);
                    holder.quantity_ll.setVisibility(View.GONE);
                }
                holder.quantity.setText(String.valueOf(qty));


            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductViewActivity.class);
                intent.putExtra("id", product.getId()+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return productList.size();

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, attribute, currency, price, org_price, shopNow;
        ProgressBar progressBar;
        LinearLayout quantity_ll;
        TextView plus, minus, quantity;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            attribute = itemView.findViewById(R.id.product_attribute);
            price = itemView.findViewById(R.id.product_price);
            org_price = itemView.findViewById(R.id.original_price);
            currency = itemView.findViewById(R.id.product_currency);
            shopNow = itemView.findViewById(R.id.shop_now);
            progressBar = itemView.findViewById(R.id.progressbar);
            quantity_ll = itemView.findViewById(R.id.quantity_ll);
            quantity = itemView.findViewById(R.id.quantity);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            cardView = itemView.findViewById(R.id.card_view);
            changeProgressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
    private void hideProgressDialog() {
        changeProgressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        changeProgressBar.setVisibility(View.VISIBLE);
    }

    private void addingToCart( AddToCart addtoCart,final  int position,final String plus) {


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
                            ((AddorRemoveCallbacks) context).onAddProduct();
                            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();

                        } else {
                            ((AddorRemoveCallbacks) context).onRemoveProduct();
                            Toast.makeText(context, "Successfully removed", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(context, "please try after sometime", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "please try after sometime", Toast.LENGTH_SHORT).show();
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
