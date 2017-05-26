package com.bmco.cratesiounofficial;

import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.Summary;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.bmco.cratesiounofficial.Utility.SUMMARY;

/**
 * Created by Bertus on 25-5-2017.
 */

public class CratesIONetworking {

    public static Summary getSummary() throws IOException {
        final String[] result = new String[1];
        Utility.getSSL(String.format(SUMMARY, new Date().getTime()), new AsyncHttpResponseHandler() {
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

    public static List<Crate> searchCrate(String query, int page) {
        String url = String.format(Locale.US, Utility.SEARCH, page, query, new Date().getTime());

        final String[] result = new String[1];
        Utility.getSSL(url, new AsyncHttpResponseHandler() {
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

        try {
            JSONObject jResult = new JSONObject(result[0]);
            JSONArray crates = jResult.getJSONArray("crates");

            ObjectMapper mapper = new ObjectMapper();
            List<Crate> crateList = new ArrayList<>();

            for (int i = 0; i < crates.length(); i++) {
                String jsonCrate = crates.getJSONObject(i).toString();
                Crate crate = mapper.readValue(jsonCrate, Crate.class);
                crateList.add(crate);
            }
            return crateList;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
