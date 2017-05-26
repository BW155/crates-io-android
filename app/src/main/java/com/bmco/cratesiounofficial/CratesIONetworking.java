package com.bmco.cratesiounofficial;

import com.bmco.cratesiounofficial.models.Summary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

import static com.bmco.cratesiounofficial.Utility.SUMMARY;

/**
 * Created by Bertus on 25-5-2017.
 */

public class CratesIONetworking {

    public static Summary getSummary() throws IOException {
        final String[] result = new String[1];
        Utility.getSSL(SUMMARY, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody.length > 0) {
                    result[0] = new String(responseBody);
                } else {
                    result[0] = "ERROR";
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody.length > 0) {
                    result[0] = new String(responseBody);
                } else {
                    result[0] = "ERROR";
                }
            }
        });

        while (result[0] == null) {
            //ignore
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result[0], Summary.class);
    }
}
