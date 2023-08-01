package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.ekycdemo.RetrofitUtil;
import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.databinding.ActivityVerificationCompletedBinding;
import com.example.ekycdemo.mero.ApiData;
import com.example.ekycdemo.mero.ApiResponse;

import com.example.ekycdemo.models.HomeResponseModel.HomeResponseModel;
import com.example.ekycdemo.models.HomeResponseModel.QuickSend.QuickSend;
import com.example.ekycdemo.models.HomeResponseModel.Services;

import com.example.ekycdemo.models.HomeResponseModel.UserData;
import com.example.ekycdemo.models.HomeResponseModel.UserDetails;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCompleted extends AppCompatActivity {
    ActivityVerificationCompletedBinding binding;
    boolean isFromConfirmationPage = false;

    ApiService apiService = RetrofitUtil.getVersion1Adapter().create(ApiService.class);
    List<ApiData> apiData1;

    UserData userData;
    ArrayList<Services> services1;
    ArrayList<QuickSend> quickSends1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationCompletedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.proceedBtn.text.setText("Proceed");
        isFromConfirmationPage = getIntent().getBooleanExtra("fromConfirmationPage", false);

        if (isFromConfirmationPage) {
            binding.proceedBtn.text.setText("Go to HomePage");
            binding.titleText.setVisibility(View.VISIBLE);
            binding.onlineVerificationMsg.setText("You have successfully verified\nyour KYC.");
        }
        binding.proceedBtn.frontImgSaveBtn.setOnClickListener(v -> {
            if (isFromConfirmationPage) {
                String token = SharedPreferencesManager.getAccessToken(this);
                Call<ApiResponse> call2 = apiService.getHomeData("Bearer " + token);
                call2.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        Gson gson = new Gson();
                        System.out.println("ajgsdkjgjkds" + gson.toJson(response.body()));


                        List<ApiData> apiDataList = response.body().getData();

                        for (ApiData apiData : apiDataList) {
                            System.out.println("here for loop");
                            if (apiData.getType().equals("detail")) {

                                String gsonval = gson.toJson(apiData.getData());

                                  userData =  gson.fromJson(gsonval, UserData.class);



                            } else if (apiData.getType().equals("services")) {
                                String gsonval = gson.toJson(apiData.getData());
                                Services[] services = gson.fromJson(gsonval, Services[].class);

                                services1 = new ArrayList<Services>();
                                services1.addAll(Arrays.asList(services));


                                // Handle serviceDataList
                            } else if (apiData.getType().equals("quick_send")) {
                             String gsonval = gson.toJson(apiData.getData());
                                QuickSend[] quickSends = gson.fromJson(gsonval, QuickSend[].class);

                                quickSends1 = new ArrayList<QuickSend>();
                                quickSends1.addAll(Arrays.asList(quickSends));
                            }
                        }

                        ArrayList<Object> transaction = new ArrayList<>();
                        HomeResponseModel homeResponseModel = new HomeResponseModel(userData,services1,quickSends1,transaction);

                        Intent intent = new Intent(VerificationCompleted.this,HomePage.class);
                        intent.putExtra("homeModel",homeResponseModel);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        System.out.println(t.toString() + "here");
                        Toast.makeText(VerificationCompleted.this, "failure here", Toast.LENGTH_SHORT).show();

                    }
                });
                return;

            }
            Intent intent = new Intent(this, PersonalDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("apiData1", (Parcelable) apiData1);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}