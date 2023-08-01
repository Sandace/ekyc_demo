package com.example.ekycdemo.views;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;



import com.example.ekycdemo.databinding.ActivityPhotoTakingBinding;

import java.io.File;

public class PhotoTaking extends AppCompatActivity {
    ActivityPhotoTakingBinding binding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;

    private static  final int CAMERA_PERMISSION_CODE = 1;
    private static  final int STORAGE_PERMISSION_CODE = 2;
    boolean isFrontImageTaken = false;
    boolean isObliqueImageTaken = false;
    boolean takeBackImage = false;
    String cardNumber= "";
    String accessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPhotoTakingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            isFrontImageTaken = bundle.getBoolean("isFromFrontCapture");
            isObliqueImageTaken = bundle.getBoolean("isFromOblique");
            takeBackImage = bundle.getBoolean("takeBackImage");
            cardNumber = bundle.getString("cardNumber");
            accessToken = bundle.getString("accessToken");
            if(isFrontImageTaken && !isObliqueImageTaken){
                binding.takeAPhotoMsg.setText("Take a Photo - \nOblique (45 degree)");
                rotateImageView(binding.sampleImage,45f);
            }

            else if (isObliqueImageTaken) {
                binding.takeAPhotoMsg.setText("Take a Photo - back side");
            }
        }
        imageUri = createUri();
        registerPictureLauncher();
        binding.launchCamera.setOnClickListener(v -> checkCameraPermissionAndOpenCamera());


    }
    private Uri createUri(){
        File imageFile = new File(getApplicationContext().getFilesDir(),"camera_photo.jpg");
        return FileProvider.getUriForFile(getApplicationContext(),"com.example.ekycdemo.fileProvider",imageFile);
    }
    private void registerPictureLauncher(){
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(), result -> {
                    if(result){
                         binding.mobileFrame.setImageURI(null);
                         binding.mobileFrame.setImageURI(imageUri);

                    }
                }
        );
    }
    private  void checkCameraPermissionAndOpenCamera(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);

        }else{
            Intent intent = new Intent(PhotoTaking.this,CameraActivity.class);
            intent.putExtra("isFromFrontCapture", isFrontImageTaken);
            intent.putExtra("isFromOblique", isObliqueImageTaken);
            intent.putExtra("takeBackImage", takeBackImage);
            intent.putExtra("cardNumber", cardNumber);
            intent.putExtra("accessToken", accessToken);
            startActivity(intent);
//            takePictureLauncher.launch(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PERMISSION_CODE){
            if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){

                Intent intent = new Intent(PhotoTaking.this,CameraActivity.class);
                intent.putExtra("isFromFrontCapture", isFrontImageTaken);
                intent.putExtra("isFromOblique", isObliqueImageTaken);
                intent.putExtra("cardNumber", cardNumber);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
//                takePictureLauncher.launch(imageUri);
            }else{
                Toast.makeText(this,"Camera permission denied, please allow permission to take picture", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void rotateImageView(View imageView, float rotationAngle) {
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotationX", 0f, rotationAngle);
        rotateAnimator.setDuration(1000); // Set the animation duration in milliseconds
        rotateAnimator.start();
    }
}