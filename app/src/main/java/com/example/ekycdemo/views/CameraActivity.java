package com.example.ekycdemo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.ekycdemo.databinding.ActivityCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;


import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {
  ActivityCameraBinding binding;
  private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    boolean isFrontImageTaken = false;
    boolean isObliqueImageTaken = false;
    boolean takeBackImage = false;
    boolean takeSelfie = false;
    boolean takeTiltedImage = false;
    boolean blinkImageTaken = false;
    boolean fromEditFrontImage = false;
    boolean fromEditObliqueImage = false;
    boolean fromEditBackImage = false;
    boolean fromEditSelfie = false;
    boolean fromEditBlinkImage = false;
    String cardNumber= "";
    String accessToken= "";
    ImageCapture imageCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);

      Bundle bundle = getIntent().getExtras();
      if(bundle!=null){
          isFrontImageTaken = bundle.getBoolean("isFromFrontCapture");
          isObliqueImageTaken = bundle.getBoolean("isFromOblique");
          takeSelfie = bundle.getBoolean("takeSelfie");
          blinkImageTaken = bundle.getBoolean("blinkImageTaken");
          cardNumber = bundle.getString("cardNumber");
          accessToken = bundle.getString("accessToken");
          fromEditFrontImage = bundle.getBoolean("fromEditFrontImage");
          fromEditObliqueImage = bundle.getBoolean("fromEditObliqueImage");
          fromEditBackImage = bundle.getBoolean("fromEditBackImage");
          fromEditSelfie = bundle.getBoolean("fromEditSelfie");
          fromEditBlinkImage = bundle.getBoolean("fromEditBlinkImage");
          if(takeSelfie || fromEditBlinkImage){
            binding.infoCard.setVisibility(View.GONE);
            binding.selfieCard.setVisibility(View.VISIBLE);
            binding.customCameraButtonForSelfie.setVisibility(View.VISIBLE);
            binding.customCameraButton.setVisibility(View.GONE);
          }
          else if((isFrontImageTaken && !isObliqueImageTaken) || fromEditObliqueImage){
            binding.captureImageMsg.setText("Capture Oblique- \n45 degree of the Card");
            isObliqueImageTaken = true;
              takeTiltedImage =true;
          }
          else if(isObliqueImageTaken || fromEditBackImage){
              binding.captureImageMsg.setText("Capture Back side\nof the card");
              takeBackImage = true;
          }

      }


        binding.previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() ->{
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider,takeSelfie);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        },getExecutor());

        binding.customCameraButton.setOnClickListener(v -> capturePhoto());
        binding.customCameraButtonForSelfie.setOnClickListener(v -> capturePhoto());
        binding.closeButton.setOnClickListener(v -> finish());
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider,boolean isSelfie) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(isSelfie?CameraSelector.LENS_FACING_FRONT: CameraSelector.LENS_FACING_BACK).build();
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();
        cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture);
    }
    private void capturePhoto(){
        Date date = new Date( );
        String timestamp =  String.valueOf(date.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Intent intent = new Intent(CameraActivity.this,FrontCaptureConfirmation.class);
                        intent.putExtra("imageUri", outputFileResults.getSavedUri().toString());
                        intent.putExtra("isFromFrontCapture", isFrontImageTaken);
                        intent.putExtra("isFromOblique", isObliqueImageTaken);
                        intent.putExtra("takeBackImage", takeBackImage);
                        intent.putExtra("takeSelfie", takeBackImage);
                        intent.putExtra("selfieTaken", takeSelfie);
                        intent.putExtra("takeTiltedImage", takeTiltedImage);
                        intent.putExtra("blinkImageTaken", blinkImageTaken);
                        intent.putExtra("cardNumber", cardNumber);
                        intent.putExtra("accessToken", accessToken);
                        intent.putExtra("fromEditFrontImage", fromEditFrontImage);
                        intent.putExtra("fromEditObliqueImage", fromEditObliqueImage);
                        intent.putExtra("fromEditBackImage", fromEditBackImage);
                        intent.putExtra("fromEditSelfie", fromEditSelfie);
                        intent.putExtra("fromEditBlinkImage", fromEditBlinkImage);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}