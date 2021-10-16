package com.quintus.labs.grocerystore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.User;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {
    private Context mContext;
    private List<User> addressList;
    private String address_status;

    @Override
    public void onBindViewHolder(final AddressListAdapter.MyViewHolder holder, int position) {
        holder.address_name_display.setText(addressList.get(position).getName());
        holder.address_phone_display.setText(addressList.get(position).getPhone());
        holder.address_email_display.setText(addressList.get(position).getEmail());
        holder.address_display.setText(addressList.get(position).getAddress());
        holder.address_country_display.setText(addressList.get(position).getCountry());
        holder.address_state_display.setText(addressList.get(position).getState());
        holder.address_city_display.setText(addressList.get(position).getCity());
        holder.address_pincode_display.setText(addressList.get(position).getZip());
        holder.address_type_display.setText(addressList.get(position).getAddress_type());
    }


    public AddressListAdapter(Context mContext, List<User> addressList, String address_status) {
        this.mContext = mContext;
        this.addressList = addressList;
        this.address_status= address_status;
    }

    @Override
    public AddressListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_items, parent, false);

        return new AddressListAdapter.MyViewHolder(itemView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address_name_display,address_phone_display,address_email_display,address_display,address_country_display,address_state_display,address_city_display,address_pincode_display,address_type_display;



        public MyViewHolder(View view) {
            super(view);
            address_name_display = view.findViewById(R.id.address_name_display);
            address_phone_display = view.findViewById(R.id.address_phone_display);
            address_email_display = view.findViewById(R.id.address_email_display);
            address_display = view.findViewById(R.id.address_display);
            address_country_display = view.findViewById(R.id.address_country_display);
            address_state_display = view.findViewById(R.id.address_state_display);
            address_city_display = view.findViewById(R.id.address_city_display);
            address_pincode_display = view.findViewById(R.id.address_pincode_display);
            address_type_display = view.findViewById(R.id.address_type_display);

        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
