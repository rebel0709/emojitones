package com.emojitones.keyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.emojitones.keyboard.fragments.MainFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.getContentInsetEnd();
        toolbar.setPadding(0, 0, 0, 0);
        View v = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        getSupportActionBar().setDisplayOptions(16);
        getSupportActionBar().setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = -1;
        v2.setLayoutParams(lp);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (drawer.isDrawerVisible((int) GravityCompat.START)) {
                    drawer.closeDrawer((int) GravityCompat.START);
                } else {
                    drawer.openDrawer((int) GravityCompat.START);
                }
            }
        });
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView navMenuView = (NavigationMenuView) this.navigationView.getChildAt(0);
        DividerItemDecoration decor = new DividerItemDecoration(getApplicationContext(), 1);
        decor.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.nav_menu_separator));
        navMenuView.addItemDecoration(decor);
//        ((TextView) findViewById(R.id.nav_by)).setText(String.format(getString(R.string.nav_version_prod), new Object[]{BuildConfig.VERSION_NAME}));
        toggle.setHomeAsUpIndicator((int) R.drawable.icon_gear32dp);

        showEmojiFragment();
        requestPermissions();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_install) {
            // Handle the camera action
            Intent intent = new Intent(this, InstallKeyboardActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_invite) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_privacy) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean requestPermissions() {
        boolean permission_status;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            permission_status = true;
        } else {
            permission_status = false;
        }
        if (!permission_status) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
        return permission_status;
    }

    private void showEmojiFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, MainFragment.newInstance()).commit();
    }

}
