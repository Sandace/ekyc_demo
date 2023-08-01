package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.databinding.ActivityConfirmationIdentityVerificationBinding;
import com.example.ekycdemo.models.DataModal;
import com.example.ekycdemo.models.KycDataModel;
import com.example.ekycdemo.models.SuccessModel;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ConfirmationIdentityVerification extends AppCompatActivity {
    KycDataModel kycDataModel;
    ActivityConfirmationIdentityVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmationIdentityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String token = SharedPreferencesManager.getAccessToken(ConfirmationIdentityVerification.this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rpsremit.truestreamz.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);


        String cardName = SharedPreferencesManager.getCardName(ConfirmationIdentityVerification.this);
        Intent intent = getIntent();
        if (intent != null) {
            kycDataModel = (KycDataModel) intent.getSerializableExtra("kycModel");
            if (kycDataModel.data.card_number != null) {
                binding.cardNumberRc.setText(kycDataModel.data.card_number);
            }
            if (kycDataModel.data.front_image != null && !kycDataModel.data.front_image.isEmpty()) {
                Glide.with(this).load(kycDataModel.data.front_image).into(binding.frontCapturedImage);
            }
            if (kycDataModel.data.back_image != null && !kycDataModel.data.back_image.isEmpty()) {
                Glide.with(this).load(kycDataModel.data.back_image).into(binding.backCapturedImg);
             }
            if (kycDataModel.data.tilted_image != null && !kycDataModel.data.tilted_image.isEmpty()) {
                Glide.with(this).load(kycDataModel.data.tilted_image).into(binding.frontCapturedImgOblique);
            }
            if (kycDataModel.data.profile_image != null && !kycDataModel.data.profile_image.isEmpty()) {
                Glide.with(this).load(kycDataModel.data.profile_image).into(binding.selfieCapturedImg);
            }if (kycDataModel.data.blink_image != null && !kycDataModel.data.blink_image.isEmpty()) {
                Glide.with(this).load(kycDataModel.data.blink_image).into(binding.blinkCapturedImg);
            }
        }
        binding.submitButton.text.setText("Submit");
        binding.cardName.setText(cardName);
        binding.editFrontImage.setOnClickListener(v->{
            Intent cameraActivityIntent = new Intent(ConfirmationIdentityVerification.this, CameraActivity.class);
            cameraActivityIntent.putExtra("fromEditFrontImage",true);
            startActivity(cameraActivityIntent);
        });
        binding.editObliqueImage.setOnClickListener(v->{
            Intent cameraActivityIntent = new Intent(ConfirmationIdentityVerification.this, CameraActivity.class);
            cameraActivityIntent.putExtra("fromEditObliqueImage",true);
            startActivity(cameraActivityIntent);
        });
        binding.editBackImage.setOnClickListener(v->{
            Intent cameraActivityIntent = new Intent(ConfirmationIdentityVerification.this, CameraActivity.class);
            cameraActivityIntent.putExtra("fromEditBackImage",true);
            startActivity(cameraActivityIntent);
        });
        binding.editSelfieImage.setOnClickListener(v->{
            Intent cameraActivityIntent = new Intent(ConfirmationIdentityVerification.this, SelfieOrBlinkInfoActivity.class);
            cameraActivityIntent.putExtra("fromEditSelfie",true);
            startActivity(cameraActivityIntent);
        });
        binding.editBlinkImage.setOnClickListener(v->{
            Intent cameraActivityIntent = new Intent(ConfirmationIdentityVerification.this, SelfieOrBlinkInfoActivity.class);
            cameraActivityIntent.putExtra("fromEditBlinkImage",true);
            startActivity(cameraActivityIntent);
        });
        binding.submitButton.frontImgSaveBtn.setOnClickListener(v->{
            binding.submitButton.text.setVisibility(View.GONE);
            binding.submitButton.progressCircularBtn.setVisibility(View.VISIBLE);
            DataModal dataModal = new DataModal(kycDataModel.data.profile_image,
                    kycDataModel.data.tilted_image,
                    kycDataModel.data.front_image,
                    kycDataModel.data.back_image,
                    kycDataModel.data.card_number,
                    kycDataModel.data.tilted_image,
                kycDataModel.data.blink_image);
            RequestBody profile_image = RequestBody.create(MediaType.parse("multipart/form-data"),dataModal.profile_image);

            RequestBody tilted_image = RequestBody.create(MediaType.parse("multipart/form-data"),dataModal.tilted_image);
            RequestBody front_image = RequestBody.create(MediaType.parse("multipart/form-data"),dataModal.front_image);
            RequestBody back_image = RequestBody.create(MediaType.parse("multipart/form-data"),dataModal.back_image);
            RequestBody card_number = RequestBody.create(MediaType.parse("multipart/form-data"),dataModal.card_number);
            RequestBody kyc_type = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
            RequestBody blink_image = RequestBody.create(MediaType.parse("multipart/form-data"),dataModal.blink_image);

            Call<SuccessModel> call = apiService.createPost("Bearer "+token,
                    profile_image,
                    tilted_image,
                    front_image,
                    back_image,
                    card_number,
                    kyc_type,
                    blink_image
                    );
            Intent verificationCompletion = new Intent(this, VerificationCompleted.class);
            call.enqueue(new Callback<SuccessModel>() {

                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                    System.out.println(response.code());
                    if(response.isSuccessful()) {
                        binding.submitButton.text.setVisibility(View.VISIBLE);
                        binding.submitButton.progressCircularBtn.setVisibility(View.GONE);
                        startActivity(verificationCompletion);
                    }
                    else{
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ConfirmationIdentityVerification.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(ConfirmationIdentityVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        binding.submitButton.text.setVisibility(View.VISIBLE);
                        binding.submitButton.progressCircularBtn.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    binding.submitButton.text.setVisibility(View.VISIBLE);
                    binding.submitButton.progressCircularBtn.setVisibility(View.GONE);
                }
            });

        });
    }
}