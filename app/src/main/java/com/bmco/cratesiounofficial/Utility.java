package com.bmco.cratesiounofficial;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Bertus on 25-5-2017.
 */

public class Utility {
    private static String BASE_URL = "https://crates.io/";
    public static String SUMMARY = "summary?_=%d"; /// + Timestamp in millis
    public static String SEARCH = "api/v1/crates?page=%d&per_page=100&q=%s&sort=&_=%d"; /// where 1: page number, 2: query, 3: sort type
    public static String DEPENDENCIES = "api/v1/crates/%s/%s/dependencies"; /// where 1: crate id
    public static String CRATE = "api/v1/crates/%s"; /// where 1: crate id

    public static void getSSL(String url, AsyncHttpResponseHandler responseHandler) {
        try {
            URL page = new URL(getAbsoluteUrl(url)); // Process the URL far enough to find the right handler
            URLConnection urlConnection = page.openConnection();
            urlConnection.setUseCaches(false); // Don't look at possibly cached data
            System.out.println("Content-type = " + urlConnection.getContentType()); // See what's here
            System.out.println("Content-length = " + urlConnection.getContentLength()); // See how much of it there is
            // Read it all and print it out
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String buffer = "";
            String line;
            while ((line = br.readLine()) != null) {
                buffer += line;
            }
            responseHandler.sendSuccessMessage(200, null, buffer.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            responseHandler.sendFailureMessage(0, null, new byte[1], e);
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static SharedPreferences settings;

    public static void InitSaveLoad(Context context) {
        settings = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public static void saveData(String key, Object value) {
        String data = new Gson().toJson(value);
        settings.edit().putString(key, data).apply();
    }

    public static <T> T loadData(String key, Type type) throws JsonSyntaxException {
        return (T) new Gson().fromJson(settings.getString(key, ""), type);
    }
}
