package com.example.ekycdemo.controllers;

import android.os.Handler;
import android.os.Looper;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadSVGImageUtil {
    public static void loadSvgImage(String url, SVGImageView imageView) {
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
}
