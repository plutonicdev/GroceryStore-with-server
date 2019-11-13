package com.quintus.labs.grocerystore.util;

import com.quintus.labs.grocerystore.api.clients.RestClient;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class Utils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "LoginFragment";
    public static final String SignUp_Fragment = "SignUpFragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String CategoryImage = RestClient.BASE_URL + "assets/images/ProductImage/category/";
    public static final String ProductImage = RestClient.BASE_URL + "assets/images/ProductImage/product/";


}
