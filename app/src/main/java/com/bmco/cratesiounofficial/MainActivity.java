package com.bmco.cratesiounofficial;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.bmco.cratesiounofficial.fragments.SearchFragment;
import com.bmco.cratesiounofficial.fragments.SummaryFragment;
import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.Summary;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static OnResult result;

    private SearchView searchView;
    private TextView downloads, crates;
    private NonSwipeableViewPager summarySearchPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.InitSaveLoad(this);

        Intent i = new Intent(this, CrateNotifier.class);
        startService(i);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        summarySearchPager = (NonSwipeableViewPager) findViewById(R.id.summary_search_pager);
        summarySearchPager.setAdapter(new SummarySearchPageAdapter(getSupportFragmentManager()));

        downloads = header.findViewById(R.id.downloads);
        crates = header.findViewById(R.id.crates);

        SummaryFragment.listener.add( new OnSummaryChangeListener() {
            @Override
            public void summary(final Summary summary) {
                crates.post(new Runnable() {
                    @Override
                    public void run() {
                        DecimalFormat df = new DecimalFormat("#,##0");
                        crates.setText(df.format(Long.valueOf(summary.getNumCrates())));
                        downloads.setText(df.format(Long.valueOf(summary.getNumDownloads())));
                    }
                });
            }

            @Override
            public void downloadStarted() {

            }
        });
    }

    private class SummarySearchPageAdapter extends FragmentPagerAdapter {

        public SummarySearchPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SummaryFragment();
                case 1:
                    return new SearchFragment();
                default:
                    return null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (summarySearchPager.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                summarySearchPager.setCurrentItem(summarySearchPager.getCurrentItem() - 1);
                searchView.setIconified(true);
                searchView.setIconified(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                System.out.println("on search submit: " + query);
                Thread searchThread = new Thread() {
                    public void run() {
                        result.needsClear();
                        result.downloading();
                        List<Crate> crates = CratesIONetworking.searchCrate(query, 1);
                        for (Crate crate: crates) {
                            result.onResult(crate);
                        }
                        searchView.post(new Runnable() {
                            @Override
                            public void run() {
                                searchView.clearFocus();
                            }
                        });
                    }
                };
                searchThread.start();
                MainActivity.result.onResult(new Crate());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    if (summarySearchPager.getCurrentItem() != 1) {
                        summarySearchPager.setCurrentItem(1, true);
                    }
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                System.out.println("on search close");
                summarySearchPager.setCurrentItem(0, true);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("About");
            dialog.setMessage(getResources().getString(R.string.about));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OKE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        if (id == R.id.action_cargo) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://crates.io/"));
            startActivity(browserIntent);
        }

        if (id == R.id.action_subscribed) {
            Intent intent = new Intent(this, SubscribedActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
