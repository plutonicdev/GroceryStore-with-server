package com.quintus.labs.grocerystore.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.adapter.AdvertisementBannerAdapter;
import com.quintus.labs.grocerystore.adapter.CategoryAdapter;
import com.quintus.labs.grocerystore.adapter.HomeSliderAdapter;
import com.quintus.labs.grocerystore.adapter.NewProductAdapter;
import com.quintus.labs.grocerystore.adapter.PopularProductAdapter;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.helper.Data;
import com.quintus.labs.grocerystore.model.AdvertisementBanner;
import com.quintus.labs.grocerystore.model.AdvertisementBannerResult;
import com.quintus.labs.grocerystore.model.Banners;
import com.quintus.labs.grocerystore.model.Category;
import com.quintus.labs.grocerystore.model.CategoryResult;
import com.quintus.labs.grocerystore.model.PopularProductsResult;
import com.quintus.labs.grocerystore.model.Product;
import com.quintus.labs.grocerystore.model.PopularProducts;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    Timer timer;
    int page_position = 0;
    Data data;
    View progress;
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;
    String token;
    private int dotscount;
    private ImageView[] dots;
    private List<Category> categoryList = new ArrayList<>();
    private List<AdvertisementBannerResult> advertisementBannerList = new ArrayList<>();
    private List<PopularProductsResult> productList = new ArrayList<>();
    private List<PopularProductsResult> popularProductList = new ArrayList<>();
    private List<Banners> bannersList = new ArrayList<>();
    private RecyclerView recyclerView, nRecyclerView, pRecyclerView,adv_banner_rv;
    private CategoryAdapter mAdapter;
    private NewProductAdapter nAdapter;
    private PopularProductAdapter pAdapter;
    private AdvertisementBannerAdapter advBannerAdapter;
    private Integer[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4, R.drawable.slider5};
    int page=1;
    int page_size=10;
   // HomeSliderAdapter viewPagerAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.category_rv);
        pRecyclerView = view.findViewById(R.id.popular_product_rv);
        adv_banner_rv = view.findViewById(R.id.adv_banner_rv);
        nRecyclerView = view.findViewById(R.id.new_product_rv);
        progress = view.findViewById(R.id.progress_bar);

        localStorage = new LocalStorage(getContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = localStorage.getApiKey();
        getBannerData();
        getCategoryData();
        getNewProduct();
        getPopularProduct();
        getOffersData();


        timer = new Timer();
        viewPager = view.findViewById(R.id.viewPager);

        sliderDotspanel = view.findViewById(R.id.SliderDots);

       HomeSliderAdapter viewPagerAdapter = new HomeSliderAdapter(getContext(), images);

       viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scheduleSlider();

        return view;
    }

    private void getPopularProduct() {
        showProgressDialog();
        Call<PopularProducts> call = RestClient.getRestService(getContext()).popularProducts(token,page,page_size);
        call.enqueue(new Callback<PopularProducts>() {
            @Override
            public void onResponse(Call<PopularProducts> call, Response<PopularProducts> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    PopularProducts productResult = response.body();
                    if (response.code() == 200) {

                        assert productResult != null;
                        popularProductList = productResult.getResults();
                        setupPopularProductRecycleView();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<PopularProducts> call, Throwable t) {

            }
        });
    }

    private void setupPopularProductRecycleView() {

        pAdapter = new PopularProductAdapter(popularProductList, getContext(), "Home");
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        pRecyclerView.setLayoutManager(pLayoutManager);
        pRecyclerView.setItemAnimator(new DefaultItemAnimator());
        pRecyclerView.setAdapter(pAdapter);

    }

    private void getNewProduct() {
        showProgressDialog();
        Call<PopularProducts> call = RestClient.getRestService(getContext()).newProducts(token,page,page_size);
        call.enqueue(new Callback<PopularProducts>() {
            @Override
            public void onResponse(Call<PopularProducts> call, Response<PopularProducts> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    PopularProducts productResult = response.body();
                    if (response.code() == 200) {

                        assert productResult != null;
                        productList = productResult.getResults();
                        setupProductRecycleView();

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

    private void setupProductRecycleView() {
        nAdapter = new NewProductAdapter(productList, getContext(), "Home");
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        nRecyclerView.setLayoutManager(nLayoutManager);
        nRecyclerView.setItemAnimator(new DefaultItemAnimator());
        nRecyclerView.setAdapter(nAdapter);

    }

    private void getBannerData() {

        showProgressDialog();

        Call<Banners> call = RestClient.getRestService(getContext()).bannerList();
        call.enqueue(new Callback<Banners>() {
            @Override
            public void onResponse(Call<Banners> call, Response<Banners> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 200) {
//                        bannersList = response.body();
//                        setupBannersRecycleView();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {
                Log.d("Error==>", t.getMessage());
            }
        });

    }
 private void getCategoryData() {

        showProgressDialog();

        Call<CategoryResult> call = RestClient.getRestService(getContext()).allCategory(token,page,page_size);
        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    CategoryResult categoryResult = response.body();
                    if (response.code() == 200) {

                        categoryList = categoryResult.getResults();
                        setupCategoryRecycleView();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {
                Log.d("Error==>", t.getMessage());
            }
        });

    }

    private void setupCategoryRecycleView() {
        mAdapter = new CategoryAdapter(categoryList, getContext(), "Home");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
private void getOffersData() {

        showProgressDialog();

        Call<AdvertisementBanner> call = RestClient.getRestService(getContext()).getAdvertisementBanners(token,page,page_size);
        call.enqueue(new Callback<AdvertisementBanner>() {
            @Override
            public void onResponse(Call<AdvertisementBanner> call, Response<AdvertisementBanner> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    AdvertisementBanner advertisementBanner = response.body();
                    if (response.code() == 200) {

                        assert advertisementBanner != null;
                        advertisementBannerList = advertisementBanner.getResults();
                        setupOffersRecycleView();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<AdvertisementBanner> call, Throwable t) {
                Log.d("Error==>", t.getMessage());
            }
        });

    }

    private void setupOffersRecycleView() {
        advBannerAdapter = new AdvertisementBannerAdapter(advertisementBannerList, getContext(), "Home");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adv_banner_rv.setLayoutManager(mLayoutManager);
        adv_banner_rv.setItemAnimator(new DefaultItemAnimator());
        adv_banner_rv.setAdapter(advBannerAdapter);


    }

    private void setupBannersRecycleView() {
     //   viewPagerAdapter = new HomeSliderAdapter(bannersList, getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
    //    viewPager.setAdapter(viewPagerAdapter);


    }


    public void scheduleSlider() {

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == dotscount) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                viewPager.setCurrentItem(page_position, true);
            }
        };

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 4000);
    }

    @Override
    public void onStop() {
        timer.cancel();
        super.onStop();
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    public void onLetsClicked(View view) {
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }

}
