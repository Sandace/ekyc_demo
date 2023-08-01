package com.example.ekycdemo.views;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.databinding.ActivityTypesOfIdentitiesBinding;
import com.example.ekycdemo.models.RpsUser;
import com.example.ekycdemo.models.TokenModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypesOfIdentities extends AppCompatActivity {
    ActivityTypesOfIdentitiesBinding binding;
    String cardNumber = "";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://rpsremit.truestreamz.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiService apiService = retrofit.create(ApiService.class);
    String accessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTypesOfIdentitiesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(view);
        Intent intent = getIntent();
        if (intent != null) {
            cardNumber = intent.getStringExtra("cardNumber");
        }

    }

    @Override
    protected void onResume() {
        RpsUser rpsUser = new RpsUser("stest21@mailinator.com", "Rps1234%");
        binding.backButtonTypesOfIdentities.setOnClickListener(v -> finish());
        binding.startEkyc.setOnClickListener(v -> {
            binding.startEkycTextview.setVisibility(View.GONE);
            binding.circularProgress.setVisibility(View.VISIBLE);

            Call<TokenModel> call = apiService.getToken(rpsUser);
            call.enqueue(new Callback<TokenModel>() {
                @Override
                public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                    if (response.isSuccessful()) {
                        binding.startEkycTextview.setVisibility(View.VISIBLE);
                        binding.circularProgress.setVisibility(View.GONE);
                        TokenModel tokenModel = response.body();
                        accessToken = tokenModel.access;
                        SharedPreferencesManager.saveAccessToken(TypesOfIdentities.this, accessToken);
                        Intent photoTakingIntent = new Intent(TypesOfIdentities.this, PhotoTaking.class);
                        photoTakingIntent.putExtra("cardNumber", cardNumber);
                        photoTakingIntent.putExtra("accessToken", accessToken);
                        startActivity(photoTakingIntent);
                    } else {
                        binding.startEkycTextview.setVisibility(View.VISIBLE);
                        binding.circularProgress.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<TokenModel> call, Throwable t) {
                    binding.startEkycTextview.setVisibility(View.VISIBLE);
                    binding.circularProgress.setVisibility(View.GONE);
                }
            });
        });
        super.onResume();
    }
}