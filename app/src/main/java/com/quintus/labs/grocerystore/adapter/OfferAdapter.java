package com.quintus.labs.grocerystore.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.VoucherList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    List<VoucherList> offerList;
    Context context;
    String Tag;

    public OfferAdapter(List<VoucherList> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    public OfferAdapter(List<VoucherList> offerList, Context context, String tag) {
        this.offerList = offerList;
        this.context = context;
        Tag = tag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_offer, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final VoucherList offer = offerList.get(position);
        holder.name.setText(offer.getName());
        holder.end_date.setText(offer.getEndDate());
        holder.status.setText(offer.getVoucherStatus());
        holder.code.setText(offer.getCode());
        holder.discount_price.setText(offer.getDiscountAmount());
        holder.minimum_ordre_price.setText(offer.getMinSpent());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PROMOCODE", offer.getCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context,"Code Copied !",Toast.LENGTH_SHORT).show();
            }
        });

//        Picasso.get().load(offer.getImage()).error(R.drawable.no_image).into(holder.imageView, new Callback() {
//            @Override
//            public void onSuccess() {
//                holder.progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return offerList.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,end_date,status,code,discount_price,minimum_ordre_price;
        ProgressBar progressBar;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.copy);
            name = itemView.findViewById(R.id.name);
            end_date = itemView.findViewById(R.id.end_date);
            status = itemView.findViewById(R.id.status);
            code = itemView.findViewById(R.id.code);
            discount_price = itemView.findViewById(R.id.discount_price);
            minimum_ordre_price = itemView.findViewById(R.id.minimum_ordre_price);
            progressBar = itemView.findViewById(R.id.progressbar);

        }
    }
}
