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
    Spinner citySpinner, stateSpinner, countrySpinner;
    RadioButton homeType, workType;
    RadioGroup radioGroup;
    ArrayList countryArray = new ArrayList();
    ArrayList stateArray = new ArrayList();
    ArrayList cityArray = new ArrayList();
    List<Country> countryList = null;
    List<AddAddressListResponse> addressList = new ArrayList<>();
    //    List<State> stateList = new ArrayList<>();
//    List<City> cityList = new ArrayList<>();
    String _city, _name, _email, _phone, _address, _state, _zip, _address_type, _country, userString;
    EditText name, email, mobile, address, zip;

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

    private void getCity() {
        Call<City> call = RestClient.getRestService(getContext()).city(token);
        call.enqueue((new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city = response.body();
                if (city != null) {
                    if (response.code() == 200) {
//                        countryList = response.body().getCountryList();
                        city.getCity();
                        citySpinner.getAdapter();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        }));
    }

    private void getState() {
        Call<State> call = RestClient.getRestService(getContext()).state(token);
        call.enqueue((new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                State state = response.body();
                if (state != null) {
                    if (response.code() == 200) {
//                        countryList = response.body().getCountryList();
                        state.getState();
                        stateSpinner.getAdapter();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {

            }
        }));
    }

//    private void getCountry() {
//        Call<List<Country>> call = RestClient.getRestService(getContext()).country(token);
//        call.enqueue((new Callback<List<Country>>() {
//            @Override
//            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
//                List<Country> countryResponse = response.body();
//                if (countryResponse != null) {
//                    if (response.code() == 200) {
//                        countryList = countryResponse;
//                       // countrySpinner.getAdapter();
////                        if (response.body() != null) {
////                    List<Country> country = response.body();
////                    if (response.code() == 200) {
////                        country.();
////                        countrySpinner.getAdapter();
////                        countryList = countryResponse.getCountryList();
//
//                    }
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Country>> call, Throwable t) {
//
//            }
//        }));
//    }

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
        zip = v.findViewById(R.id.sa_zip);

        localStorage = new LocalStorage(getContext());
        gson = new Gson();
        userString = localStorage.getUserLogin();

        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = localStorage.getApiKey();
        progress = v.findViewById(R.id.progress_bar);

        getAddressList();


        getCountry();

        countryArray.add(0, "select List<Country>");
        ArrayAdapter countryAdapter
                = new ArrayAdapter(getActivity(), R.layout.spinnertextview, countryArray);
        countryAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        stateArray.add(0, "select State");
        ArrayAdapter stateAdapter
                = new ArrayAdapter(getActivity(), R.layout.spinnertextview, stateArray);
        stateAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);
        getState();
        cityArray.add(0, "select City");
        ArrayAdapter cityAdapter
                = new ArrayAdapter(getActivity(), R.layout.spinnertextview, cityArray);
        cityAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        getCity();

        Log.d("User String : ", userString);
//        if (user != null) {
//            name.setText(user.getName());
//            email.setText(user.getEmail());
//            mobile.setText(user.getMobile());
//            zip.setText(user.getZip());
//            address.setText(user.getAddress());
//        }

//        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    _country = countryList.get(position - 1).();
//                } else {
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    _state = stateList.get(position - 1).getState();
//                } else {
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    _city = cityList.get(position - 1).getCity();
//                } else {
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });


