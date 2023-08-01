package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.example.ekycdemo.R;
import com.example.ekycdemo.controllers.MainController;
import com.example.ekycdemo.databinding.ActivityMainBinding;
import com.example.ekycdemo.models.UserModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainController controller;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        controller = new MainController();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        adapter = ArrayAdapter.createFromResource(this,
                R.array.Nationalities, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.nationalitySpinner.setAdapter(adapter);


        binding.nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    binding.textView.setVisibility(View.GONE);
                } else {
                    binding.textView.setVisibility(View.VISIBLE);
                }
                UserModel userModel = new UserModel(parent.getItemAtPosition(position).toString());
                controller.saveUserNationality(userModel);
                updateUserNationality();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserModel userModel = new UserModel("Select Nationality");
                controller.saveUserNationality(userModel);
                updateUserNationality();
            }
        });
        binding.nextButton.setOnClickListener(
                v -> {
                    updateUserNationality();
                    Intent intent = new Intent(MainActivity.this, IdentityVerificationActivity.class);
                    intent.putExtra("nationality", controller.getUser().getNationality());
                    startActivity(intent);
                }
        );


    }

    private void updateUserNationality() {
        UserModel userModel = controller.getUser();
        binding.textView.setText(userModel.getNationality());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nepal:
                binding.textView.setVisibility(View.VISIBLE);
                break;
            case R.id.japan:
                binding.textView.setVisibility(View.GONE);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}