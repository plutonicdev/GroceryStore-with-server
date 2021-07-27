package com.quintus.labs.grocerystore.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.adapter.CategoryAdapter;
import com.quintus.labs.grocerystore.adapter.SubCategoryAdapter;
import com.quintus.labs.grocerystore.model.Banners;
import com.quintus.labs.grocerystore.model.SubCategory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {
    List<SubCategory> subCategoryList= new ArrayList<>();
   // ProgressBar progressBar;
//    ImageView imageView;
//    TextView textView;
//    CardView cardView;
    private RecyclerView recyclerView;
    private SubCategoryAdapter mAdapter;
    View progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        recyclerView=findViewById(R.id.subCategory_rv);
        progress = findViewById(R.id.progress_bar);
      //  progressBar=findViewById(R.id.progressbar);
//        imageView=findViewById(R.id.category_image);
//        textView=findViewById(R.id.category_title);
//        cardView=findViewById(R.id.card_view);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        changeActionBarTitle(getSupportActionBar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        Intent intent = getIntent();
        subCategoryList =  (List<SubCategory>) intent.getSerializableExtra("category");

        getData();



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
        tv.setText("SubCategory"); // ActionBar title text
        tv.setTextSize(20);

        // Set the text color of TextView to red
        // This line change the ActionBar title text color
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the ActionBar display option
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);
    }

    private void getData() {
        if (subCategoryList.size() > 0) {
            setupSubCategoryRecycleView();
//            for (int i = 0; i < subCategoryList.size(); i++) {
//                Picasso.get()
//                        .load( subCategoryList.get(i).getImage())
//                        .into(imageView, new Callback() {
//                            @Override
//                            public void onSuccess() {
//                               progressBar.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onError(Exception e) {
//                                Log.d("Error : ", e.getMessage());
//                            }
//                        });
//                  textView.setText(subCategoryList.get(i).getName());
////                Banners sliderUtils = new Banners();
////                sliderUtils.setImage(bannersListData.get(i).getImage());
//
//            }
        }
       // Toast.makeText(this,subCategoryList.get(0).getName(),Toast.LENGTH_SHORT).show();
    }

    private void setupSubCategoryRecycleView() {
        mAdapter = new SubCategoryAdapter(subCategoryList, getApplicationContext());
          RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }

}