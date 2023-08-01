package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ekycdemo.R;
import com.example.ekycdemo.databinding.ActivitySelifeOrBlinkInfoBinding;

public class SelfieOrBlinkInfoActivity extends AppCompatActivity {
    ActivitySelifeOrBlinkInfoBinding binding;
    boolean takeBlinkImage= false;
    boolean blinkImageTaken = false;
    boolean fromEditSelfie = false;
    boolean fromEditBlinkImage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelifeOrBlinkInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            takeBlinkImage= bundle.getBoolean("takeBlinkImage");
            fromEditSelfie= bundle.getBoolean("fromEditSelfie");
            fromEditBlinkImage= bundle.getBoolean("fromEditBlinkImage");
        }
        binding.closeBtn.setOnClickListener(v -> finish());
        if(takeBlinkImage || fromEditBlinkImage){
            binding.ivSelfie.setImageResource(R.drawable.blink);
            binding.selfieTitleTxt.setText("Blink twice 2X");
            binding.selfieDescriptionTxt.setText("Take selfie and blink twice (2x) and Make sure your whole face is visible.");
            blinkImageTaken = true;
         }
        binding.takeSelfieBtn.text.setText("Take Selfie");
        binding.takeSelfieBtn.frontImgSaveBtn.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(SelfieOrBlinkInfoActivity.this,CameraActivity.class);
            cameraIntent.putExtra("takeSelfie",true);
            cameraIntent.putExtra("blinkImageTaken",blinkImageTaken);
            cameraIntent.putExtra("fromEditSelfie",fromEditSelfie);
            cameraIntent.putExtra("fromEditBlinkImage",fromEditBlinkImage);
            startActivity(cameraIntent);
        });
    }
}