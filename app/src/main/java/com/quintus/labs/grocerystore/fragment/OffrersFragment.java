package com.quintus.labs.grocerystore.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.adapter.NewProductAdapter;
import com.quintus.labs.grocerystore.adapter.OfferAdapter;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.helper.Data;
import com.quintus.labs.grocerystore.model.Offer;
import com.quintus.labs.grocerystore.model.VoucherListData;
import com.quintus.labs.grocerystore.model.VoucherList;
import com.quintus.labs.grocerystore.model.PopularProductsResult;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.VoucherList;
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
public class OffrersFragment extends Fragment {



    List<VoucherList> offerList = new ArrayList<>();

    private RecyclerView recyclerView;
    private OfferAdapter mAdapter;
    int page = 1;
    int page_size = 10;
    boolean isLoading = true;
    View progress;
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;
    String token;

    public OffrersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        recyclerView = view.findViewById(R.id.offer_rv);


        progress = view.findViewById(R.id.progress_bar);

        localStorage = new LocalStorage(getContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = localStorage.getApiKey();

        getOffers();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Offer");
    }


    private void getOffers() {
        showProgressDialog();
        Call<VoucherListData> call = RestClient.getRestService(getContext()).allOffers(token, page, page_size);
        call.enqueue(new Callback<VoucherListData>() {
            @Override
            public void onResponse(Call<VoucherListData> call, Response<VoucherListData> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    VoucherListData voucherList = response.body();
                    if (response.code() == 200) {

                        offerList = voucherList.getResults();
                        setupProductRecycleView();

                        if (page < voucherList.getTotalPages()) {
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
            public void onFailure(Call<VoucherListData> call, Throwable t) {
                Log.d("Error", t.getMessage());
                hideProgressDialog();

            }
        });
    }

    private void setupProductRecycleView() {
        mAdapter = new OfferAdapter(offerList, getContext(), "new");
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(pLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
    

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == offerList.size() - 1) {
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
        Call<VoucherListData> call = RestClient.getRestService(getContext()).allOffers(token, page, page_size);
        call.enqueue(new Callback<VoucherListData>() {
            @Override
            public void onResponse(Call<VoucherListData> call, Response<VoucherListData> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    VoucherListData productResult = response.body();
                    if (response.code() == 200) {

                        offerList.addAll( productResult.getResults());
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
            public void onFailure(Call<VoucherListData> call, Throwable t) {
                Log.d("Error", t.getMessage());
                hideProgressDialog();

            }
        });


    }

}
