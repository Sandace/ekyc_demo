package com.example.ekycdemo.controllers;

import com.example.ekycdemo.mero.ApiResponse;
import com.example.ekycdemo.models.CountryList;
import com.example.ekycdemo.models.IntendOfAccount;
import com.example.ekycdemo.models.KycDataModel;
import com.example.ekycdemo.models.PostalCodeModel;
import com.example.ekycdemo.models.RpsUser;
import com.example.ekycdemo.models.TermsAndConditionModel;
import com.example.ekycdemo.models.TokenModel;
import com.example.ekycdemo.models.SuccessModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/v1/home_api/")
    Call<ApiResponse> getHomeData(@Header("Authorization") String auth);

    @GET("api/v1/user_agreement/")
    Call<TermsAndConditionModel> getTermsAndConditions(@Query("type") String type);

    @POST("api/token/")
    Call<TokenModel> getToken(@Body RpsUser user);

    @Multipart
    @POST("api/v1/statelog")
    Call<KycDataModel> postKycData(@Header("Authorization") String auth, @Part MultipartBody.Part front_image,
                                   @Part("state") RequestBody state);
    @GET("api/v1/postal_code/")
    Call<PostalCodeModel> getPostalCode(@Query("postalcode") String postalCode);

    @GET("api/v1/home_api/kyc_information")
    Call<IntendOfAccount> getIntendOfAccountList();

    @Multipart
    @POST("api/v1/statelog")
    Call<KycDataModel> postKycData(@Header("Authorization") String auth, @Part MultipartBody.Part front_image,
                                   @Part("state") RequestBody state,
                                   @Part("card_number") RequestBody card_number
                                   );
    @Multipart
    @POST("api/v1/document")
    Call<SuccessModel> createPost(@Header("Authorization") String auth, @Part("profile_image") RequestBody title,
                                  @Part("front_image") RequestBody profile_image,
                                  @Part("tilted_image") RequestBody firstName,
                                  @Part("back_image") RequestBody lastName,
                                  @Part("card_number") RequestBody dob,
                                  @Part("kyc_type") RequestBody nationality,
                                  @Part("blink_image") RequestBody blink_image
                                  );

    @GET("/api/v1/nationality/")
    Call<CountryList> getCountriesList(@Query("type") String type);

    @Multipart
    @POST("api/v1/kyc/")
    Call<SuccessModel> postAllKycData(@Header("Authorization") String auth,
                                      @Part("title") RequestBody title,
                                      @Part("first_name") RequestBody firstName,
                                      @Part("last_name") RequestBody lastName,
                                      @Part("dob") RequestBody dob,
                                      @Part("nationality") RequestBody nationality,
                                      @Part("intended_use_of_account") RequestBody intendedUseOfAccount,
                                      @Part("mobile_number") RequestBody mobileNumber,
                                      @Part("phone_number") RequestBody phoneNumber,
                                      @Part("gender") RequestBody gender,
                                      @Part("card_issue_date") RequestBody cardIssueDate,
                                      @Part("card_expiry_date") RequestBody cardExpiryDate,
                                      @Part("period_of_stay") RequestBody periodOfStay,
                                      @Part("postal_code") RequestBody postalCode,
                                      @Part("prefecture") RequestBody prefecture,
                                      @Part("city") RequestBody city,
                                      @Part("street") RequestBody street,
                                      @Part("building_name") RequestBody buildingName

    );
    @Multipart
    @POST("api/v1/kyc/")
    Call<SuccessModel> postAllKycData(@Header("Authorization") String auth,
                                      @Part("title") RequestBody title,
                                      @Part("first_name") RequestBody firstName,
                                      @Part("last_name") RequestBody lastName,
                                      @Part("dob") RequestBody dob,
                                      @Part("nationality") RequestBody nationality,
                                      @Part("intended_use_of_account") RequestBody intendedUseOfAccount,
                                      @Part("mobile_number") RequestBody mobileNumber,
                                      @Part("phone_number") RequestBody phoneNumber,
                                      @Part("gender") RequestBody gender,
                                      @Part("card_issue_date") RequestBody cardIssueDate,
                                      @Part("card_expiry_date") RequestBody cardExpiryDate,
                                      @Part("period_of_stay") RequestBody periodOfStay,
                                      @Part("postal_code") RequestBody postalCode,
                                      @Part("prefecture") RequestBody prefecture,
                                      @Part("city") RequestBody city,
                                      @Part("street") RequestBody street,
                                      @Part("annual_income") RequestBody annualIncome,
                                      @Part("home_contact_number") RequestBody homeContactNumber,
                                      @Part("emergency_number") RequestBody emergencyNumber,
                                      @Part("building_name") RequestBody buildingName

    );
}
