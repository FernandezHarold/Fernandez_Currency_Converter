package com.example.fernandez_currency_converter.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("v6/86f98363b00284f9ed46d43b/latest/{currency}")
    Call<JsonObject>  getExchangeCurrency(@Path("currency")String currency);
}
