package com.gari.canarychallenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.Manifest;


import com.gari.canarychallenge.contentefragment.FiftenkioFragment;
import com.gari.canarychallenge.contentefragment.FiftenmileFragment;
import com.gari.canarychallenge.contentefragment.HundmileFragment;
import com.gari.canarychallenge.contentefragment.SevenmileFragment;
import com.gari.canarychallenge.contentefragment.SupportFragment;
import com.gari.canarychallenge.contentefragment.TurnbyturnFragment;
import com.gari.canarychallenge.contentefragment.fivekioFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import android.location.LocationListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class selectActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String MyPREFERENCES = "MyPrefs";
    protected static final String TAG = "MainActivity";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    double latitude;
    double longitude;
    FloatingActionButton fab;
    FloatingActionButton fab_map;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private LocationManager locationManager;
    private String provider;
    final public static int SEND_SMS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
//        checkAndroidVersion();
        buildGoogleApiClient();
//     GPS location provider
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("flag", "100mile");
        editor.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Canary challenge 2016");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new HundmileFragment()).commit();
        fab = (FloatingActionButton) findViewById(R.id.fab_contente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new TurnbyturnFragment()).commit();
                fab_map.show();
                fab.hide();
            }
        });
        fab_map = (FloatingActionButton) findViewById(R.id.fab_map);
        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = sharedPreferences.getString("flag", null);

                if (flag.equals("5km")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new fivekioFragment()).commit();
                    fab_map.hide();
                    fab.show();
                } else if (flag.equals("50km")) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new FiftenkioFragment()).commit();
                    fab_map.hide();
                    fab.show();

                } else if (flag.equals("50mile")) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new FiftenmileFragment()).commit();
                    fab_map.hide();
                    fab.show();

                } else if (flag.equals("75km")) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new SevenmileFragment()).commit();
                    fab_map.hide();
                    fab.show();

                } else if (flag.equals("100mile")) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new HundmileFragment()).commit();
                    fab_map.hide();
                    fab.show();

                } else {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new HundmileFragment()).commit();
                    fab_map.hide();
                    fab.show();

                }


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

    //    public boolean isProviderEnabled(Context ctx, String provider){
//        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(provider);
//    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.fivekiro) {

            setTitle("5 Km");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection = getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner, new fivekioFragment());
            hounderdframenttrangection.commit();
            fab_map.hide();
            fab.show();
            editor.putString("flag", "5km");
            editor.commit();


            // Handle the camera action
        } else if (id == R.id.fiftenkiro) {
            setTitle("50 Km");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection = getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner, new FiftenkioFragment());
            hounderdframenttrangection.commit();
            fab_map.hide();
            fab.show();
            editor.putString("flag", "50km");
            editor.commit();

        } else if (id == R.id.fiftenmile) {

            setTitle("50 Mile");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection = getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner, new FiftenmileFragment());
            hounderdframenttrangection.commit();
            fab_map.hide();
            fab.show();
            editor.putString("flag", "50mile");
            editor.commit();


        } else if (id == R.id.sevenmile) {

            setTitle("75 Km");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection = getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner, new SevenmileFragment());
            hounderdframenttrangection.commit();
            fab_map.hide();
            fab.show();
            editor.putString("flag", "75km");
            editor.commit();


        } else if (id == R.id.hounderd) {
            setTitle("100 Mile");
            android.support.v4.app.FragmentTransaction hounderdframenttrangection = getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner, new HundmileFragment());
            hounderdframenttrangection.commit();
            fab_map.hide();
            fab.show();
            editor.putString("flag", "100mile");
            editor.commit();

        } else if (id == R.id.help) {
            Bundle bundle=new Bundle();
            onConnected(bundle);
            SupportFragment supportFragment = new SupportFragment();
            String latstr = Double.toString(latitude);
            String lngstr = Double.toString(longitude);

            String senddata = latstr + ":" + lngstr;
            bundle.putString("locationdata", senddata);
            supportFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction hounderdframenttrangection = getSupportFragmentManager().beginTransaction();
            hounderdframenttrangection.replace(R.id.fragment_contaner, supportFragment);
            hounderdframenttrangection.commit();
            fab_map.show();
            fab.hide();
            Log.d("Positiondata", senddata);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void checkAndroidVersion(){
      Bundle bundle=new Bundle();
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(selectActivity.this,Manifest.permission.SEND_SMS);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(selectActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},124);
                ActivityCompat.requestPermissions(selectActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},125);

                return;
            }else{
                onConnected(bundle);
            }
        } else {
            onConnected(bundle);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(selectActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},124);
            ActivityCompat.requestPermissions(selectActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},125);
            return;
        }
        else {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                Log.d("mycurrentlocation is",Double.toString(mLastLocation.getLatitude()));
                latitude=mLastLocation.getLatitude();
                longitude=mLastLocation.getLongitude();
//            Toast.makeText(this,String.format("%s: %f",
//                    mLastLocation.getLatitude()),Toast.LENGTH_LONG);
//            Toast.makeText(this,String.format("%s: %f",
//                    mLastLocation.getLongitude()),Toast.LENGTH_LONG);

            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show();
            }

        }




    }

    @Override
    public void onConnectionSuspended(int i) {

        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }
}
