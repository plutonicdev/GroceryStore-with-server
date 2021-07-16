package com.quintus.labs.grocerystore.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.AddAddressListResponse;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.model.UserResult;
import com.quintus.labs.grocerystore.util.Utils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class AddressFragment extends Fragment {

    Context context;
    TextView txt_pyment;
    Spinner citySpinner, stateSpinner, countrySpinner;
    RadioButton homeType, workType;
    RadioGroup radioGroup;
    ArrayList<String> stringArrayState;
    ArrayList<String> stringArrayCity;
    ArrayList<String> stringArrayCountry;
    String spinnerStateValue, spinnerCountryValue, _city, _name, _email, _phone, _address, _state, _zip, _address_type, _country, userString;
    EditText name, email, mobile, address, zip;

    LocalStorage localStorage;
    Gson gson;
    User user;
    View progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_address, container, false);
        citySpinner = v.findViewById(R.id.citySpinner);
        countrySpinner = v.findViewById(R.id.countrySpinner);
        stateSpinner = v.findViewById(R.id.stateSpinner);
        getCategory();
        name = v.findViewById(R.id.sa_name);
        email = v.findViewById(R.id.sa_email);
        mobile = v.findViewById(R.id.sa_mobile);
        address = v.findViewById(R.id.sa_address);
        radioGroup = v.findViewById(R.id.radioGroup);
        homeType = v.findViewById(R.id.homeType);
        workType = v.findViewById(R.id.workType);
        zip = v.findViewById(R.id.sa_zip);

        localStorage = new LocalStorage(getContext());
        gson = new Gson();
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);
        progress = v.findViewById(R.id.progress_bar);

        Log.d("User String : ", userString);
        if (user != null) {
            name.setText(user.getFname());
            email.setText(user.getEmail());
            mobile.setText(user.getMobile());
            zip.setText(user.getZip());
            address.setText(user.getAddress());
        }


        init();
        context = container.getContext();
        txt_pyment = v.findViewById(R.id.txt_pyment);

        txt_pyment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _name = name.getText().toString();
                _email = email.getText().toString();
                _phone = mobile.getText().toString();
                _address = address.getText().toString();

                _zip = zip.getText().toString();
                Pattern p = Pattern.compile(Utils.regEx);

                Matcher m = p.matcher(_email);


                if (_name.length() == 0) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (_email.length() == 0) {
                    email.setError("Enter email");
                    email.requestFocus();
                } else if (!m.find()) {
                    email.setError("Enter Correct email");
                    email.requestFocus();
                } else if (_phone.length() == 0) {
                    mobile.setError("Enter mobile Number");
                    mobile.requestFocus();
                } else if (_address.length() == 0) {
                    address.setError("Enter your Address");
                    address.requestFocus();
                } else if (_zip.length() == 0) {
                    zip.setError("Enter your Zip Code");
                    zip.requestFocus();
                } else {
                    User userAddress = new User(user.getId(), _name, _phone, _email, _address, _state, _address_type, _country, _city, _zip, user.getToken());
                    String user_address = gson.toJson(userAddress);
                    localStorage.createUserLoginSession(user_address);
                    saveUserAddress(userAddress);


                }


            }
        });
        return v;
    }


    private void getCategory() {

    }

    private void saveUserAddress(final User userAddress) {
        showProgressDialog();
//        Call<AddAddressListResponse> call = RestClient.getRestService(getContext()).updateUser(userAddress);
//        call.enqueue(new Callback<AddAddressListResponse>() {
//            @Override
//            public void onResponse(Call<AddAddressListResponse> call, Response<AddAddressListResponse> response) {
//                Log.d("Response :=>", response.body() + "");
//                if (response != null) {
//                    if (response.code() == 200) {
//                        user.setAddress(userAddress.getAddress());
//                        user.setState(userAddress.getState());
//                        user.setCountry(userAddress.getCountry());
//                        user.setCity(userAddress.getCity());
//                        user.setAddress_type(userAddress.getAddress_type());
//                        user.setZip(userAddress.getZip());
//                        user.setZip(userAddress.getZip());
//                        userString = gson.toJson(user);
//                        localStorage.createUserLoginSession(userString);
//
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
//                        ft.replace(R.id.content_frame, new PaymentFragment());
//                        ft.commit();
//                    } else {
//                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                hideProgressDialog();
//            }
//
//            @Override
//            public void onFailure(Call<AddAddressListResponse> call, Throwable t) {
//
//            }
//        });

    }

    private void init() {
        stringArrayState = new ArrayList<String>();
        stringArrayCity = new ArrayList<String>();
        stringArrayCountry = new ArrayList<String>();

        //set city adapter
        final ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview, stringArrayCity);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapterCity);

        if (user.getCity() != null) {
            int selectionPosition = adapterCity.getPosition(user.getCity());
            citySpinner.setSelection(selectionPosition);
        }

        //Get state json value from assets folder
        try {
            JSONObject obj = new JSONObject(loadJSONFromAssetState());
            JSONArray m_jArry = obj.getJSONArray("statelist");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String state = jo_inside.getString("State");
                String id = jo_inside.getString("id");

                stringArrayState.add(state);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview, stringArrayState);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        if (user.getState() != null) {
            int selectionPosition = adapter.getPosition(user.getState());
            stateSpinner.setSelection(selectionPosition);
        }


        //state spinner item selected listner with the help of this we get selected value

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                String Text = stateSpinner.getSelectedItem().toString();


                spinnerStateValue = String.valueOf(stateSpinner.getSelectedItem());
                _state = spinnerStateValue;
                stringArrayCity.clear();

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAssetCity());
                    JSONArray m_jArry = obj.getJSONArray("citylist");

                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        String state = jo_inside.getString("State");
                        String cityid = jo_inside.getString("id");

                        if (spinnerStateValue.equalsIgnoreCase(state)) {
                            _city = jo_inside.getString("city");
                            stringArrayCity.add(_city);
                        }

                    }

                    //notify adapter city for getting selected value according to state
                    adapterCity.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview, stringArrayState);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapterCountry);
        if (user.getCountry() != null) {
            int selectionPosition = adapter.getPosition(user.getState());
            countrySpinner.setSelection(selectionPosition);
        }


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerCityValue = String.valueOf(citySpinner.getSelectedItem());
                Log.e("SpinnerCityValue", spinnerCityValue);

                _city = spinnerCityValue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                String Text = stateSpinner.getSelectedItem().toString();


                spinnerCountryValue = String.valueOf(stateSpinner.getSelectedItem());
                _country = spinnerCountryValue;
                stringArrayState.clear();

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAssetState());
                    JSONArray m_jArry = obj.getJSONArray("statelist");

                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        String state = jo_inside.getString("State");
                        String cityid = jo_inside.getString("id");

                        if (spinnerCountryValue.equalsIgnoreCase(state)) {
                            _city = jo_inside.getString("city");
                            stringArrayState.add(_state);
                        }

                    }

                    //notify adapter city for getting selected value according to state
                    adapterCity.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    public String loadJSONFromAssetState() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("state.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetCity() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("cityState.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetCountry() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("countryState.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Address");
    }


    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }

}
