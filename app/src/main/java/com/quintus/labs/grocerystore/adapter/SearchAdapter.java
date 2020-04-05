package com.quintus.labs.grocerystore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.ProductViewActivity;
import com.quintus.labs.grocerystore.model.Product;
import com.quintus.labs.grocerystore.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    List<Product> productList;
    Context context;

    public SearchAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public SearchAdapter(List<Product> productList, Context context, String tag) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_products, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Product product = productList.get(position);

        holder.title.setText(product.getName());

        if (product.getImage() != null) {
            Picasso.get().load(Utils.ProductImage + product.getImage()).into(holder.imageView);
        }
        holder.row_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductViewActivity.class);
                intent.putExtra("id", product.getId());
                intent.putExtra("title", product.getName());
                intent.putExtra("image", product.getImage());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("currency", product.getCurrency());
                intent.putExtra("attribute", product.getAttribute());
                intent.putExtra("discount", product.getDiscount());
                intent.putExtra("description", product.getDescription());


                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        LinearLayout row_ll;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.search_image);
            title = itemView.findViewById(R.id.search_title);
            row_ll = itemView.findViewById(R.id.row_ll);
        }
    }
}
