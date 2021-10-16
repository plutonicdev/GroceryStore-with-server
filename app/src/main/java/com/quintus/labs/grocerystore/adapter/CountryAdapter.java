package com.quintus.labs.grocerystore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryAdapter extends BaseAdapter {

    Context context;
    List<Country> countryList;
    ArrayList<Country> arrayList=new ArrayList<Country>();
    private static LayoutInflater inflater=null;

    public CountryAdapter(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList.addAll(countryList);
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = inflater.inflate(R.layout.country_list_item, null);
        }

        Country country = countryList.get(position);
        TextView country_name = (TextView) listItemView.findViewById(R.id.country_name);
        country_name.setText(country.getCountry());



        return listItemView;
    }


    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        countryList.clear();
        if (charText.length()==0){
            countryList.addAll(arrayList);
        }
        else {
            for (Country country : arrayList){
                if (country.getCountry().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    countryList.add(country);
                }
            }
        }
        notifyDataSetChanged();
    }



}
