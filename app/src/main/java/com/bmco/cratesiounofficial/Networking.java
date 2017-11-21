package com.bmco.cratesiounofficial;

import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.Dependency;
import com.bmco.cratesiounofficial.models.Summary;
import com.bmco.cratesiounofficial.models.User;
import com.bmco.cratesiounofficial.models.Version;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.bmco.cratesiounofficial.Utility.ME;
import static com.bmco.cratesiounofficial.Utility.SUMMARY;

/**
 * Created by Bertus on 25-5-2017.
 */

public class Networking {

    private static HashMap<String, String> cachedReadmes = new HashMap<>();
    private static HashMap<String, List<Dependency>> cachedDependencies = new HashMap<>();

    public static User getMe(String token) throws IOException {
        final String[] result = new String[1];
        Utility.getSSL(Utility.getAbsoluteUrl(ME), new AsyncHttpResponseHandler() {
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

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jResult.getJSONObject("user").toString(), User.class);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Summary getSummary() throws IOException {
        final String[] result = new String[1];
        Utility.getSSL(Utility.getAbsoluteUrl(SUMMARY), new AsyncHttpResponseHandler() {
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
        Utility.getSSL(Utility.getAbsoluteUrl(url), new AsyncHttpResponseHandler() {
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
                crate.getDependencies(null);
                crateList.add(crate);
            }
            return crateList;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Dependency> getDependenciesForCrate(String id, String version) {
        String url = String.format(Locale.US, Utility.DEPENDENCIES, id, version);
        if (cachedDependencies.get(id + version) != null) {
            return cachedDependencies.get(id + version);
        }

        final String[] result = new String[1];
        Utility.getSSL(Utility.getAbsoluteUrl(url), new AsyncHttpResponseHandler() {
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
            JSONArray dependencies = jResult.getJSONArray("dependencies");

            ObjectMapper mapper = new ObjectMapper();
            List<Dependency> dependencyList = new ArrayList<>();

            for (int i = 0; i < dependencies.length(); i++) {
                String jsonDependency = dependencies.getJSONObject(i).toString();
                Dependency dependency = mapper.readValue(jsonDependency, Dependency.class);
                dependencyList.add(dependency);
            }
            cachedDependencies.put(id + version, dependencyList);
            return dependencyList;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static Crate getCrateById(String id) {
        String url = String.format(Locale.US, Utility.CRATE, id);

        final String[] result = new String[1];
        Utility.getSSL(Utility.getAbsoluteUrl(url), new AsyncHttpResponseHandler() {
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
            JSONObject jsCrate = jResult.getJSONObject("crate");
            JSONArray jsVersions = jResult.getJSONArray("versions");

            ObjectMapper mapper = new ObjectMapper();

            Crate crate = mapper.readValue(jsCrate.toString(), Crate.class);
            List<Version> versions = new ArrayList<>();
            for (int i = 0; i < jsVersions.length(); i++) {
                Version version = mapper.readValue(jsVersions.getJSONObject(i).toString(), Version.class);
                versions.add(version);
            }
            String readme = Networking.getReadme(id, versions.get(0).getNum());
            versions.get(0).setReadme(readme);
            crate.setVersionList(versions);
            return crate;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getReadme(String id, String version) {
        if (cachedReadmes.get(id + version) != null) {
            return cachedReadmes.get(id + version);
        }
        String url = String.format(Locale.US, Utility.README, id, version);

        final String[] result = new String[1];
        Utility.getSSL(Utility.getAbsoluteUrl(url), new AsyncHttpResponseHandler() {
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

        cachedReadmes.put(id + version, result[0]);
        return result[0];
    }

    public static List<Crate> getCratesByUserId(int userId) {
        String url = String.format(Locale.US, Utility.CRATES_BY_USERID, userId);

        final String[] result = new String[1];
        Utility.getSSL(Utility.getAbsoluteUrl(url), new AsyncHttpResponseHandler() {
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
            JSONArray jsCrates = jResult.getJSONArray("crates");

            ObjectMapper mapper = new ObjectMapper();

            List<Crate> crates = new ArrayList<>();

            for (int i = 0; i < jsCrates.length(); i++) {
                crates.add(mapper.readValue(jsCrates.getJSONObject(i).toString(), Crate.class));
            }

            return crates;
        } catch (JSONException | IOException e) {
            return null;
        }
    }
}
