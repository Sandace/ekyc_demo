package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.RealPathUtil;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.databinding.ActivityFrontCaptureConfirmationBinding;
import com.example.ekycdemo.models.KycDataModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FrontCaptureConfirmation extends AppCompatActivity {
    ActivityFrontCaptureConfirmationBinding binding;
    Uri imageUrl;
    boolean isFrontImageTaken = false;
    boolean isObliqueImageTaken = false;
    boolean takeSelfie = false;
    boolean takeBackImage = false;
    boolean selfieTaken = false;
    boolean takeTiltedImage = false;
    boolean blinkImageTaken = false;
    boolean fromEditFrontImage = false;
    boolean fromEditObliqueImage = false;
    boolean fromEditBackImage = false;
    boolean fromEditSelfie = false;
    boolean fromEditBlinkImage = false;
    String cardNumber = "";
    String accessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFrontCaptureConfirmationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);

        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra("imageUri");
            imageUrl = Uri.parse(data);
            binding.frontCapturedImg.setImageURI(null);
            binding.frontCapturedImg.setImageURI(imageUrl);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFrontImageTaken = bundle.getBoolean("isFromFrontCapture");
            isObliqueImageTaken = bundle.getBoolean("isFromOblique");
            takeSelfie = bundle.getBoolean("takeSelfie");
            takeBackImage = bundle.getBoolean("takeBackImage");
            selfieTaken = bundle.getBoolean("selfieTaken");
            takeTiltedImage = bundle.getBoolean("takeTiltedImage");
            blinkImageTaken = bundle.getBoolean("blinkImageTaken");
            cardNumber = bundle.getString("cardNumber");
            accessToken = bundle.getString("accessToken");
            fromEditFrontImage = bundle.getBoolean("fromEditFrontImage");
            fromEditObliqueImage = bundle.getBoolean("fromEditObliqueImage");
            fromEditBackImage = bundle.getBoolean("fromEditBackImage");
            fromEditSelfie = bundle.getBoolean("fromEditSelfie");
            fromEditBlinkImage = bundle.getBoolean("fromEditBlinkImage");
        }
        binding.retakePicture.setOnClickListener(v -> finish());
        binding.frontImgSaveBtn.frontImgSaveBtn.setOnClickListener(v -> {
            binding.frontImgSaveBtn.text.setVisibility(View.GONE);
            binding.frontImgSaveBtn.progressCircularBtn.setVisibility(View.VISIBLE);
            binding.retakePicture.setEnabled(false);
            try {
                postKycData();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }


    private void postKycData() {

        String token = SharedPreferencesManager.getAccessToken(FrontCaptureConfirmation.this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rpsremit.truestreamz.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Uri path = Uri.parse(RealPathUtil.getRealPath(FrontCaptureConfirmation.this, imageUrl));

        File file = new File(String.valueOf(path));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData((blinkImageTaken || fromEditBlinkImage) ? "blink_image" : selfieTaken ? "profile_image" : takeTiltedImage ? "tilted_image" : (isObliqueImageTaken || fromEditBackImage) ? "back_image" : "front_image", file.getName(), requestFile);
        RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"), blinkImageTaken ? "BLINK" : selfieTaken ? "profile_image" : takeTiltedImage ? "TILTED" : isObliqueImageTaken ? "BACK" : "FRONT");


        ApiService apiService = retrofit.create(ApiService.class);
        Call<KycDataModel> call;
        if (cardNumber != null && !cardNumber.trim().isEmpty()) {
            RequestBody card_number = RequestBody.create(MediaType.parse("multipart/form-data"), cardNumber);
            call = apiService.postKycData("Bearer " + token, body, state, card_number);
        } else {
            call = apiService.postKycData("Bearer " + token, body, state);
        }
        call.enqueue(new Callback<KycDataModel>() {
            @Override
            public void onResponse(Call<KycDataModel> call, Response<KycDataModel> response) {
                binding.frontImgSaveBtn.text.setVisibility(View.VISIBLE);
                binding.frontImgSaveBtn.progressCircularBtn.setVisibility(View.GONE);
                binding.retakePicture.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(FrontCaptureConfirmation.this, "Image saved successfully.", Toast.LENGTH_SHORT).show();
                    navigateToNextPage(response.body());
                }
            }

            @Override
            public void onFailure(Call<KycDataModel> call, Throwable t) {
                binding.frontImgSaveBtn.text.setVisibility(View.VISIBLE);
                binding.frontImgSaveBtn.progressCircularBtn.setVisibility(View.GONE);
                binding.retakePicture.setEnabled(true);
                Toast.makeText(FrontCaptureConfirmation.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToNextPage(KycDataModel model) {
        if (blinkImageTaken || fromEditFrontImage || fromEditObliqueImage || fromEditBackImage || fromEditSelfie || fromEditBlinkImage) {
            Intent confirmationPageIntent = new Intent(FrontCaptureConfirmation.this, ConfirmationIdentityVerification.class);
            confirmationPageIntent.putExtra("kycModel", model);
            startActivity(confirmationPageIntent);
            return;
        }
        if (takeSelfie) {
            Intent cameraIntent = new Intent(FrontCaptureConfirmation.this, SelfieOrBlinkInfoActivity.class);
            cameraIntent.putExtra("takeSelfie", true);
            startActivity(cameraIntent);
            return;
        }
        if (selfieTaken) {
            Intent blinkIntent = new Intent(FrontCaptureConfirmation.this, SelfieOrBlinkInfoActivity.class);
            blinkIntent.putExtra("takeBlinkImage", true);
            startActivity(blinkIntent);
            return;
        }
        Intent cameraIntent = new Intent(FrontCaptureConfirmation.this, PhotoTaking.class);
        cameraIntent.putExtra("isFromFrontCapture", true);
        cameraIntent.putExtra("isFromOblique", isObliqueImageTaken);
        cameraIntent.putExtra("takeBackImage", takeBackImage);
        startActivity(cameraIntent);
    }

}