package com.example.ekycdemo.controllers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ekycdemo.R;
import java.util.ArrayList;

public class ChooseFileAdapter extends RecyclerView.Adapter<ChooseFileAdapter.ViewHolder> {

    private ArrayList<String> fileNames;
    Context context;
    public ChooseFileAdapter(Context context, ArrayList<String> fileNames) {
        this.context = context;
        this.fileNames = fileNames;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.fileNameTextView);
            imageView  = (ImageView) view.findViewById(R.id.delete_icon);
        }

        public TextView getTextView() {
            return textView;
        } public ImageView getImageView() {
            return imageView;
        }
    }
    @NonNull
    @Override
    public ChooseFileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.upload_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseFileAdapter.ViewHolder viewHolder, int position) {
        viewHolder.getImageView().setOnClickListener(v -> {
            fileNames.remove(position);
            notifyDataSetChanged();
        });
        viewHolder.getTextView().setText(fileNames.get(position));
    }

    @Override
    public int getItemCount() {
        return fileNames.size();
    }
}