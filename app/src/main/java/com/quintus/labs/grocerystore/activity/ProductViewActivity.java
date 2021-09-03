package com.quintus.labs.grocerystore.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.helper.Converter;
import com.quintus.labs.grocerystore.model.AddToCart;
import com.quintus.labs.grocerystore.model.Cart;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.ProductDetails;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.util.Utils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class ProductViewActivity extends BaseActivity {
    private static int cart_count = 0;
    public TextView quantity, inc, dec;
    String _id, _title, _image, _description, _price, _currency, _discount, _attribute;
    TextView id, title, description, price, org_price, currency, discount, attribute;
    ImageView imageView;
    ProgressBar progressBar;
    LinearLayout addToCart, share;
    RelativeLayout quantityLL;
    List<Cart> cartList = new ArrayList<>();
    int cartId;
    Cart cart;
    ProductDetails productDetails;
    String token;
    User user;
    View changeProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        changeProgressBar =findViewById(R.id.progress_bar);

        Intent intent = getIntent();

          _id = intent.getStringExtra("id");
        localStorage = new LocalStorage(getApplicationContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = localStorage.getApiKey();

        getProductDetails();
        getCartDetails();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        changeActionBarTitle(getSupportActionBar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

     //   cart_count = cartCount();

        title = findViewById(R.id.apv_title);
        description = findViewById(R.id.description);
        currency = findViewById(R.id.apv_currency);
        price = findViewById(R.id.apv_price);
        org_price = findViewById(R.id.apv_org_price);
        discount = findViewById(R.id.apv_discount);
        imageView = findViewById(R.id.apv_image);
        progressBar = findViewById(R.id.progressbar);
        addToCart = findViewById(R.id.add_to_cart_ll);
        share = findViewById(R.id.apv_share);
        quantityLL = findViewById(R.id.quantity_rl);
        quantity = findViewById(R.id.quantity);
        inc = findViewById(R.id.quantity_plus);
        dec = findViewById(R.id.quantity_minus);

        cartList = getCartList();



//        if (!cartList.isEmpty()) {
//            for (int i = 0; i < cartList.size(); i++) {
//                if (cartList.get(i).getId().equalsIgnoreCase(_id)) {
//                    addToCart.setVisibility(View.GONE);
//                    quantityLL.setVisibility(View.VISIBLE);
//                    quantity.setText(cartList.get(i).getQuantity());
//                    cartId = i;
//
//                }
//            }
//        }


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    String userEntry = productDetails.getImages().get(0).getImage() + "\n" + _title + "\n" + _description + "\n" + _attribute + "-" + _currency + _price + "(" + _discount + ")";
                String userEntry = productDetails.getImages().get(0).getImage() + "\n" + productDetails.getName() + "\n" + productDetails.getDescription() + "\n" + productDetails.getCurrency().getSymbol() +  productDetails.getPrice() + "(" + productDetails.getMrp() + ")";
                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);
                textShareIntent.setType("text/plain");
                startActivity(textShareIntent);
            }
        });


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                _price = price.getText().toString();
//
//                cart = new Cart(_id, _title, _image, _currency, _price, _attribute, "1", _price);
//                cartList.add(cart);
//                String cartStr = gson.toJson(cartList);
//                //Log.d("CART", cartStr);
//                localStorage.setCart(cartStr);

                addToCart.setVisibility(View.GONE);
                quantityLL.setVisibility(View.VISIBLE);
                AddToCart addtoCart = new AddToCart(1,Integer.parseInt(_id),null,true);
                addingToCart(addtoCart,"plus");

            }
        });


        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddToCart addtoCart = new AddToCart(1,Integer.parseInt(_id),null,true);
                addingToCart(addtoCart,"plus");







//                _price = price.getText().toString();
//
//
//                // int total_item = Integer.parseInt(cartList.get(cartId).getQuantity());
//                int total_item = Integer.parseInt(quantity.getText().toString());
//                total_item++;
//                Log.d("totalItem", total_item + "");
//                quantity.setText(total_item + "");
//                String subTotal = String.valueOf(Double.parseDouble(_price) * total_item);
//                cartList.get(cartId).setQuantity(quantity.getText().toString());
//                cartList.get(cartId).setSubTotal(subTotal);
//                cartList.get(cartId).setAttribute(attribute.getText().toString());
//                cartList.get(cartId).setPrice(_price);
//                String cartStr = gson.toJson(cartList);
//                //Log.d("CART", cartStr);
//                localStorage.setCart(cartStr);




            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AddToCart addtoCart = new AddToCart(1,Integer.parseInt(_id),null,false);
                addingToCart(addtoCart,"minus");




