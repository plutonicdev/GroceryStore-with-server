package com.quintus.labs.grocerystore.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.adapter.SearchAdapter;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.fragment.AddressFragment;
import com.quintus.labs.grocerystore.fragment.CategoryFragment;
import com.quintus.labs.grocerystore.fragment.HomeFragment;
import com.quintus.labs.grocerystore.fragment.MyOrderFragment;
import com.quintus.labs.grocerystore.fragment.NewProductFragment;
import com.quintus.labs.grocerystore.fragment.OffrersFragment;
import com.quintus.labs.grocerystore.fragment.PopularProductFragment;
import com.quintus.labs.grocerystore.fragment.ProfileFragment;
import com.quintus.labs.grocerystore.helper.Converter;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.Product;
import com.quintus.labs.grocerystore.model.ProductDetail;
import com.quintus.labs.grocerystore.model.ProductResult;
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
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static int cart_count = 0;
    User user;
    private ArrayList<ProductDetail> productDetail = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    SearchAdapter mAdapter;
    private RecyclerView recyclerView;
    boolean doubleBackToExitPressedOnce = false;

    LocalStorage localStorage;

    Gson gson;
    String token;
    View progress;

    @SuppressLint("ResourceAsColor")
    static void centerToolbarTitle(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(Color.parseColor("#f9a825"));
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
            //also you can use titleView for changing font: titleView.setTypeface(Typeface);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getCartDetails();
            FragmentManager manager = getSupportFragmentManager();
            int count = manager.getBackStackEntryCount();
            // Log.d("This Fragment name: ", ""+count);
            if (count == 1) {
                if (doubleBackToExitPressedOnce) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            } else {

                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_shopping_basket));
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        searchItem.setVisible(false);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setQueryHint("Search Here...");
        EditText searchBox = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchBox.setTextColor(Color.BLACK);
        searchBox.setHintTextColor(Color.GRAY);
        searchView.setIconifiedByDefault(true);
        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_close_black_24dp);

        if (searchView != null) {
            final SearchView finalSearchView = searchView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Toast like print
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                    if (s.length() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        productList = new ArrayList<>();
                    } else {
                        getSearchProduct(s);
                    }

                    return true;
                }
            });
        }


        return true;
    }

    private void getSearchProduct(String query) {


    }

    private void setUpRecyclerView() {
        if (productList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        mAdapter = new SearchAdapter(productList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_action:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        centerToolbarTitle(toolbar);
        //cart_count = cartCount();

        progress = findViewById(R.id.progress_bar);
        localStorage = new LocalStorage(getApplicationContext());
        gson = new Gson();
        token = localStorage.getApiKey();
        getCartDetails();
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.search_recycler_view);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        localStorage = new LocalStorage(getApplicationContext());
        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        TextView nav_user = hView.findViewById(R.id.nav_header_name);
        LinearLayout nav_footer = findViewById(R.id.footer_text);
        if (user != null) {
            nav_user.setText(user.getFname());
        }
        nav_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localStorage.logoutUser();
                startActivity(new Intent(getApplicationContext(), LoginRegisterActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                // Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            }
        });

        displaySelectedScreen(R.id.nav_home);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
//            case R.id.nav_address:
//                fragment = new AddressFragment();
//                break;
            case R.id.nav_category:
                fragment = new CategoryFragment();
                break;
            case R.id.nav_popular_products:
                fragment = new PopularProductFragment();
                break;
            case R.id.nav_new_product:
                fragment = new NewProductFragment();
                break;

            case R.id.nav_offers:
                fragment = new OffrersFragment();
                break;

            case R.id.nav_my_order:
                fragment = new MyOrderFragment();
                break;
            case R.id.nav_my_cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;


        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    @Override
    public void onAddProduct() {
        super.onAddProduct();
        //  cart_count++;
        getCartDetails();
        //  invalidateOptionsMenu();

    }

    @Override
    public void onRemoveProduct() {
        super.onRemoveProduct();
        //  cart_count--;
        getCartDetails();
        //  invalidateOptionsMenu();
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

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }


}
