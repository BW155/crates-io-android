package com.bmco.cratesiounofficial.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bmco.cratesiounofficial.Networking;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.Utility;
import com.bmco.cratesiounofficial.models.User;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText apiToken;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        apiToken = findViewById(R.id.api_token);
        confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Utility.saveData("token", apiToken.getText().toString().trim());
                            User user = Networking.getMe(apiToken.getText().toString().trim());
                            if (user != null) {
                               apiToken.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       finish();
                                   }
                               });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
