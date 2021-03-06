package com.mohammadkz.bimix.API;


import androidx.cardview.widget.CardView;

import com.mohammadkz.bimix.Model.CheckResult;
import com.mohammadkz.bimix.Model.HistoryInsuranceResponse;
import com.mohammadkz.bimix.Model.InsuranceResponse;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.RequestResponse;
import com.mohammadkz.bimix.Model.UpdateResponse;
import com.mohammadkz.bimix.Model.User;

import java.util.List;

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
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("TrackingCheck.php")
    Call<RequestResponse> req_checkTrackingCode(@Field("code") String code);

    @FormUrlEncoded
    @POST("FireInsurance.php")
    Call<RequestResponse> req_fireInsurance(@Field("userAuth") String userAuth,
                                            @Field("typeOfStructure") String typeOfStructure,
                                            @Field("buildingType") String buildingType,
                                            @Field("price") String price,
                                            @Field("area") String area,
                                            @Field("lifeTime") String lifeTime,
                                            @Field("postCode") String postCode,
                                            @Field("address") String address,
                                            @Field("date") String date,
                                            @Field("trackingCode") String trackingCode
    );

    @FormUrlEncoded
    @POST("getUserInsurance.php")
    Call<List<InsuranceResponse>> req_getUserInsurance(@Field("auth") String auth

    );

    @FormUrlEncoded
    @POST("updatePayStatus.php")
    Call<UpdateResponse> payStatus(@Field("userAuth") String userAuth,
                                   @Field("status") String status,
                                   @Field("trackingCode") String trackingCode,
                                   @Field("kind") String kind

    );

    @FormUrlEncoded
    @POST("third_party.php")
    Call<RequestResponse> req_thirdInsurance(@Field("ID") String ID,
                                             @Field("endDate") String endDate,
                                             @Field("lastCompany") String lastCompany,
                                             @Field("useFor") String useFor,
                                             @Field("pic1") String pic1,
                                             @Field("pic2") String pic2,
                                             @Field("pic3") String pic3,
                                             @Field("trackingCode") String trackingCode,
                                             @Field("userAuth") String userAuth,
                                             @Field("code") String insuranceId,
                                             @Field("backCertificate") String backCertificate,
                                             @Field("onCertificate") String onCertificate,
                                             @Field("date") String date
    );

    @FormUrlEncoded
    @POST("checkNotification.php")
    Call<List<CheckResult>> checkResult(
            @Field("auth") String auth
    );

    @FormUrlEncoded
    @POST("getUserProfile.php")
    Call<User> getUser(@Field("auth") String auth);

    @FormUrlEncoded
    @POST("updateProfile.php")
    Call<LoginResponse> updateUser(@Field("auth") String auth,
                                   @Field("idCard_image") String idCard_image,
                                   @Field("idCard") String idCard,
                                   @Field("birthdayDate") String birthdayDate

    );


    @FormUrlEncoded
    @POST("BodyInsurance.php")
    Call<RequestResponse> req_bodyInsurance(@Field("ID") String ID,
                                            @Field("userAuth") String userAuth,
                                            @Field("trackingCode") String trackingCode,
                                            @Field("status") String status,
                                            @Field("code") String code,
                                            @Field("useFor") String useFor,
                                            @Field("carName") String carName,
                                            @Field("lastCompany") String lastCompany,
                                            @Field("createDate") String createDate,
                                            @Field("pic1") String pic1,
                                            @Field("pic2") String pic2,
                                            @Field("date") String date,
                                            @Field("cover") String cover,
                                            @Field("discount") String discount,
                                            @Field("visit") boolean visit,
                                            @Field("visit_time") String visit_time,
                                            @Field("visit_date") String visit_date,
                                            @Field("visit_address") String visit_address,
                                            @Field("another_person") boolean another_person,
                                            @Field("another_person_name") String another_person_name,
                                            @Field("another_person_idCard") String another_person_idCard,
                                            @Field("another_person_phone") String another_person_phone,
                                            @Field("another_person_birthdayDate") String another_person_birthdayDate,
                                            @Field("installments") boolean installments
    );
}
