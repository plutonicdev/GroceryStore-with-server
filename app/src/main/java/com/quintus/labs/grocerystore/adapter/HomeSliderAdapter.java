package com.quintus.labs.grocerystore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.Banners;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class HomeSliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
  //  private Integer[] images;
    private  List<Banners> bannersList;


    public HomeSliderAdapter(Context context) {
        this.context = context;
    }

//    public HomeSliderAdapter(Context context, Integer[] images) {
//        this.context = context;
//        this.images = images;
//    }

    public HomeSliderAdapter( Context context,List bannersList) {
        this.context = context;
        this.bannersList = bannersList;
    }

//    public HomeSliderAdapter(Context context, List<Banners> bannersList) {
//    }

    @Override
    public int getCount() {
        return bannersList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_home_slider, null);
        ImageView imageView = view.findViewById(R.id.imageView);
      final ProgressBar progressBar = view.findViewById(R.id.progressbar);
      //  imageView.setImageResource(images[position]);

        Banners utils = bannersList.get(position);
        Picasso.get().load(utils.getImage()).error(R.drawable.no_image).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
            }
        });


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}