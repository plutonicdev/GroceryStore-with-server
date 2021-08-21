package com.quintus.labs.grocerystore.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.api.clients.RestClient;
import com.quintus.labs.grocerystore.model.AddAddress;
import com.quintus.labs.grocerystore.model.AddAddressListResponse;
import com.quintus.labs.grocerystore.model.City;
import com.quintus.labs.grocerystore.model.Country;

import com.quintus.labs.grocerystore.model.Pin;
import com.quintus.labs.grocerystore.model.Country;
import com.quintus.labs.grocerystore.model.AddAddressListResponse;
import com.quintus.labs.grocerystore.model.State;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.util.Utils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import java.util.ArrayList;
import java.util.List;
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
public class AddressFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    Context context;
    TextView txt_pyment;
    Spinner citySpinner, stateSpinner, countrySpinner,zipSpinner;
    RadioButton homeType, workType;
    RadioGroup radioGroup;
    ArrayList countryArray = new ArrayList();
    ArrayList stateArray = new ArrayList();
    ArrayList cityArray = new ArrayList();
    ArrayList pinArray = new ArrayList();
    List<Country> countryList = null;
    List<State> stateList = null;
    List<City> cityList = null;
    List<Pin> pinList = null;
    List<AddAddressListResponse> addressList = new ArrayList<>();

    String _city, _name, _email, _phone, _address, _state, _pin, _address_type, _country, userString;
    EditText name, email, mobile, address;
    int country_id, state_id,city_id, pin_id,address_id;

    LocalStorage localStorage;
    AddAddressListResponse addAddressListResponse;
    String token;
    Gson gson;
    User user;
    View progress;

    String type = "add";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_address, container, false);
        citySpinner = v.findViewById(R.id.citySpinner);
        countrySpinner = v.findViewById(R.id.countrySpinner);
        stateSpinner = v.findViewById(R.id.stateSpinner);
        name = v.findViewById(R.id.sa_name);
        email = v.findViewById(R.id.sa_email);
        mobile = v.findViewById(R.id.sa_mobile);
        address = v.findViewById(R.id.sa_address);
        radioGroup = v.findViewById(R.id.radioGroup);
        homeType = v.findViewById(R.id.homeType);
        workType = v.findViewById(R.id.workType);
        zipSpinner = v.findViewById(R.id.zipSpinner);

        localStorage = new LocalStorage(getContext());
        gson = new Gson();
        userString = localStorage.getUserLogin();

        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = localStorage.getApiKey();
        progress = v.findViewById(R.id.progress_bar);
        txt_pyment = v.findViewById(R.id.txt_pyment);
        context = container.getContext();

        getAddressList();

        getCountry();
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    _country = countryList.get(position - 1).getCountry();
                   country_id = countryList.get(position - 1).getId();
                    getState(country_id);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    _state = stateList.get(position - 1).getState();
                    state_id = stateList.get(position - 1).getId();
                    getCity(state_id);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


   citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  _education = education[position];
                if (position > 0) {
                    _city = cityList.get(position - 1).getCity();
                    city_id = cityList.get(position - 1).getId();
                    getPin(city_id);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


   zipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  _education = education[position];
                if (position > 0) {
                    _pin = pinList.get(position - 1).getPinCode();
                    pin_id = pinList.get(position - 1).getId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        Log.d("User String : ", userString);




        txt_pyment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
//                ft.replace(R.id.content_frame, new ConfirmFragment ());
//                ft.commit();
                _name = name.getText().toString();
                _email = email.getText().toString();
                _phone = mobile.getText().toString();
                _address = address.getText().toString();

                if(homeType.isChecked()){
                    _address_type="HOME";
                }else{
                    _address_type="WORK";

                }

              //  _zip = zip.getText().toString();
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
                } else if (_country == null || _country.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_country), Toast.LENGTH_LONG).show();
                } else if (_state == null || _state.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_state), Toast.LENGTH_LONG).show();
                } else if (_city == null || _city.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_city), Toast.LENGTH_LONG).show();
                } else if (_address_type == null || _address_type.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_address_type), Toast.LENGTH_LONG).show();
