package com.quintus.labs.grocerystore.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.adapter.OrderAdapter;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.Order;
import com.quintus.labs.grocerystore.model.OrdersResult;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.Order;
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
public class MyOrderFragment extends Fragment {
    public static String TAG = "MyOrderFragment==>";
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;
    Token token;
    LinearLayout linearLayout;
    View progress;

    private List<OrdersResult> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter mAdapter;


    int page = 1;
    int page_size = 10;
    boolean isLoading = true;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        recyclerView = view.findViewById(R.id.order_rv);
        linearLayout = view.findViewById(R.id.no_order_ll);
        localStorage = new LocalStorage(getContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = new Token(localStorage.getApiKey());
        progress = view.findViewById(R.id.progress_bar);

        fetchOrderDetails();

        return view;
    }

    private void fetchOrderDetails() {
        showProgressDialog();
        Call<Order> call = RestClient.getRestService(getContext()).getOrderDetails(localStorage.getApiKey(), page, page_size);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    Order order = response.body();
                    if (response.code() == 200) {

                        orderList = order.getResults();
                        setupOrderRecycleView();
                        if (page < order.getTotalPages()) {
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
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                hideProgressDialog();
            }

        });

    }

    private void setupOrderRecycleView() {
        if (orderList.isEmpty()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        mAdapter = new OrderAdapter(orderList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("MyOrder");
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == orderList.size() - 1) {
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
        Call<Order> call = RestClient.getRestService(getContext()).getOrderDetails(localStorage.getApiKey(), page, page_size);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    Order order = response.body();
                    if (response.code() == 200) {

                        orderList.addAll( order.getResults());
                        mAdapter.notifyDataSetChanged();

                        if (page < order.getTotalPages()) {
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
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("Error", t.getMessage());
                hideProgressDialog();

            }
        });


    }

}