//                _price = price.getText().toString();
//
//                //int total_item = Integer.parseInt(quantity.getText().toString());
//                int total_item = Integer.parseInt(quantity.getText().toString());
//                if (total_item != 1) {
//                    total_item--;
//                    quantity.setText(total_item + "");
//                    Log.d("totalItem", total_item + "");
//                    String subTotal = String.valueOf(Double.parseDouble(_price) * total_item);
//
//
//                    cartList.get(cartId).setQuantity(quantity.getText().toString());
//                    cartList.get(cartId).setSubTotal(subTotal);
//                    cartList.get(cartId).setAttribute(attribute.getText().toString());
//                    cartList.get(cartId).setPrice(_price);
//                    String cartStr = gson.toJson(cartList);
//                    //Log.d("CART", cartStr);
//                    localStorage.setCart(cartStr);
//                }
            }
        });


    }

    private void getProductDetails() {

        Call<ProductDetails>call = RestClient.getRestService(getApplicationContext()).productDetails(token,_id);
        call.enqueue(new retrofit2.Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                if(response.code()==200){
                    productDetails = response.body();
                    title.setText(productDetails.getName());
                    description.setText(productDetails.getDescription());
                    org_price.setText(productDetails.getMrp());
                    org_price.setPaintFlags(org_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    price.setText(productDetails.getPrice());
                    currency.setText(productDetails.getCurrency().getSymbol());
//                    if(Double.parseDouble(productDetails.getDiscount())>0){
//                        discount.setText(productDetails.getDiscount());
//                        discount.setVisibility(View.VISIBLE);
//                    }else{
//                        discount.setVisibility(View.GONE);
//                    }



                    if(productDetails.getImages().get(0).getImage()!=null){
                        Picasso.get().load(productDetails.getImages().get(0).getImage()).error(R.drawable.no_image).into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void changeActionBarTitle(ActionBar actionBar) {
        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        TextView tv = new TextView(getApplicationContext());
        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        // Set text to display in TextView
        tv.setText(_title); // ActionBar title text
        tv.setTextSize(20);

        // Set the text color of TextView to red
        // This line change the ActionBar title text color
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the ActionBar display option
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();
                return true;

            case R.id.cart_action:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(ProductViewActivity.this, cart_count, R.drawable.ic_shopping_basket));
        return true;
    }


    private void hideProgressDialog() {
        changeProgressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        changeProgressBar.setVisibility(View.VISIBLE);
    }



    private void addingToCart(AddToCart addtoCart, final String plus) {


        showProgressDialog();
        Call<AddToCart> call = RestClient.getRestService(getApplicationContext()).addToCart(token, addtoCart);
        call.enqueue(new retrofit2.Callback<AddToCart>() {
            @Override
            public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {

                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    AddToCart addToCartResponse = response.body();
                    if (response.code() == 200) {

                        if (plus.equalsIgnoreCase("plus")) {
                            onAddProduct();
                            Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_LONG).show();

                        } else {
                            onRemoveProduct();
                            Toast.makeText(getApplicationContext(), "Successfully removed", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_LONG).show();
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

    @Override
    public void onAddProduct() {
        super.onAddProduct();
        //  cart_count++;
        getCartDetails();
    //    invalidateOptionsMenu();

    }

    @Override
    public void onRemoveProduct() {
        super.onRemoveProduct();
        //  cart_count--;
        getCartDetails();
     //   invalidateOptionsMenu();
    }

    private void getCartDetails() {

        showProgressDialog();
        Call<CartDetails> call = RestClient.getRestService(getApplicationContext()).getCartList(token);
        call.enqueue(new retrofit2.Callback<CartDetails>() {
            @Override
            public void onResponse(Call<CartDetails> call, Response<CartDetails> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    CartDetails cartDetails = response.body();
                    if (response.code() == 200) {
                        cart_count=cartDetails.getTotalItems();
                        invalidateOptionsMenu();
                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<CartDetails> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                hideProgressDialog();
            }
        });

    }
}


