package com.example.ekycdemo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ekycdemo.R;
import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.controllers.Validator.TextValidator;
import com.example.ekycdemo.databinding.ActivityIdentityVerificationBinding;

import com.example.ekycdemo.models.TermsAndConditionModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentityVerificationActivity extends AppCompatActivity {
    ActivityIdentityVerificationBinding binding;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rpsremit.truestreamz.com/").addConverterFactory(GsonConverterFactory.create()).build();

    Gson gson;
    ApiService apiService = retrofit.create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        binding = ActivityIdentityVerificationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(view);
        Drawable drawable = getResources().getDrawable(R.drawable.border);
        Intent intent = getIntent();
        binding.editTextResidenceCard.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(12)});
        SharedPreferencesManager.saveCardName(IdentityVerificationActivity.this, "Resident Card");
        if (intent != null) {
            String receivedData = intent.getStringExtra("nationality");
            if (receivedData.trim().toLowerCase().contains("japan")) {
                binding.residenceCardLayoutRow.checkableResidenceCard.setChecked(true);
                binding.residenceCardLayoutRow.checkableResidenceCard.setCardBackgroundColor(getResources().getColor(R.color.secondary_blue));
                binding.drivingLicenseLayout.cardView.setBackground(null);
                binding.residenceCardLayoutRow.cardName.setText("My Number Card");
                binding.cardNumberName.setText("My Number Card");
                SharedPreferencesManager.saveCardName(IdentityVerificationActivity.this, "My Number Card");

            } else {
                binding.residenceCardLayoutRow.checkableResidenceCard.setCardBackgroundColor(getResources().getColor(R.color.secondary_blue));
                binding.drivingLicenseLayout.cardView.setVisibility(View.GONE);
                binding.residenceCardLayoutRow.checkableResidenceCard.setChecked(true);
                binding.residenceCardLayoutRow.checkableResidenceCard.setCheckable(false);
            }

        }
        String text = binding.agreeTermsLayout.personalInfoTextView.getText().toString();
        String text2 = binding.agreeTermsLayout.kycInfoTextview.getText().toString();
        SpannableString ss = new SpannableString(text);
        SpannableString ss2 = new SpannableString(text2);
        ForegroundColorSpan fcs = new ForegroundColorSpan(0xff3462B5);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Call<TermsAndConditionModel> call = apiService.getTermsAndConditions("personal_information");

                call.enqueue(new Callback<TermsAndConditionModel>() {
                    @Override
                    public void onResponse(Call<TermsAndConditionModel> call, Response<TermsAndConditionModel> response) {

                        if (response.isSuccessful()) {
                            TermsAndConditionModel termsAndConditionModel = response.body();
                            assert termsAndConditionModel != null;
                            Intent termsIntent = new Intent(IdentityVerificationActivity.this, TermsAndConditions.class);
                            termsIntent.putExtra("data", termsAndConditionModel);
                            startActivity(termsIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<TermsAndConditionModel> call, Throwable t) {
                        // Handle network errors or request failure
                    }
                });
            }

        };
        ss.setSpan(clickableSpan1, 20, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcs, 20, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.agreeTermsLayout.personalInfoTextView.setText(ss);
        binding.agreeTermsLayout.personalInfoTextView.setMovementMethod(LinkMovementMethod.getInstance());

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Call<TermsAndConditionModel> call = apiService.getTermsAndConditions("kyc_agreement");

                call.enqueue(new Callback<TermsAndConditionModel>() {
                    @Override
                    public void onResponse(Call<TermsAndConditionModel> call, Response<TermsAndConditionModel> response) {

                        if (response.isSuccessful()) {
                            TermsAndConditionModel termsAndConditionModel = response.body();
                            assert termsAndConditionModel != null;
                            Intent termsIntent = new Intent(IdentityVerificationActivity.this, TermsAndConditions.class);
                            termsIntent.putExtra("data", termsAndConditionModel);
                            startActivity(termsIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<TermsAndConditionModel> call, Throwable t) {
                        // Handle network errors or request failure
                    }
                });
            }
        };
        ss2.setSpan(clickableSpan2, 20, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(fcs, 20, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.agreeTermsLayout.kycInfoTextview.setText(ss2);
        binding.agreeTermsLayout.kycInfoTextview.setMovementMethod(LinkMovementMethod.getInstance());
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(IdentityVerificationActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
        binding.residenceCardLayoutRow.checkableResidenceCard.setOnClickListener(v -> {
            binding.residenceCardLayoutRow.checkableResidenceCard.setCardBackgroundColor(getResources().getColor(R.color.secondary_blue));
            binding.drivingLicenseLayout.checkableDrivingLicense.setCardBackgroundColor(getResources().getColor(R.color.white));
            binding.residenceCardLayoutRow.cardView.setBackground(drawable);
            binding.drivingLicenseLayout.cardView.setBackground(null);
            if (binding.residenceCardLayoutRow.checkableResidenceCard.isChecked()) {
                return;
            } else {
                binding.residenceCardLayoutRow.checkableResidenceCard.setChecked(true);
                binding.drivingLicenseLayout.checkableDrivingLicense.setChecked(false);
                binding.cardNumberName.setText("My Number Card");
                SharedPreferencesManager.saveCardName(IdentityVerificationActivity.this, "My Number Card");
            }
        });
        binding.drivingLicenseLayout.checkableDrivingLicense.setOnClickListener(v -> {
            binding.drivingLicenseLayout.checkableDrivingLicense.setCardBackgroundColor(getResources().getColor(R.color.secondary_blue));
            binding.drivingLicenseLayout.cardView.setBackground(drawable);
            binding.residenceCardLayoutRow.cardView.setBackground(null);
            binding.residenceCardLayoutRow.checkableResidenceCard.setCardBackgroundColor(getResources().getColor(R.color.white));
            if (binding.drivingLicenseLayout.checkableDrivingLicense.isChecked()) {
                return;
            } else {
                binding.drivingLicenseLayout.checkableDrivingLicense.setChecked(true);
                binding.residenceCardLayoutRow.checkableResidenceCard.setChecked(false);
                binding.cardNumberName.setText("Driving License");
                SharedPreferencesManager.saveCardName(IdentityVerificationActivity.this, "Driving license");
            }

        });
        binding.agreeTermsLayout.personalInfoCheckbox.setOnClickListener(v -> {
            if (binding.agreeTermsLayout.checkPersonalInfo.getVisibility() == View.VISIBLE) {
                binding.agreeTermsLayout.checkPersonalInfo.setVisibility(View.INVISIBLE);
                return;
            }
            binding.agreeTermsLayout.checkPersonalInfo.setVisibility(View.VISIBLE);

        });
        binding.agreeTermsLayout.kycInfoCheckbox.setOnClickListener(v -> {
            if (binding.agreeTermsLayout.checkKycInfo.getVisibility() == View.VISIBLE) {
                binding.agreeTermsLayout.checkKycInfo.setVisibility(View.INVISIBLE);
                return;
            }
            binding.agreeTermsLayout.checkKycInfo.setVisibility(View.VISIBLE);
        });


        binding.editTextResidenceCard.addTextChangedListener(new TextValidator(binding.editTextResidenceCard) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
//                    binding.residenceCardLayout.setError("*Required");
//                    textView.requestFocus();
                } else {
                    textView.setError(null);
                    binding.editTextResidenceCard.setError(null);
                }
            }
        });

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextResidenceCard.getText().toString().trim().isEmpty()) {
                    binding.editTextResidenceCard.setError("Enter your card number");
                } else if (binding.agreeTermsLayout.checkPersonalInfo.getVisibility() != View.VISIBLE || binding.agreeTermsLayout.kycInfoCheckbox.getVisibility() != View.VISIBLE) {
                    Toast.makeText(getApplicationContext(), "Please agree terms and condtions to proceed", Toast.LENGTH_SHORT).show();
                } else {
                    Intent typesOfIdentitiesIntent = new Intent(IdentityVerificationActivity.this, TypesOfIdentities.class);
                    typesOfIdentitiesIntent.putExtra("cardNumber", binding.editTextResidenceCard.getText().toString());
                    startActivity(typesOfIdentitiesIntent);
                }
            }
        });
    }


    public static String htmlToString(String html) {
        return Html.fromHtml(html.replaceAll("<.*?>", "").replaceAll("\n", "").replaceAll("\t", " ")).toString();
    }
}