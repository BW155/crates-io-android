package com.bmco.cratesiounofficial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.Dependency;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crate);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Crate crate = (Crate)getIntent().getSerializableExtra("crate");
        setTitle(crate.getName());

        TextView downloads = (TextView) findViewById(R.id.crate_downloads);
        TextView maxVersion = (TextView) findViewById(R.id.crate_max_version);
        TextView createdAt = (TextView) findViewById(R.id.crate_created_at);
        TextView description = (TextView) findViewById(R.id.crate_description);

        Button homepage = (Button) findViewById(R.id.home_link);
        Button repo = (Button) findViewById(R.id.rep_link);
        Button docs = (Button) findViewById(R.id.doc_link);

        if (crate.getHomepage() != null) {
            homepage.setEnabled(true);
            homepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(String.valueOf(crate.getHomepage())));
                    startActivity(intent);
                }
            });
        }

        if (crate.getDocumentation() != null) {
            docs.setEnabled(true);
            docs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(String.valueOf(crate.getDocumentation())));
                    startActivity(intent);
                }
            });
        }

        if (crate.getRepository() != null) {
            repo.setEnabled(true);
            repo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(String.valueOf(crate.getRepository())));
                    startActivity(intent);
                }
            });
        }

        DecimalFormat df = new DecimalFormat("#,##0");
        downloads.setText(df.format(Long.valueOf(crate.getDownloads())));
        maxVersion.setText("v" + crate.getMaxVersion());

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        try {
            Date date = formatter.parse(crate.getCreatedAt());
            createdAt.setText(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        description.setText(crate.getDescription());

        final LinearLayout dependencies = (LinearLayout) findViewById(R.id.dependency_group);

        crate.getDependencies(new OnDependencyDownloadListener() {
            @Override
            public void onDependenciesReady(List<Dependency> dependencyList) {
                for (final Dependency d: dependencyList) {
                    final Button button = new Button(dependencies.getContext());
                    button.setText(d.getCrateId());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog dialog = new AlertDialog.Builder(dependencies.getContext()).create();
                            dialog.setTitle("Loading");
                            dialog.setCancelable(false);
                            dialog.setView(LayoutInflater.from(dependencies.getContext()).inflate(R.layout.load_alert, null));
                            dialog.show();
                            Thread crateThread = new Thread() {
                                public void run() {
                                    Intent intent = new Intent(dependencies.getContext(), CrateActivity.class);
                                    intent.putExtra("crate", CratesIONetworking.getCrateById(d.getCrateId()));
                                    dialog.dismiss();
                                    startActivity(intent);
                                }
                            };
                            crateThread.start();
                        }
                    });
                    dependencies.post(new Runnable() {
                        @Override
                        public void run() {
                            dependencies.addView(button);
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
