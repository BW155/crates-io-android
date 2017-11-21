package com.bmco.cratesiounofficial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.User;
import com.bmco.cratesiounofficial.recyclers.DashboardRecyclerAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    private TextView profileUsername;
    private FontFitTextView crateCount, crateDownloads;
    private CircleImageView profileImage;
    private RecyclerView myCrates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        profileImage = findViewById(R.id.profile_image);
        profileUsername = findViewById(R.id.profile_username);
        crateCount = findViewById(R.id.crate_count);
        crateDownloads = findViewById(R.id.crate_downloads);

        myCrates = findViewById(R.id.my_crates);

        if (Utility.loadData("token", String.class) != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        final User user = Networking.getMe(Utility.loadData("token", String.class));
                        profileImage.post(() -> profileUsername.setText(user.getLogin()));
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                final List<Crate> crates = Networking.getCratesByUserId(user.getId());
                                myCrates.post(() -> {
                                    myCrates.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
                                    myCrates.setAdapter(new DashboardRecyclerAdapter(DashboardActivity.this, crates));
                                    crateCount.setText(NumberFormat.getNumberInstance().format(crates.size()));
                                    int downloads = 0;
                                    for (Crate c: crates) {
                                        downloads += c.getDownloads();
                                    }
                                    crateDownloads.setText(NumberFormat.getNumberInstance().format(downloads));
                                });
                            }
                        };
                        thread.start();
                        Utility.getSSL(user.getAvatar(), new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                final Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                profileImage.post(() -> profileImage.setImageBitmap(bitmap));
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
