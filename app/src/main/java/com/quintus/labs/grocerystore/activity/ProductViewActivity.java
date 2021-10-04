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
import com.quintus.labs.grocerystore.interfaces.AddorRemoveCallbacks;
import com.quintus.labs.grocerystore.model.AddToCart;
import com.quintus.labs.grocerystore.model.Cart;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.ProductDetail;
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
    String _id, _title, _image, _description, _price, _currency, _discount, _attribute, _quantity;
    TextView id, title, description, price, org_price, currency, discount, attribute;
    ImageView imageView;
    ProgressBar progressBar;
    LinearLayout addToCart, share;
    RelativeLayout quantityLL;
    List<ProductDetail> cartList = new ArrayList<>();
    int cartId;
    Cart cart;
    ProductDetails productDetails;
    String token;
    User user;
    View changeProgressBar;
    private ArrayList<ProductDetail> productDetail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        changeProgressBar = findViewById(R.id.progress_bar);

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
          quantity.setText("0");


        if (!cartList.isEmpty()) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getId().equals(Integer.parseInt(_id))) {
                    addToCart.setVisibility(View.GONE);
                    quantityLL.setVisibility(View.VISIBLE);
                    quantity.setText(String.valueOf(cartList.get(i).getCount()));
                    cartId = i;

                }
            }
        }


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    String userEntry = productDetails.getImages().get(0).getImage() + "\n" + _title + "\n" + _description + "\n" + _attribute + "-" + _currency + _price + "(" + _discount + ")";
                String userEntry = productDetails.getImages().get(0).getImage() + "\n" + productDetails.getName() + "\n" + productDetails.getDescription() + "\n" + productDetails.getCurrency().getSymbol() + productDetails.getPrice() + "(" + productDetails.getMrp() + ")";
                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);
                textShareIntent.setType("text/plain");
                startActivity(textShareIntent);
            }
        });


        if(quantity.getText().toString().equalsIgnoreCase("0")){
            addToCart.setVisibility(View.VISIBLE);
            quantityLL.setVisibility(View.GONE);
        }
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCart.setVisibility(View.GONE);
                quantityLL.setVisibility(View.VISIBLE);

                _quantity = quantity.getText().toString();
                int qty = Integer.parseInt(_quantity) + 1;
                quantity.setText(String.valueOf(qty));

                AddToCart addtoCart = new AddToCart(1, Integer.parseInt(_id), null, true);
                addingToCart(addtoCart, "plus");

            }
        });


        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _quantity = quantity.getText().toString();
                int qty = Integer.parseInt(_quantity) + 1;
                quantity.setText(String.valueOf(qty));

                AddToCart addtoCart = new AddToCart(1, Integer.parseInt(_id), null, true);
                addingToCart(addtoCart, "plus");



            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _quantity = quantity.getText().toString();
                int qty = Integer.parseInt(_quantity) - 1;
                quantity.setText(String.valueOf(qty));

                if(quantity.getText().toString().equalsIgnoreCase("0")){
                    addToCart.setVisibility(View.VISIBLE);
                    quantityLL.setVisibility(View.GONE);
                }

                AddToCart addtoCart = new AddToCart(1, Integer.parseInt(_id), null, false);
                addingToCart(addtoCart, "minus");



            }
        });


    }

    private void getProductDetails() {

        Call<ProductDetails> call = RestClient.getRestService(getApplicationContext()).productDetails(_id);
        call.enqueue(new retrofit2.Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                if (response.code() == 200) {
                    productDetails = response.body();
                    title.setText(productDetails.getName());
                    description.setText(productDetails.getDescription());
                    org_price.setText(productDetails.getMrp());
                    org_price.setPaintFlags(org_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    price.setText(productDetails.getPrice());
                    currency.setText(productDetails.getCurrency().getSymbol());


                    if (productDetails.getImages().get(0).getImage() != null) {
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
                Log.d(TAG, "onFailure: " + t.getMessage());
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
                            Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();

                        } else {
                            onRemoveProduct();
                            Toast.makeText(getApplicationContext(), "Successfully removed", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();
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
        getCartDetails();

    }

    @Override
    public void onRemoveProduct() {
        super.onRemoveProduct();
        getCartDetails();
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
                        cart_count = cartDetails.getTotalItems();

                        productDetail.clear();
                        if(cartDetails.getProductDetails().size()>0) {
                            for (int i = 0; i < cartDetails.getProductDetails().size(); i++) {

                                int id = cartDetails.getProductDetails().get(i).getProduct().getId();
                                int count = cartDetails.getProductDetails().get(i).getCount();
                                ProductDetail productDetails = new ProductDetail(id, count);
                                productDetail.add(productDetails);
                            }
                            String cartStr = gson.toJson(productDetail);
                            Log.d("CART", cartStr);
                            localStorage.setCart(cartStr);
                        }else{
                            productDetail.clear();
                            localStorage.deleteCart();
                        }


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

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();

    }


}


