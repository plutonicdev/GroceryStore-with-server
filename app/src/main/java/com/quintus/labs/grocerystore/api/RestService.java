package com.quintus.labs.grocerystore.api;


import static com.quintus.labs.grocerystore.api.LocalAppConfig.TENANT_ID_HEADER_VALUE;

import com.quintus.labs.grocerystore.model.AddAddress;
import com.quintus.labs.grocerystore.model.AddAddressListResponse;
import com.quintus.labs.grocerystore.model.AddToCart;
import com.quintus.labs.grocerystore.model.AdvertisementBanner;
import com.quintus.labs.grocerystore.model.Banners;
import com.quintus.labs.grocerystore.model.CartDetails;
import com.quintus.labs.grocerystore.model.CategoryResult;
import com.quintus.labs.grocerystore.model.CheckoutDetails;
import com.quintus.labs.grocerystore.model.City;
import com.quintus.labs.grocerystore.model.Country;
import com.quintus.labs.grocerystore.model.Currency;
import com.quintus.labs.grocerystore.model.InitiatePayment;
import com.quintus.labs.grocerystore.model.MessageResponse;
import com.quintus.labs.grocerystore.model.Order;
import com.quintus.labs.grocerystore.model.OrderDetails;
import com.quintus.labs.grocerystore.model.Pin;
import com.quintus.labs.grocerystore.model.PopularProducts;
import com.quintus.labs.grocerystore.model.ProductDetails;
import com.quintus.labs.grocerystore.model.ProductResult;
import com.quintus.labs.grocerystore.model.Products;
import com.quintus.labs.grocerystore.model.State;
import com.quintus.labs.grocerystore.model.Total;
import com.quintus.labs.grocerystore.model.UpdatePayment;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.model.User1;
import com.quintus.labs.grocerystore.model.UserResponse;
import com.quintus.labs.grocerystore.model.Voucher;
import com.quintus.labs.grocerystore.model.VoucherListData;
import com.quintus.labs.grocerystore.model.VoucherResult;
import com.quintus.labs.grocerystore.model.VoucherValidity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/register/")
    Call<UserResponse> register(@Body User user);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/login/")
    Call<UserResponse> login(@Body User user);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/forget_password/")
    Call<UserResponse> forgotPassword(@Body User user);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @PUT("users/confirm_forget_password/")
    Call<MessageResponse> resetPassword(@Body User1 user);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/verify_otp/")
    Call<UserResponse> otpVerification(@Body User user);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/verify_resend_otp/")
    Call<UserResponse> resendOTP(@Body User user);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("config/slider_images/")
    Call<List<Banners>> bannerList();

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/categories/")
    Call<CategoryResult> allCategory(@Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("vouchers/list/")
    Call<VoucherListData> allOffers(@Header("Authorization") String token, @Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/popular_categories/")
    Call<CategoryResult> popularCategory(@Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/popular_products/")
    Call<PopularProducts> popularProducts(@Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/product/recently_added/")
    Call<PopularProducts> newProducts(@Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/category/{id}/products/")
    Call<PopularProducts> allProducts(@Path("id") int id, @Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/product/{id}/details/")
    Call<ProductDetails> productDetails(@Path("id") String id);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/wishlist/")
    Call<ProductResult> getFavouriteProducts(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/currency_lists/")
    Call<Currency> currencyData(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("users/address/list/")
    Call<List<AddAddressListResponse>> getAddressList(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("users/address/default/")
    Call<ProductResult> defaultAddress(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/address/create/")
    Call<AddAddress> addAddress(@Header("Authorization") String token, @Body AddAddress addAddress);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @PUT("users/address/{id}/update/")
    Call<AddAddress> updateAddress(@Header("Authorization") String token, @Path("id") int id, @Body AddAddress addAddress);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @DELETE("users/address/{id}/delete/")
    Call<ProductResult> deleteAddress(@Header("Authorization") String token, @Path("id") String id);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("catalog/search/")
    Call<ProductResult> searchProduct(@Header("Authorization") String token, @Query("search") String search, @Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("catalog/add_update_wishlist/")
    Call<ProductResult> addFavouriteProduct(@Header("Authorization") String token);


    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("vouchers/voucher_validity/")
    Call<VoucherResult> checkVoucher(@Header("Authorization") String token, @Body Voucher voucher);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("orders/list/")
    Call<Order> getOrderDetails(@Header("Authorization") String token, @Query("page") int page, @Query("page_size") int page_size);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("orders/{id}/details/")
    Call<OrderDetails> getSingleOrderDetails(@Header("Authorization") String token, @Path("id") int id);

    @GET("locations/countries/")
    Call<List<Country>> getCountry();

    @GET("locations/country/{id}/states/")
    Call<List<State>> getState(@Path("id") int id);

    @GET("locations/state/{id}/cities/")
    Call<List<City>> getCity(@Path("id") int id);

    @GET("locations/pin/")
    Call<Pin> pin(@Path("id") int id);

    @GET("locations/city/{id}/pin/")
    Call<List<Pin>> getZip(@Path("id") int id);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("users/address/create/")
    Call<VoucherValidity> createAddress();

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @PUT("/users/address/{id}/update/")
    Call<VoucherValidity> updateAddress(@Path("id") int id);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("vouchers/voucher_validity/")
    Call<VoucherValidity> checkVoucher(@Header("Authorization") String token);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("payments/initiate/")
    Call<InitiatePayment> initiatePayment(@Header("Authorization") String token, @Body Total total);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @PUT("payments/{id}/status_update/")
    Call<UpdatePayment> updatePayment(@Header("Authorization") String token, @Path("id") String id, @Body UpdatePayment updatePayment);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("carts/checkout/")
    Call<CheckoutDetails> createOrder(@Header("Authorization") String token, @Body CheckoutDetails checkoutDetails);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("carts/list/")
    Call<CartDetails> getCartList(@Header("Authorization") String token);


    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @POST("carts/create_update/")
    Call<AddToCart> addToCart(@Header("Authorization") String token, @Body AddToCart addtoCart);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @HTTP(method = "DELETE", path = "carts/remove_product/", hasBody = true)
    Call<Void> removeFromCart(@Header("Authorization") String token, @Body Products products);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @PATCH("baskets/{basketID}/lines/{lineID}/")
    Call<ProductResult> cartAddRemoveQuantity(@Header("Authorization") String token, @Path("basketID") String basketID, @Path("lineID") String lineID);

    @Headers("X-TENANT-ID:" + TENANT_ID_HEADER_VALUE)
    @GET("config/promotion_images/")
    Call<AdvertisementBanner> getAdvertisementBanners(@Header("Authorization") String token, @Query("page") int page, @Query("page_size") int page_size);


}
