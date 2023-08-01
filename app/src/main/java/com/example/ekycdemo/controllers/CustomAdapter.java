package com.example.ekycdemo.controllers;


import android.content.Context;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.example.ekycdemo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.caverock.androidsvg.SVGImageView;
import com.example.ekycdemo.models.Country;
import com.example.ekycdemo.models.CountryList;


public class CustomAdapter extends BaseAdapter implements Filterable {
    private Context mContext;

    private List<Country> countries;
    private List<Country> filteredCountries;

    public CustomAdapter(Context mContext, List<Country> countries) {
        this.mContext = mContext;
        this.countries = countries;
        this.filteredCountries = countries;
    }


    @Override
    public int getCount() {
        if (filteredCountries != null) {
            return filteredCountries.size();
        }
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredCountries.get(position).countryNameEn;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item, parent, false);
        TextView title;
        SVGImageView i1;
        i1 = (SVGImageView) row.findViewById(R.id.imgIcon);
        title = (TextView) row.findViewById(R.id.txtTitle);

        title.setText(filteredCountries.get(position).countryNameEn);
        loadSvgImage(filteredCountries.get(position).flag, i1);

        return (row);
    }

    public void loadSvgImage(String url, SVGImageView imageView) {
        Handler handler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        SVG svg = SVG.getFromString(response.body().string());
                        handler.post(() -> imageView.setSVG(svg));
                    } catch (SVGParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = countries.size();
                    filterResults.values = countries;

                } else {
                    List<Country> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();
                    for (Country itemsModel : countries) {
                        if (itemsModel.countryNameEn.toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }
                    }
                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCountries = (List<Country>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public String getIconUrl(int index){
        return filteredCountries.get(index).flag;
    }
}
