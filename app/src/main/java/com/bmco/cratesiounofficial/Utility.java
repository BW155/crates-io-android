package com.bmco.cratesiounofficial;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

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
}