//                } else if (_zip.length() == 0) {
//                    zip.setError("Enter your Zip Code");
//                    zip.requestFocus();
                } else {
                    AddAddress userAddress = new AddAddress(_name, _phone, _email, _address, _address_type,country_id,city_id,pin_id,state_id);
                    String user_address = gson.toJson(userAddress);
                    localStorage.createUserLoginSession(user_address);
                    if(type.equalsIgnoreCase("add")) {
                        saveUserAddress(userAddress);

                    }else{

                        updateUserAddress(userAddress);

                    }


                }


            }
        });
        return v;

    }

    private void getAddressList() {
        showProgressDialog();
        Call<List<AddAddressListResponse>> call = RestClient.getRestService(getContext()).getAddressList(token);
        call.enqueue(new Callback<List<AddAddressListResponse>>() {
            @Override
            public void onResponse(Call<List<AddAddressListResponse>> call, Response<List<AddAddressListResponse>> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    addressList = response.body();
                    if (response.code() == 200) {

                        if (addressList.size() > 0) {
                            type = "update";

                            name.setText(addressList.get(0).getName());
                            email.setText(addressList.get(0).getEmail());
                            mobile.setText(addressList.get(0).getPhone());
//                            _country = addressList.get(0).getCountry().getCountry();
//                            country_id =  addressList.get(0).getCountry().getId();
//
//                            _state = addressList.get(0).getState().getState();
//                            state_id = addressList.get(0).getState().getId();
//
//                            _city = addressList.get(0).getCity().getCity();
//                            city_id = addressList.get(0).getCity().getId();
//
//                            _pin = addressList.get(0).getPin().getPinCode();
//                            pin_id = addressList.get(0).getPin().getId();

                          address_id=addressList.get(0).getId();
                            address.setText(addressList.get(0).getAddress());
                            if (addressList.get(0).getAddressType().equalsIgnoreCase("HOME")) {
                                homeType.setChecked(true);
                                _address_type="HOME";
                            } else {
                                if (addressList.get(0).getAddressType().equalsIgnoreCase("WORK")) {
                                    workType.setChecked(true);
                                    _address_type="WORK";
                                }
                            }

                        } else {
                            type = "add";
                        }


                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<AddAddressListResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                hideProgressDialog();

            }
        });

    }


    private void saveUserAddress(final AddAddress userAddress) {
        showProgressDialog();
        Call<AddAddress> call = RestClient.getRestService(getContext()).addAddress(token,userAddress);
        call.enqueue(new Callback<AddAddress>() {
            @Override
            public void onResponse(Call<AddAddress> call, Response<AddAddress> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 201) {
                        address_id=response.body().getId();
//                        user.setAddress(userAddress.getAddress());
//                        user.setState(userAddress.getState());
//                        // user.setCountry(userAddress.getCountry());
//                        user.setCity(userAddress.getCity());
//                        user.setAddress_type(userAddress.getAddress_type());
//                        user.setZip(userAddress.getZip());
//                        userString = gson.toJson(user);
//                        localStorage.createUserLoginSession(userString);
//                        pinAddress();
//                        for (int i = 0; i < countryList.size(); i++) {
//                            countryArray.add(countryList.get(i).().toString());
//                        }

                        localStorage.setAddressId(String.valueOf(response.body().getId()));

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                        ft.replace(R.id.content_frame, new ConfirmFragment ());
                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<AddAddress> call, Throwable t) {

            }
        });

    }



    private void updateUserAddress(final AddAddress userAddress) {
        showProgressDialog();
        Call<AddAddress> call = RestClient.getRestService(getContext()).updateAddress(token,address_id,userAddress);
        call.enqueue(new Callback<AddAddress>() {
            @Override
            public void onResponse(Call<AddAddress> call, Response<AddAddress> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 200) {
                        address_id=response.body().getId();
//                        user.setAddress(userAddress.getAddress());
//                        user.setState(userAddress.getState());
//                        // user.setCountry(userAddress.getCountry());
//                        user.setCity(userAddress.getCity());
//                        user.setAddress_type(userAddress.getAddress_type());
//                        user.setZip(userAddress.getZip());
//                        userString = gson.toJson(user);
//                        localStorage.createUserLoginSession(userString);
//                        pinAddress();
//                        for (int i = 0; i < countryList.size(); i++) {
//                            countryArray.add(countryList.get(i).().toString());
//                        }

                        localStorage.setAddressId(String.valueOf(response.body().getId()));

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                        ft.replace(R.id.content_frame, new ConfirmFragment ());
                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<AddAddress> call, Throwable t) {

            }
        });

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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getCountry() {

        Call<List<Country>> call = RestClient.getRestService(getContext()).getcountry();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    List<Country> countryResult = response.body();
                    if (response.code() == 200) {
                        countryList=countryResult;
                        for (int i = 0; i < countryResult.size(); i++) {
                            countryArray.add(countryResult.get(i).getCountry());
                        }

                        addCountryToSpinner();


                    }

                }


            }

            private void addCountryToSpinner() {
                countryArray.add(0, "select country");
                ArrayAdapter countryAdapter
                        = new ArrayAdapter(getActivity(), R.layout.spinnertextview, countryArray);
                countryAdapter.setDropDownViewResource(
                        android.R.layout
                                .simple_spinner_dropdown_item);
                countrySpinner.setAdapter(countryAdapter);
                countrySpinner.setSelection(0);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });


    }

    private void getState(int id) {
        Call<List<State>> call = RestClient.getRestService(getContext()).getState(id);
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    List<State> stateResult = response.body();
                    if (response.code() == 200) {
                        stateList=stateResult;
                        for (int i = 0; i < stateResult.size(); i++) {
                            stateArray.add(stateResult.get(i).getState());
                        }

                        addStateToSpinner();


                    }

                }


            }

            private void addStateToSpinner() {
                stateArray.add(0, "select state");
                ArrayAdapter stateAdapter
                        = new ArrayAdapter(getActivity(), R.layout.spinnertextview, stateArray);
                stateAdapter.setDropDownViewResource(
                        android.R.layout
                                .simple_spinner_dropdown_item);
                stateSpinner.setAdapter(stateAdapter);
                stateSpinner.setSelection(0);
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {

            }
        });


    }

    private void getCity(int state_id) {
        Call<List<City>> call = RestClient.getRestService(getContext()).getcity(state_id);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    List<City> cityResult = response.body();
                    if (response.code() == 200) {
                        cityList=cityResult;
                        for (int i = 0; i < cityResult.size(); i++) {
                            cityArray.add(cityResult.get(i).getCity());
                        }

                        addCityToSpinner();


                    }

                }


            }

            private void addCityToSpinner() {
                cityArray.add(0, "select city");
                ArrayAdapter cityAdapter
                        = new ArrayAdapter(getActivity(), R.layout.spinnertextview, cityArray);
                cityAdapter.setDropDownViewResource(
                        android.R.layout
                                .simple_spinner_dropdown_item);
                citySpinner.setAdapter(cityAdapter);
                citySpinner.setSelection(0);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });


    }

        public void getPin(int id) {
        Call<List<Pin>> call = RestClient.getRestService(getContext()).getzip(id);
        call.enqueue(new Callback<List<Pin>>() {
            @Override
            public void onResponse(Call<List<Pin>> call, Response<List<Pin>> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    List<Pin> pinResult = response.body();
                    if (response.code() == 200) {
                        pinList=pinResult;
                        for (int i = 0; i < pinResult.size(); i++) {
                            pinArray.add(pinResult.get(i).getPinCode());
                        }

                        addPinToSpinner();


                    }

                }


            }

            private void addPinToSpinner() {
                pinArray.add(0, "select Zip");
                ArrayAdapter pinAdapter
                        = new ArrayAdapter(getActivity(), R.layout.spinnertextview, pinArray);
                pinAdapter.setDropDownViewResource(
                        android.R.layout
                                .simple_spinner_dropdown_item);
                zipSpinner.setAdapter(pinAdapter);
                zipSpinner.setSelection(0);
            }


            @Override
            public void onFailure(Call<List<Pin>> call, Throwable t) {

            }
        });


    }




}
