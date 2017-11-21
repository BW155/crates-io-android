package com.bmco.cratesiounofficial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bmco.cratesiounofficial.models.Alert;
import com.bmco.cratesiounofficial.recyclers.SubscribedAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubscribedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.subscribed_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Type listType = new TypeToken<ArrayList<Alert>>(){}.getType();
        List<Alert> alerts = Utility.loadData("alerts", listType);

        TextView emptyView = findViewById(R.id.empty_view);
        if (alerts == null || alerts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setAdapter(new SubscribedAdapter(this, alerts));
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
