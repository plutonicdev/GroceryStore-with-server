package com.quintus.labs.grocerystore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.AdvertisementBannerResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class AdvertisementBannerAdapter extends RecyclerView.Adapter<AdvertisementBannerAdapter.MyViewHolder> {

    List<AdvertisementBannerResult> advertisementBannerList;
    Context context;
    String Tag;

    public AdvertisementBannerAdapter(List<AdvertisementBannerResult> advertisementBannerList, Context context) {
        this.advertisementBannerList = advertisementBannerList;
        this.context = context;
    }

    public AdvertisementBannerAdapter(List<AdvertisementBannerResult> advertisementBannerList, Context context, String tag) {
        this.advertisementBannerList = advertisementBannerList;
        this.context = context;
        Tag = tag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_adv_banner, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final AdvertisementBannerResult result = advertisementBannerList.get(position);


        Picasso.get()
                .load( result.getImage())
                .into(holder.imageView, new Callback() {
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

        return advertisementBannerList.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);

        }
    }
}
