package com.quintus.labs.grocerystore.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.adapter.PopularProductAdapter;
import com.quintus.labs.grocerystore.adapter.ProductAdapter;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.helper.Converter;
import com.quintus.labs.grocerystore.helper.Data;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.Category;
import com.quintus.labs.grocerystore.model.PopularProducts;
import com.quintus.labs.grocerystore.model.PopularProductsResult;
import com.quintus.labs.grocerystore.model.Product;
import com.quintus.labs.grocerystore.model.ProductResult;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
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
public class ProductActivity extends BaseActivity {
    private static int cart_count = 0;
    Data data;

    String Tag = "List";
    View progress;
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;
    String token;
    String categoryName;
    Category category;
    ProductAdapter mAdapter;
    private RecyclerView recyclerView;
    Integer id;
    int page=1;
    int page_size=10;
    boolean isLoading = true;

    List<PopularProductsResult> productList = new ArrayList<>();


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        changeActionBarTitle(getSupportActionBar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        progress = findViewById(R.id.progress_bar);

        localStorage = new LocalStorage(getApplicationContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = localStorage.getApiKey();
        getCartDetails();
        Intent intent = getIntent();

        id= intent.getIntExtra("id",0);

        cart_count = cartCount();
        recyclerView = findViewById(R.id.product_rv);


        getCategoryProduct();


    }




    private void getCategoryProduct() {

        showProgressDialog();
        Call<PopularProducts> call = RestClient.getRestService(getApplicationContext()).allProducts(id,page,page_size);
        call.enqueue(new Callback<PopularProducts>() {
            @Override
            public void onResponse(Call<PopularProducts> call, Response<PopularProducts> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    PopularProducts productResult = response.body();
                    if (response.code() == 200) {

                        productList = productResult.getResults();
                        setUpRecyclerView();

                        if (page < productResult.getTotalPages()) {
                            isLoading = true;
                        } else {
                            isLoading = false;
                        }

                        initScrollListener();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<PopularProducts> call, Throwable t) {
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
        tv.setText("Products"); // ActionBar title text
        tv.setTextSize(20);

        // Set the text color of TextView to red
        // This line change the ActionBar title text color
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the ActionBar display option
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);
    }

    private void setUpRecyclerView() {

        mAdapter = new ProductAdapter(productList, ProductActivity.this,Tag);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private void setUpGridRecyclerView() {

        mAdapter = new ProductAdapter(productList, ProductActivity.this,Tag);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public void onToggleClicked(View view) {
        if (Tag.equalsIgnoreCase("List")) {
            Tag = "Grid";
            setUpGridRecyclerView();
        } else {
            Tag = "List";
            setUpRecyclerView();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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
        menuItem.setIcon(Converter.convertLayoutToImage(ProductActivity.this, cart_count, R.drawable.ic_shopping_basket));
        return true;
    }


    @Override
    public void onAddProduct() {
        getCartDetails();

    }

    @Override
    public void onRemoveProduct() {
        getCartDetails();

    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productList.size() - 1) {
                        //bottom of list!
                        loadMore();

                    }
                }
            }
        });

    }

    private void loadMore() {
        page = page + 1;

        showProgressDialog();
        Call<PopularProducts> call = RestClient.getRestService(getApplicationContext()).allProducts(id,page, page_size);
        call.enqueue(new Callback<PopularProducts>() {
            @Override
            public void onResponse(Call<PopularProducts> call, Response<PopularProducts> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    PopularProducts productResult = response.body();
                    if (response.code() == 200) {

                        productList.addAll( productResult.getResults());
                        mAdapter.notifyDataSetChanged();

                        if (page < productResult.getTotalPages()) {
                            isLoading = true;
                        } else {
                            isLoading = false;
                        }

                        initScrollListener();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<PopularProducts> call, Throwable t) {
                Log.d("Error", t.getMessage());
                hideProgressDialog();

            }
        });


    }



    private void getCartDetails() {

        showProgressDialog();
        Call<CartDetails> call = RestClient.getRestService(getApplicationContext()).getCartList(token);
        call.enqueue(new Callback<CartDetails>() {
            @Override
            public void onResponse(Call<CartDetails> call, Response<CartDetails> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    CartDetails cartDetails = response.body();
                    if (response.code() == 200) {
                        assert cartDetails != null;
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
