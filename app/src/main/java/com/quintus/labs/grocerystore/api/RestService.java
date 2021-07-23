package com.quintus.labs.grocerystore.api;


import com.quintus.labs.grocerystore.model.AddAddress;
import com.quintus.labs.grocerystore.model.AddAddressListResponse;
import com.quintus.labs.grocerystore.model.AdvertisementBanner;
import com.quintus.labs.grocerystore.model.Banners;
import com.quintus.labs.grocerystore.model.Category;
import com.quintus.labs.grocerystore.model.CategoryResult;
import com.quintus.labs.grocerystore.model.City;
import com.quintus.labs.grocerystore.model.Country;
import com.quintus.labs.grocerystore.model.Currency;
import com.quintus.labs.grocerystore.model.Order;
import com.quintus.labs.grocerystore.model.OrderItem;
import com.quintus.labs.grocerystore.model.OrdersResult;
import com.quintus.labs.grocerystore.model.Pin;
import com.quintus.labs.grocerystore.model.PlaceOrder;
import com.quintus.labs.grocerystore.model.PopularProducts;
import com.quintus.labs.grocerystore.model.ProductDetails;
import com.quintus.labs.grocerystore.model.ProductResult;
import com.quintus.labs.grocerystore.model.State;
import com.quintus.labs.grocerystore.model.Token;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.model.UserResult;
import com.quintus.labs.grocerystore.model.VoucherList;
import com.quintus.labs.grocerystore.model.VoucherListData;
import com.quintus.labs.grocerystore.model.VoucherValidity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * RetrofitExample
 * https://github.com/quintuslabs/RetrofitExample
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public interface RestService {
    String tenant_id = "2";
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

    @POST("users/verify_resend_otp/")
    Call<UserResponse> resendOTP(@Body User user);

  //  @Headers("X-TENANT-ID:" + tenant_id)
    @GET("config/slider_images/")
    Call<List<Banners>> bannerList();

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/categories/")
    Call<CategoryResult> allCategory(@Header("Authorization") String token, @Query("page") int page,@Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("vouchers/list/")
    Call<VoucherListData> allOffers(@Header("Authorization") String token, @Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/popular_categories/")
    Call<CategoryResult> popularCategory(@Header("Authorization") String token,@Query("page") int page,@Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/popular_products/")
    Call<PopularProducts> popularProducts(@Header("Authorization") String token,@Query("page") int page,@Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/product/recently_added/")
    Call<PopularProducts> newProducts(@Header("Authorization") String token,@Query("page") int page,@Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/category/{id}/products/")
    Call<PopularProducts> allProducts(@Header("Authorization") String token, @Path("id") String id, @Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/product/{id}/details/")
    Call<ProductDetails> productDetails(@Header("Authorization") String token,@Path("id") String id);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/wishlist/")
    Call<ProductResult> getFavouriteProducts(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/currency_lists/")
    Call<Currency> currencyData(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("users/address/list/")
    Call<AddAddressListResponse> getAddressList(@Header("token") User user);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("users/address/default/")
    Call<ProductResult> defaultAddress(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("users/address/create/")
    Call<AddAddress> addAddress(@Header("token") User user);

    @Headers("X-TENANT-ID:" + tenant_id)
    @PUT("users/address/{id}/update/")
    Call<ProductResult> updateAddress(@Header("Authorization") String token,@Path("id") String id);

    @Headers("X-TENANT-ID:" + tenant_id)
    @DELETE("users/address/{id}/delete/")
    Call<ProductResult> deleteAddress(@Header("Authorization") String token,@Path("id") String id);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/search/")
    Call<ProductResult> searchProduct(@Header("Authorization") String token,@Query("search") String search,@Query("page") int page,@Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("catalog/add_update_wishlist/")
    Call<ProductResult> addfavouriteProduct(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("orders/list/")
    Call<Order> getOrderDetails(@Header("Authorization") String token,@Query("page") int page,@Query("page_size") int page_size);


    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("orders/{id}/details/")
    Call<ProductResult> getSingleOrderDetails(@Header("Authorization") String token,@Path("id") String id);


    @GET("locations/countries/")
    Call<List<Country>> getcountry();

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/country/{id}/states/")
    Call<State> state(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/state/{id}/cities/")
    Call<City> city(@Header("Authorization") String token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/pin/")
    Call<Pin> pin(@Header("Authorization") String token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/city/{id}/pin/")
    Call<ProductResult> zip(@Header("Authorization") String token,@Path("id") String id);


    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("vouchers/voucher_validity/")
    Call<VoucherValidity> checkVoucher(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("payments/initiate/")
    Call<ProductResult> initatePayment(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("payments/{id}/status_update/")
    Call<ProductResult> updatePayment(@Header("Authorization") String token,@Path("id") String id);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("carts/checkout/")
    Call<ProductResult> createOrder(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("carts/list/")
    Call<ProductResult> getCartList(@Header("Authorization") String token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("carts/create_update/")
    Call<ProductResult> addToCart(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @DELETE("carts/remove_product/")
    Call<ProductResult> removeFromCart(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @PATCH("baskets/{basketID}/lines/{lineID}/")
    Call<ProductResult> cartAddRemoveQuantity(@Header("Authorization") String token,@Path("basketID") String basketID,@Path("lineID") String lineID);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("config/promotion_images/")
    Call<AdvertisementBanner> getAdvertisementBanners(@Header("Authorization") String token,@Query("page") int page,@Query("page_size") int page_size);


    /* old apis */


//    @POST("api/v1/getlist")
//    Call<ProductResult> getCategoryProduct(@Body Category category);
//
//    @POST("api/v1/placeorder")
//    Call<OrdersResult> confirmPlaceOrder(@Body PlaceOrder placeOrder);
//
//    @POST("api/v1/orderDetails")
//    Call<OrdersResult> orderDetails(@Body Order order);
//
////    @POST("api/v1/updateUser")
////    Call<UserResult> updateUser(@Body User user);
//
//    @POST("users/address/create/")
//    Call<AddAddressListResponse> updateUser(@Body User user);

//    @GET("users/address/list/")
//    Call<AddAddressListResponse> getAddressList(@Body User user);


//    @GET("api/v1/product/search")
//    Call<ProductResult> searchProduct(@Query("s") String search);
//
//    @POST("api/v1/singleOrderDetails")
//    Call<OrdersResult> getOrderItems(@Body OrderItem orderItem);
}
