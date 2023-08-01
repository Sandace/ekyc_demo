package com.example.ekycdemo.views;


import androidx.core.text.HtmlCompat;

import android.app.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;

import com.example.ekycdemo.databinding.ActivityTermsAndConditionsBinding;
import com.example.ekycdemo.models.TermsAndConditionModel;

public class TermsAndConditions extends Activity {
    ActivityTermsAndConditionsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        TermsAndConditionModel termsAndConditionModel = (TermsAndConditionModel) getIntent().getSerializableExtra("data");
        binding.termsTitle.setText(
                termsAndConditionModel.data.name_en
        );
        binding.termsBody.setText(Html.fromHtml(Html.fromHtml(termsAndConditionModel.data.description_en).toString()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.termsBody.setText(HtmlCompat.fromHtml(termsAndConditionModel.data.description_en, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            binding.termsBody.setText(Html.fromHtml(termsAndConditionModel.data.description_en));
        }
        binding.backButton2.setOnClickListener(v -> finish());
        }

    }
