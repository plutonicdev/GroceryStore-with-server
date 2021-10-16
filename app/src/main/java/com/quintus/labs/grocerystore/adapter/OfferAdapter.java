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
    Integer[] imageList = { R.drawable.offer_bg_1, R.drawable.offer_bg_2, R.drawable.offer_bg_3, R.drawable.offer_bg_4};


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

        if(offer.getVoucherStatus().equalsIgnoreCase("ongoing")) {
            holder.cardView.setVisibility(View.VISIBLE);
        }else{
            holder.cardView.setVisibility(View.GONE);
        }
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
                    Toast.makeText(context, "Code Copied !", Toast.LENGTH_SHORT).show();
                }
            });

            if (position >= imageList.length) {

            } else {
                Picasso.get().load(imageList[position]).into(holder.backgroundImage);
            }


    }

    @Override
    public int getItemCount() {

        return offerList.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,end_date,status,code,discount_price,minimum_ordre_price;
        ProgressBar progressBar;
        ImageView imageView,backgroundImage;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.copy);
            backgroundImage = itemView.findViewById(R.id.offer_image);
            name = itemView.findViewById(R.id.name);
            end_date = itemView.findViewById(R.id.end_date);
            status = itemView.findViewById(R.id.status);
            code = itemView.findViewById(R.id.code);
            discount_price = itemView.findViewById(R.id.discount_price);
            minimum_ordre_price = itemView.findViewById(R.id.minimum_ordre_price);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.card_view);

        }
    }
}
