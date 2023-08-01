package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ekycdemo.R;
import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.ChooseFileAdapter;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.databinding.ActivityKycConfirmationPageBinding;
import com.example.ekycdemo.models.KycData;
import com.example.ekycdemo.models.SuccessModel;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KycConfirmationPage extends AppCompatActivity {
    ActivityKycConfirmationPageBinding binding;
    KycData kycData;

    boolean additionalInfoProvided = false;
    ArrayList<String> arrayList = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rpsremit.truestreamz.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKycConfirmationPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kycData = (KycData) getIntent().getSerializableExtra("kycData");
        additionalInfoProvided = getIntent().getBooleanExtra("additionalInfoProvided", false);
        arrayList = (ArrayList<String>) getIntent().getStringArrayListExtra("fileList");

        setData();
        setOnclickListeners();
    }


    private void setData() {
        binding.submitButtonKycConfirmation.text.setText("Submit");
        if (kycData != null) {
            //personal details
            binding.titleText.setText(kycData.title);
            binding.firstName.setText(kycData.first_name);
            binding.lastName.setText(kycData.last_name);
            binding.dateOfBirth.setText(kycData.dateOfBirth);
            binding.nationality.setText(kycData.nationality);
            binding.mobileNumber.setText(kycData.mobile_number);
            binding.phoneNumber.setText(kycData.phone_number);
            binding.gender.setText(kycData.gender);
            binding.periodOfStay.setText(kycData.period_of_stay);
            binding.expireDate.setText(kycData.card_expiry_date);
            //residence address
            binding.postalCode.setText(kycData.postal_code);
            binding.prefecture.setText(kycData.prefecture);
            binding.city.setText(kycData.city);
            binding.streetAddress.setText(kycData.street);
            binding.buildingName.setText(kycData.building_name);
            //additional Information
            if (additionalInfoProvided) {

                binding.annualIncome.setText(kycData.annual_income);
                binding.homePhoneNumber.setText(kycData.home_contact_number);
                binding.emergencyContact.setText(kycData.emergency_number);

                ChooseFileAdapter chooseFileAdapter = new ChooseFileAdapter(this, arrayList);
                RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.uploaded_files);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(chooseFileAdapter);

                binding.additionalInfoCard.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setOnclickListeners() {
        binding.editIconButtonPersonalDetail.editObliqueImage.setOnClickListener(v -> {
            Intent intent = new Intent(KycConfirmationPage.this, PersonalDetailsActivity.class);
            intent.putExtra("isFromKycConfirmation", true);
            startActivity(intent);
        });
        binding.kycEditIconAdditionalInfo.editObliqueImage.setOnClickListener(v -> {
            finish();
        });
        binding.backButtonKycConfirmation.setOnClickListener(v -> {
            finish();
        });
        binding.submitButtonKycConfirmation.frontImgSaveBtn.setOnClickListener(v -> {
            binding.submitButtonKycConfirmation.text.setVisibility(View.GONE);
            binding.submitButtonKycConfirmation.progressCircularBtn.setVisibility(View.VISIBLE);

            ApiService apiService = retrofit.create(ApiService.class);
            String token = SharedPreferencesManager.getAccessToken(KycConfirmationPage.this);
            Call<SuccessModel> call;
            RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.title);
            RequestBody firstName = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.first_name);
            RequestBody lastName = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.last_name);
            RequestBody dob = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.dob);
            RequestBody nationality = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.nationality);
            RequestBody intendOfUse = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.intended_use_of_account);
            RequestBody mobileNumber = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.mobile_number);
            RequestBody phoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.phone_number);
            RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.gender);
            RequestBody cardIssueDate = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.card_issue_date);
            RequestBody cardExpiryDate = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.card_expiry_date);
            RequestBody periodOfStay = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.period_of_stay);
            RequestBody postalCode = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.postal_code);
            RequestBody prefecture = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.prefecture);
            RequestBody city = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.city);
            RequestBody street = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.street);
            RequestBody buildingName = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.building_name);
            if (additionalInfoProvided) {
                RequestBody annual_income = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.annual_income);
                RequestBody homeContactNumber = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.home_contact_number);
                RequestBody emergencyContactNumber = RequestBody.create(MediaType.parse("multipart/form-data"), kycData.emergency_number);

             call = apiService.postAllKycData(
                    "Bearer " + token,
                    title,
                    firstName,
                    lastName,
                    dob,
                    nationality,
                    intendOfUse,
                    mobileNumber,
                    phoneNumber,
                    gender,
                    cardIssueDate,
                    cardExpiryDate,
                    periodOfStay,
                    postalCode,
                    prefecture,
                    city,
                    street,
                    annual_income,
                    homeContactNumber,
                    emergencyContactNumber,
                    buildingName
            );
            }else{
                call = apiService.postAllKycData(
                        "Bearer " + token,
                        title,
                        firstName,
                        lastName,
                        dob,
                        nationality,
                        intendOfUse,
                        mobileNumber,
                        phoneNumber,
                        gender,
                        cardIssueDate,
                        cardExpiryDate,
                        periodOfStay,
                        postalCode,
                        prefecture,
                        city,
                        street,
                        buildingName
                );
            }
            System.out.println("init");
            call.enqueue(new Callback<SuccessModel>() {
                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                    System.out.println(response.code());
                    if (response.isSuccessful()) {
                        binding.submitButtonKycConfirmation.text.setVisibility(View.VISIBLE);
                        binding.submitButtonKycConfirmation.progressCircularBtn.setVisibility(View.GONE);
                        Intent intent = new Intent(KycConfirmationPage.this, VerificationCompleted.class);
                        intent.putExtra("fromConfirmationPage", true);
                        startActivity(intent);
                        System.out.println("Success posting ekyc data");
                    } else {

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(KycConfirmationPage.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(KycConfirmationPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        binding.submitButtonKycConfirmation.text.setVisibility(View.VISIBLE);
                        binding.submitButtonKycConfirmation.progressCircularBtn.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    binding.submitButtonKycConfirmation.text.setVisibility(View.VISIBLE);
                    binding.submitButtonKycConfirmation.progressCircularBtn.setVisibility(View.GONE);
                }
            });
        });
    }

}