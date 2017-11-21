package com.bmco.cratesiounofficial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.bmco.cratesiounofficial.Networking.getCratesByUserId;

public class DashboardActivity extends AppCompatActivity {

    private TextView profileUsername;
    private CircleImageView profileImage;
    private RecyclerView myCrates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileImage = findViewById(R.id.profile_image);
        profileUsername = findViewById(R.id.profile_username);
        myCrates = findViewById(R.id.my_crates);

        if (Utility.loadData("token", String.class) != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        final User user = Networking.getMe((String) Utility.loadData("token", String.class));
                        profileImage.post(new Runnable() {
                            @Override
                            public void run() {
                                profileUsername.setText(user.getLogin());
                            }
                        });
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                List<Crate> crates = Networking.getCratesByUserId(user.getId());

                            }
                        };
                        thread.start();
                        Utility.getSSL(user.getAvatar(), new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                final Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                profileImage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        profileImage.setImageBitmap(bitmap);
                                    }
                                });
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
}
