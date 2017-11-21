package com.bmco.cratesiounofficial;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bmco.cratesiounofficial.fragments.SearchFragment;
import com.bmco.cratesiounofficial.fragments.SummaryFragment;
import com.bmco.cratesiounofficial.interfaces.OnCrateResult;
import com.bmco.cratesiounofficial.interfaces.OnSummaryChangeListener;
import com.bmco.cratesiounofficial.models.Crate;
import com.bmco.cratesiounofficial.models.Summary;
import com.bmco.cratesiounofficial.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static OnCrateResult result;

    private SearchView searchView;
    private TextView downloads, crates, profileUsername;
    private NonSwipeableViewPager summarySearchPager;
    private LinearLayout profileSection;
    private CircleImageView profileImage;
    private Menu menu;

    public static final String token = "ZQRN3oC1CIabzbHf5eCM45UVHbB0Ae1p";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.InitSaveLoad(this);

        Intent i = new Intent(this, CrateNotifier.class);
        startService(i);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        summarySearchPager = findViewById(R.id.summary_search_pager);
        summarySearchPager.setAdapter(new SummarySearchPageAdapter(getSupportFragmentManager()));

        downloads = header.findViewById(R.id.downloads);
        crates = header.findViewById(R.id.crates);
        profileSection = header.findViewById(R.id.profile_section);
        profileImage = header.findViewById(R.id.profile_image);
        profileUsername = header.findViewById(R.id.profile_username);
        profileSection.setVisibility(View.INVISIBLE);

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
        menu = navigationView.getMenu();
        loadNavProfile();
    }

    private void loadNavProfile() {
        final MenuItem login = menu.findItem(R.id.action_login);
        final MenuItem profile = menu.findItem(R.id.action_dashboard);
        if (Utility.loadData("token", String.class) != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        final User user = Networking.getMe((String) Utility.loadData("token", String.class));
                        profileSection.post(new Runnable() {
                            @Override
                            public void run() {
                                profileSection.setVisibility(View.VISIBLE);
                                profileUsername.setText(user.getLogin());
                                login.setVisible(false);
                                profile.setVisible(true);
                            }
                        });
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        if (searchView != null) {
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
                            List<Crate> crates = Networking.searchCrate(query, 1);
                            for (Crate crate : crates) {
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
        }
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

        if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 200);
        }

        if (id == R.id.action_dashboard) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200) {
            this.loadNavProfile();
        }
    }
}
