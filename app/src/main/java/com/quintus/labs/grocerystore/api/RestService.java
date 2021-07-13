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
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
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
    String tenant_id = "1";
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

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("config/slider_images/")
    Call<Banners> bannerList();

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/categories/?page={page}&page_size={pageSize}")
    Call<CategoryResult> allCategory(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("vouchers/list/?page={page}&page_size={pageSize}")
    Call<CategoryResult> allOffers(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/popular_categories/")
    Call<CategoryResult> popularCategory(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/popular_products/?page={page}&page_size={pageSize}")
    Call<ProductResult> popularProducts(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/product/recently_added/?page={page}&page_size={pageSize}")
    Call<ProductResult> newProducts(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/category/${id}/products/?page={page}&page_size={pageSize}")
    Call<ProductResult> allProducts(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/product/{id}/details/")
    Call<ProductResult> productDetails(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/wishlist/")
    Call<ProductResult> getFavouriteProducts(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/currency_lists/")
    Call<ProductResult> currencyData(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("users/address/list/")
    Call<ProductResult> addressList(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("users/address/default/")
    Call<ProductResult> defaultAddress(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("users/address/create/")
    Call<ProductResult> addAddress(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @PUT("users/address/{id}/update/")
    Call<ProductResult> updateAddress(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @DELETE("users/address/{id}/delete/")
    Call<ProductResult> deleteAddress(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("catalog/search/?search={text}&page=1&page_size=100")
    Call<ProductResult> searchProduct(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("catalog/add_update_wishlist/")
    Call<ProductResult> addfavouriteProduct(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("orders/list/?page={page}&page_size={pageSize}")
    Call<ProductResult> getOrderDetails(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("orders/{id}/details/")
    Call<ProductResult> getSingleOrderDetails(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/countries/")
    Call<ProductResult> country(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/country/{id}/states/")
    Call<ProductResult> state(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/state/{id}/cities/")
    Call<ProductResult> city(@Body Token token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/pin/")
    Call<ProductResult> pin(@Body Token token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("locations/city/{id}/pin/")
    Call<ProductResult> zip(@Body Token token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("vouchers/voucher_validity/")
    Call<ProductResult> checkVoucher(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("payments/initiate/")
    Call<ProductResult> initatePayment(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("payments/{id}/status_update/")
    Call<ProductResult> updatePayment(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("carts/checkout/")
    Call<ProductResult> createOrder(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("carts/list/")
    Call<ProductResult> getCartList(@Body Token token);


    @Headers("X-TENANT-ID:" + tenant_id)
    @POST("carts/create_update/")
    Call<ProductResult> addToCart(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @DELETE("carts/remove_product/")
    Call<ProductResult> removeFromCart(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @PATCH("baskets/{basketID}/lines/{lineID}/")
    Call<ProductResult> cartAddRemoveQuantity(@Body Token token);

    @Headers("X-TENANT-ID:" + tenant_id)
    @GET("config/promotion_images/?page_size=4&page=1")
    Call<ProductResult> getAdvertisementBanners(@Body Token token);


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
