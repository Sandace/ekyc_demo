package com.example.ekycdemo.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.caverock.androidsvg.SVGImageView;
import com.example.ekycdemo.R;
import com.example.ekycdemo.models.HomeResponseModel.Services;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    Context context;
    private ArrayList<Services> services;


    public ServicesAdapter(Context context,ArrayList<Services> services) {
        this.context = context;
        this.services = services;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final SVGImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.service_name);
            imageView  = (SVGImageView) view.findViewById(R.id.service_icon);
        }

        public TextView getTextView() {
            return textView;
        } public SVGImageView getImageView() {
            return imageView;
        }
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.services_list, parent, false);
        return new ServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {
        LoadSVGImageUtil.loadSvgImage(services.get(position).image,holder.getImageView());
        holder.getTextView().setText(services.get(position).name);

    }

    @Override
    public int getItemCount() {
        return services.size();
    }


 }
