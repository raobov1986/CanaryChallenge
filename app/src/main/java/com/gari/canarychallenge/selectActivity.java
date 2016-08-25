package com.gari.canarychallenge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gari.canarychallenge.contentefragment.FiftenkioFragment;
import com.gari.canarychallenge.contentefragment.FiftenmileFragment;
import com.gari.canarychallenge.contentefragment.HundmileFragment;
import com.gari.canarychallenge.contentefragment.SevenmileFragment;
import com.gari.canarychallenge.contentefragment.SupportFragment;
import com.gari.canarychallenge.contentefragment.TurnbyturnFragment;
import com.gari.canarychallenge.contentefragment.fivekioFragment;

public class selectActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    FloatingActionButton fab_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Canary challenge 2016");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner,new HundmileFragment()).commit();
        fab = (FloatingActionButton) findViewById(R.id.fab_contente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner,new TurnbyturnFragment()).commit();
            }
        });
        fab_map=(FloatingActionButton)findViewById(R.id.fab_map);
        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner,new fivekioFragment()).commit();

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.fivekiro) {

            setTitle("5 Km");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection=getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner,new fivekioFragment());
            hounderdframenttrangection.commit();
            fab_map.hide();



            // Handle the camera action
        } else if (id == R.id.fiftenkiro) {
            setTitle("50 Km");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection=getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner,new FiftenkioFragment());
            hounderdframenttrangection.commit();

        } else if (id == R.id.fiftenmile) {

            setTitle("50 Mile");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection=getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner,new FiftenmileFragment());
            hounderdframenttrangection.commit();


        } else if (id == R.id.sevenmile) {

            setTitle("75 Km");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection=getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner,new SevenmileFragment());
            hounderdframenttrangection.commit();


        }else if (id == R.id.hounderd) {
            setTitle("100 Mile");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection=getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner,new HundmileFragment());
            hounderdframenttrangection.commit();

        }
        else if (id == R.id.help) {

            android.support.v4.app.FragmentTransaction hounderdframenttrangection=getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner,new SupportFragment());
            hounderdframenttrangection.commit();
            fab_map.show();
            fab.hide();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
