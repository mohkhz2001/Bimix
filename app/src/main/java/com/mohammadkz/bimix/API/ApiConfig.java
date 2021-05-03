package com.mohammadkz.bimix.API;


import com.mohammadkz.bimix.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConfig {

    @FormUrlEncoded
    @POST("Login.php")
    Call<LoginResponse> Login(@Field("phoneNumber") String username,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("Signup.php")
    Call<LoginResponse> SignUp(@Field("phoneNumber") String phoneNumber,
                               @Field("name") String name,
                               @Field("password") String password,
                               @Field("auth") String auth);
}