//        init();
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
                } else if (_country == null || _country.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_country), Toast.LENGTH_LONG).show();
                } else if (_state == null || _state.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_state), Toast.LENGTH_LONG).show();
                } else if (_city == null || _city.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_city), Toast.LENGTH_LONG).show();
                } else if (_address_type == null || _address_type.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_address_type), Toast.LENGTH_LONG).show();
                } else if (_zip.length() == 0) {
                    zip.setError("Enter your Zip Code");
                    zip.requestFocus();
                } else {
                    User userAddress = new User(_name, _phone, _email, _address, _state, _address_type, _country, _city, _zip);
                    String user_address = gson.toJson(userAddress);
                    localStorage.createUserLoginSession(user_address);
                    saveUserAddress(userAddress);


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
//            zip.setText(user.getZip());
                            address.setText(addressList.get(0).getAddress());
                            if (addressList.get(0).getAddressType().equalsIgnoreCase("HOME")) {
                                homeType.setChecked(true);
                            } else {
                                if (addressList.get(0).getAddressType().equalsIgnoreCase("WORK")) {
                                    workType.setChecked(true);
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


    private void saveUserAddress(final User userAddress) {
        showProgressDialog();
        Call<AddAddress> call = RestClient.getRestService(getContext()).addAddress(userAddress);
        call.enqueue(new Callback<AddAddress>() {
            @Override
            public void onResponse(Call<AddAddress> call, Response<AddAddress> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 200) {
                        user.setAddress(userAddress.getAddress());
                        user.setState(userAddress.getState());
                        // user.setCountry(userAddress.getCountry());
                        user.setCity(userAddress.getCity());
                        user.setAddress_type(userAddress.getAddress_type());
                        user.setZip(userAddress.getZip());
                        userString = gson.toJson(user);
                        localStorage.createUserLoginSession(userString);
//                        pinAddress();
//                        for (int i = 0; i < countryList.size(); i++) {
//                            countryArray.add(countryList.get(i).().toString());
//                        }

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                        ft.replace(R.id.content_frame, new PaymentFragment());
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


    public void pinAddress() {
        Call<Pin> call = RestClient.getRestService(getContext()).pin(token);
        call.enqueue(new Callback<Pin>() {
            @Override
            public void onResponse(Call<Pin> call, Response<Pin> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {
                    if (response.code() == 200) {

                    } else {
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }

                hideProgressDialog();
            }


            @Override
            public void onFailure(Call<Pin> call, Throwable t) {

            }
        });


    }


//    private void init() {
//        stringArrayState = new ArrayList<String>();
//        stringArrayCity = new ArrayList<String>();
//        stringArrayCountry = new ArrayList<String>();
//
//        //set city adapter
//        final ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview, stringArrayCity);
//        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        citySpinner.setAdapter(adapterCity);
//
//        if (user.getCity() != null) {
//            int selectionPosition = adapterCity.getPosition(user.getCity());
//            citySpinner.setSelection(selectionPosition);
//        }
//
//        //Get state json value from assets folder
//        try {
//            JSONObject obj = new JSONObject(loadJSONFromAssetState());
//            JSONArray m_jArry = obj.getJSONArray("statelist");
//
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(i);
//
//                String state = jo_inside.getString("State");
//                String id = jo_inside.getString("id");
//
//                stringArrayState.add(state);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview, stringArrayState);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        stateSpinner.setAdapter(adapter);
//        if (user.getState() != null) {
//            int selectionPosition = adapter.getPosition(user.getState());
//            stateSpinner.setSelection(selectionPosition);
//        }
//
//
//        //state spinner item selected listner with the help of this we get selected value
//
//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Object item = parent.getItemAtPosition(position);
//                String Text = stateSpinner.getSelectedItem().toString();
//
//
//                spinnerStateValue = String.valueOf(stateSpinner.getSelectedItem());
//                _state = spinnerStateValue;
//                stringArrayCity.clear();
//
//                try {
//                    JSONObject obj = new JSONObject(loadJSONFromAssetCity());
//                    JSONArray m_jArry = obj.getJSONArray("citylist");
//
//                    for (int i = 0; i < m_jArry.length(); i++) {
//                        JSONObject jo_inside = m_jArry.getJSONObject(i);
//                        String state = jo_inside.getString("State");
//                        String cityid = jo_inside.getString("id");
//
//                        if (spinnerStateValue.equalsIgnoreCase(state)) {
//                            _city = jo_inside.getString("city");
//                            stringArrayCity.add(_city);
//                        }
//
//                    }
//
//                    //notify adapter city for getting selected value according to state
//                    adapterCity.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview, stringArrayState);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        countrySpinner.setAdapter(adapterCountry);
//        if (user.() != null) {
//            int selectionPosition = adapter.getPosition(user.getState());
//            countrySpinner.setSelection(selectionPosition);
//        }
//
//
//        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String spinnerCityValue = String.valueOf(citySpinner.getSelectedItem());
//                Log.e("SpinnerCityValue", spinnerCityValue);
//
//                _city = spinnerCityValue;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Object item = parent.getItemAtPosition(position);
//                String Text = stateSpinner.getSelectedItem().toString();
//
//
//                spinnerCountryValue = String.valueOf(stateSpinner.getSelectedItem());
//                _country = spinnerCountryValue;
//                stringArrayState.clear();
//
//                try {
//                    JSONObject obj = new JSONObject(loadJSONFromAssetState());
//                    JSONArray m_jArry = obj.getJSONArray("statelist");
//
//                    for (int i = 0; i < m_jArry.length(); i++) {
//                        JSONObject jo_inside = m_jArry.getJSONObject(i);
//                        String state = jo_inside.getString("State");
//                        String cityid = jo_inside.getString("id");
//
//                        if (spinnerCountryValue.equalsIgnoreCase(state)) {
//                            _city = jo_inside.getString("city");
//                            stringArrayState.add(_state);
//                        }
//
//                    }
//
//                    //notify adapter city for getting selected value according to state
//                    adapterCity.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//
//    }
//
//
//    public String loadJSONFromAssetState() {
//        String json = null;
//        try {
//            InputStream is = getContext().getAssets().open("state.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, StandardCharsets.UTF_8);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    public String loadJSONFromAssetCity() {
//        String json = null;
//        try {
//            InputStream is = getContext().getAssets().open("cityState.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, StandardCharsets.UTF_8);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    public String loadJSONFromAssetCountry() {
//        String json = null;
//        try {
//            InputStream is = getContext().getAssets().open("countryState.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, StandardCharsets.UTF_8);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }


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
                        countryList = countryResult;


                    }

                }


            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });


    }
}
