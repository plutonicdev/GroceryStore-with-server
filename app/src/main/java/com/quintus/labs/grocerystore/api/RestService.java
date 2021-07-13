package com.quintus.labs.grocerystore.api;


import com.quintus.labs.grocerystore.model.Banners;
import com.quintus.labs.grocerystore.model.Category;
import com.quintus.labs.grocerystore.model.CategoryResult;
import com.quintus.labs.grocerystore.model.Order;
import com.quintus.labs.grocerystore.model.OrderItem;
import com.quintus.labs.grocerystore.model.OrdersResult;
import com.quintus.labs.grocerystore.model.PlaceOrder;
import com.quintus.labs.grocerystore.model.ProductResult;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.model.UserResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * RetrofitExample
 * https://github.com/quintuslabs/RetrofitExample
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public interface RestService {

//    @POST("api/v1/register")
//    Call<UserResult> register(@Body User user);

    @POST("users/register/")
    Call<UserResponse> register(@Body User user);

//    @POST("api/v1/login")
//    Call<UserResult> login(@Body User user);

    @POST("users/login/")
    Call<UserResponse> login(@Body User user);

//    @POST("api/v1/forgot_password")
//    Call<UserResult> forgotPassword(@Body User user);

    @POST("users/forget_password/")
    Call<UserResponse> forgotPassword(@Body User user);

//    @POST("api/v1/reset_password")
//    Call<UserResult> resetPassword(@Body User user);

    @POST("users/confirm_forget_password/")
    Call<UserResponse> resetPassword(@Body User user);

    @POST("users/verify_otp/")
    Call<UserResponse> otpVarification(@Body User user);

    @GET("config/slider_images/")
    Call<Banners> bannerList();

    @GET("catalog/categories/?page={page}&page_size={pageSize}")
    @POST("/users/verify_resend_otp/")
    Call<UserResponse> resendOTP(@Body User user);

    @POST("api/v1/allcategory")
    Call<CategoryResult> allCategory(@Body Token token);

    @GET("vouchers/list/?page={page}&page_size={pageSize}")
    Call<CategoryResult> allOffers(@Body Token token);

    @GET("catalog/popular_categories/")
    Call<CategoryResult> popularCategory(@Body Token token);

    @GET("catalog/popular_products/?page={page}&page_size={pageSize}")
    Call<ProductResult> popularProducts(@Body Token token);

    @GET("catalog/product/recently_added/?page={page}&page_size={pageSize}")
    Call<ProductResult> newProducts(@Body Token token);

    @GET("catalog/category/${id}/products/?page={page}&page_size={pageSize}")
    Call<ProductResult> allProducts(@Body Token token);

    @GET("catalog/product/{id}/details/")
    Call<ProductResult> productDetails(@Body Token token);

    @GET("catalog/wishlist/")
    Call<ProductResult> getFavouriteProducts(@Body Token token);

    @GET("catalog/currency_lists/")
    Call<ProductResult> currencyData(@Body Token token);

    @GET("users/address/list/")
    Call<ProductResult> addresList(@Body Token token);

    @POST("users/address/create/")
    Call<ProductResult> addAddress(@Body Token token);

    @PUT("users/address/{id}/update/")
    Call<ProductResult> updateAddress(@Body Token token);

    @DELETE("users/address/{id}/delete/")
    Call<ProductResult> deleteAddress(@Body Token token);

    @GET("catalog/search/?search={text}&page=1&page_size=100")
    Call<ProductResult> searchProduct(@Body Token token);

    @POST("catalog/add_update_wishlist/")
    Call<ProductResult> addfavouriteProduct(@Body Token token);

    @GET("orders/list/?page={page}&page_size={pageSize}")
    Call<ProductResult> getOrderDetails(@Body Token token);

    @GET("orders/{id}/details/")
    Call<ProductResult> getSingleOrderDetails(@Body Token token);

  @GET("locations/countries/")
    Call<ProductResult> country(@Body Token token);

  @GET("locations/country/{id}/states/")
    Call<ProductResult> state(@Body Token token);

  @GET("locations/state/{id}/cities/")
    Call<ProductResult> city(@Body Token token);





    /* old apis */


    @POST("api/v1/getlist")
    Call<ProductResult> getCategoryProduct(@Body Category category);

    @POST("api/v1/placeorder")
    Call<OrdersResult> confirmPlaceOrder(@Body PlaceOrder placeOrder);

    @POST("api/v1/orderDetails")
    Call<OrdersResult> orderDetails(@Body Order order);

    @POST("api/v1/updateUser")
    Call<UserResult> updateUser(@Body User user);

    @GET("api/v1/product/search")
    Call<ProductResult> searchProduct(@Query("s") String search);

    @POST("api/v1/singleOrderDetails")
    Call<OrdersResult> getOrderItems(@Body OrderItem orderItem);
}
