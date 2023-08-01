package com.example.ekycdemo.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ekycdemo.R;
import com.example.ekycdemo.controllers.ChooseFileAdapter;
import com.example.ekycdemo.databinding.ActivityAdditionalInformationBinding;
import com.example.ekycdemo.models.KycData;

import java.io.File;
import java.util.ArrayList;


public class AdditionalInformation extends AppCompatActivity {
    ActivityAdditionalInformationBinding binding;
    ActivityResultLauncher<Intent> chooseFileActivityResultLauncher;
    KycData data;
    ArrayList<String> multipleFileNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdditionalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        data = (KycData) getIntent().getSerializableExtra("kycData");
        registerChooseFileActivityLauncher();
        setOnClickListeners();
    }

    private void registerChooseFileActivityLauncher() {
        chooseFileActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getClipData() != null) {
                                handleMultipleFiles(data);

                            } else {
                                handleSingleFile(data);
                            }
                        }
                    }
                });
    }


    private void setOnClickListeners() {
        binding.uploadDocument.setOnClickListener(v -> {
            loadFile();
        });
        binding.skip.setOnClickListener(v -> {
            Intent intent = new Intent(AdditionalInformation.this, KycConfirmationPage.class);
            intent.putExtra("kycData", data);
            intent.putExtra("fromSkipButton", true);
            startActivity(intent);
        });
        binding.backButtonTypesOfIdentities.setOnClickListener(v -> {
            finish();
        });
        binding.previousButton.setOnClickListener(v -> {
            finish();
        });
        binding.proceedButton.setOnClickListener(v -> {
            if (checkInputFields()) {
                data.annual_income = binding.annualIncome.getText().toString();
                data.home_contact_number = binding.contactNumber.getText().toString();
                data.emergency_number = binding.emergencyContactNumber.getText().toString();
                Intent intent = new Intent(AdditionalInformation.this, KycConfirmationPage.class);
                intent.putExtra("kycData", data);
                intent.putExtra("fromSkipButton", false);
                intent.putExtra("additionalInfoProvided", true);
                intent.putStringArrayListExtra("fileList", multipleFileNameList);
                intent.putExtra("imageUri", multipleFileNameList);
                startActivity(intent);
            } else {
                Toast.makeText(AdditionalInformation.this, "Fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInputFields() {
        if (binding.annualIncome.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (binding.emergencyContactNumber.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (binding.contactNumber.getText().toString().trim().isEmpty()) {
            return false;
        }
        return !multipleFileNameList.isEmpty();
    }

    private void loadFile() {
        final String type = "*/*";
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType(type);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        chooseFileActivityResultLauncher.launch(i);
    }

    private void handleMultipleFiles(Intent data) {
        String[] filePaths = new String[data.getClipData().getItemCount()];
        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
            Uri uri = data.getClipData().getItemAt(i).getUri();
            filePaths[i] = String.valueOf(uri.getPath());
        }
        //files
        ArrayList<File> fileList = new ArrayList<File>();


        //trimmed list of file with only names
        for (int j = 0; j < filePaths.length; j++) {
            String[] pathArray = filePaths[j].split("/");
            String fileName = pathArray[pathArray.length - 1];
            //files
            fileList.add(new File(filePaths[j]));
            multipleFileNameList.add(fileName);
        }
        ChooseFileAdapter chooseFileAdapter = new ChooseFileAdapter(this, multipleFileNameList);
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.upload_item_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseFileAdapter);
    }

    private void handleSingleFile(Intent data) {
        Uri uploadFileUrl = data.getData();
        File file = new File(uploadFileUrl.getPath());
        String path = file.getPath();
        String[] pathArray = path.split("/");
        String fileName = pathArray[pathArray.length - 1];
        multipleFileNameList.add(fileName);
        ChooseFileAdapter chooseFileAdapter = new ChooseFileAdapter(this, multipleFileNameList);
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.upload_item_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseFileAdapter);
    }
}