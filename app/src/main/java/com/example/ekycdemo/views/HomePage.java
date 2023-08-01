package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.ekycdemo.R;
import com.example.ekycdemo.controllers.ChooseFileAdapter;
import com.example.ekycdemo.controllers.LoadSVGImageUtil;
import com.example.ekycdemo.controllers.ServicesAdapter;
import com.example.ekycdemo.databinding.ActivityHomePageBinding;
import com.example.ekycdemo.models.HomeResponseModel.HomeResponseModel;

public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(binding.getRoot());

        HomeResponseModel homeResponseModel = (HomeResponseModel) getIntent().getSerializableExtra("homeModel");

        if(homeResponseModel!=null){
            binding.customToolBar.greetingText.setText(String.format("%s, ", homeResponseModel.userDetails.greeting));
            binding.customToolBar.userName.setText(String.format("%s, ", homeResponseModel.userDetails.firstName));
            LoadSVGImageUtil.loadSvgImage(homeResponseModel.userDetails.avatar,binding.customToolBar.imageView1);
            ServicesAdapter servicesAdapter = new ServicesAdapter(this, homeResponseModel.services);
            RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.services_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(servicesAdapter);
        }
    }
}