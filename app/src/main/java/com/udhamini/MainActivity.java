package com.udhamini;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.udhamini.NavigationDrawerConstants;
import com.udhamini.GalleryFragment;
import com.udhamini.HomeFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        // Loading profile image
        ImageView profileImage = navHeader.findViewById(R.id.profileImage);
        Glide.with(this).load(NavigationDrawerConstants.PROFILE_URL)
                .apply(RequestOptions.circleCropTransform())
                .thumbnail(0.5f)
                .into(profileImage);
        //Loading backgrounf image
        ImageView navBackground = navHeader.findViewById(R.id.img_header_bg);
        Glide.with(this).load(NavigationDrawerConstants.BACKGROUND_URL)
                .thumbnail(0.5f)
                .into(navBackground);


        //Select Home by default
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
        } else if (id == R.id.nav_gallery) {
            fragment = new GalleryFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_videos) {
//            fragment = new VideosFragment();
//            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_settings) {
//            fragment = new SettingsFragment();
//            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_share) {
            //Display Share Via dialogue
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType(NavigationDrawerConstants.SHARE_TEXT_TYPE);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, NavigationDrawerConstants.SHARE_TITLE);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, NavigationDrawerConstants.SHARE_MESSAGE);
            startActivity(Intent.createChooser(sharingIntent, NavigationDrawerConstants.SHARE_VIA));

        } else if (id == R.id.nav_visit_us) {
            //Open URL on click of Visit Us
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NavigationDrawerConstants.SITE_URL));
            startActivity(urlIntent);
        }

        else if (id == R.id.cv) {
            //Open URL on click of Visit Us
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NavigationDrawerConstants.SITE_URL));
            startActivity(urlIntent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads the specified fragment to the frame
     *
     * @param fragment
     */
    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}