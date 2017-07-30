package com.bmco.cratesiounofficial;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bmco.cratesiounofficial.models.Alert;
import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.Dependency;
import com.google.gson.reflect.TypeToken;
import com.varunest.sparkbutton.SparkButton;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

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

        final SparkButton alertButton = (SparkButton) findViewById(R.id.alert_button);

        FloatingTextButton homepage = (FloatingTextButton) findViewById(R.id.home_link);
        FloatingTextButton repo = (FloatingTextButton) findViewById(R.id.rep_link);
        FloatingTextButton docs = (FloatingTextButton) findViewById(R.id.doc_link);

        if (crate.getHomepage() != null) {
            homepage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
            docs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
            repo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        maxVersion.setText(crate.getMaxVersion());

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.getDefault());
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
                    final FloatingTextButton button = (FloatingTextButton)  LayoutInflater.from(dependencies.getContext()).inflate(R.layout.link_button, null);
                    button.setTitle(d.getCrateId());
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

        final Context context = this;

        List<Alert> alerts;
        Type listType = new TypeToken<ArrayList<Alert>>(){}.getType();
        alerts = Utility.loadData("alerts", listType);
        if (alerts == null) {
            alerts = new ArrayList<>();
        }

        final List<Alert> finalAlerts = alerts;

        for (Alert alert: finalAlerts) {
            if (alert.getCrate().getName().equals(crate.getName())) {
                alertButton.setChecked(alert.isDownloads() || alert.isVersion());
                break;
            }
        }

        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.alert_activate_dialog, null);
                final CheckBox downloads = (CheckBox) view.findViewById(R.id.downloads);
                final CheckBox version = (CheckBox) view.findViewById(R.id.version);
                dialog.setView(view);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Alert alert: finalAlerts) {
                            if (alert.getCrate().getName().equals(crate.getName())) {
                                alert.setDownloads(downloads.isChecked());
                                alert.setVersion(version.isChecked());
                                Utility.saveData("alerts", finalAlerts);
                                if (alert.isDownloads() || alert.isVersion()) {
                                    alertButton.setChecked(true);
                                    alertButton.playAnimation();
                                } else {
                                    alertButton.setChecked(false);
                                }
                                return;
                            }
                        }
                        Alert alert = new Alert(version.isChecked(), downloads.isChecked(), crate);
                        finalAlerts.add(alert);
                        Utility.saveData("alerts", finalAlerts);
                        if (alert.isDownloads() || alert.isVersion()) {
                            alertButton.setChecked(true);
                            alertButton.playAnimation();
                        }
                    }
                });
                for (Alert alert: finalAlerts) {
                    if (alert.getCrate().getName().equals(crate.getName())) {
                        downloads.setChecked(alert.isDownloads());
                        version.setChecked(alert.isVersion());
                        break;
                    }
                }
                dialog.show();
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
