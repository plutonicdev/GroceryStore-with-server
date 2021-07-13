package com.quintus.labs.grocerystore.payment.instamojo.interfaces;

import com.quintus.labs.grocerystore.payment.instamojo.model.GatewayOrderStatus;
import com.quintus.labs.grocerystore.payment.instamojo.model.GetOrderIDRequest;
import com.quintus.labs.grocerystore.payment.instamojo.model.GetOrderIDResponse;
import com.quintus.labs.grocerystore.payment.instamojo.model.PaymentStatus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyBackendService {

    @POST("api/payments/")
    Call<GetOrderIDResponse> createOrder(@Header("Authorization") String token, @Body GetOrderIDRequest request);

    @GET("api/payments/status/")
    Call<GatewayOrderStatus> orderStatus(@Header("Authorization") String token, @Query("env") String env, @Query("order_id") String orderID,
                                         @Query("transaction_id") String transactionID);

    @POST("api/payments/refund/")
    Call<ResponseBody> refundAmount(@Header("Authorization") String token, @Query("env") String env,
                                    @Query("transaction_id") String transaction_id,
                                    @Query("amount") String amount);

    @POST("api/payments/complete/")
    Call<PaymentStatus> completePayment(@Header("Authorization") String token, @Body PaymentStatus paymentStatus);
}
