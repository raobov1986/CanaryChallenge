package com.gari.canarychallenge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.location.Location;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.gari.canarychallenge.Utilities.DataParser;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Context mContext;
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    ArrayList<LatLng> fivemilepoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    public String routes;
    LocationRequest mLocationRequest;
    PolylineOptions lineOptions;
//    Button btn_sendVerificationCode;
    EditText edt_phoneNo;
    String mobile;
    final public static int SEND_SMS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Initializing
        MarkerPoints = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fivemilepoints=new ArrayList<>();
        ImageButton distance_imagebutton=(ImageButton)findViewById(R.id.bicycly_imageButton);
        distance_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShowAlertDialogWithListview();



            }
        });



    }
    public void ShowAlertDialogWithListview()
    {
        List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("5 km");
        mAnimals.add("50 km");
        mAnimals.add("50 mile");
        mAnimals.add("75 km");
        mAnimals.add("100 mile");
        mAnimals.add("Tury by tury diretion");


        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();
                Toast.makeText(MapsActivity.this,selectedText,Toast.LENGTH_LONG).show();
                //Selected item in listview
                Log.d("list is select",selectedText);
                if (selectedText.equals("50 mile")){
                    routes=selectedText;
                    mMap.clear();
                    showfivemile();
                }
                else if(selectedText.equals("50 km")){
                    mMap.clear();
                    Toast.makeText(MapsActivity.this,selectedText,Toast.LENGTH_LONG).show();
                    showfivekiro();



                }
                else if(selectedText.equals("75 km")){


                    mMap.clear();
                    Toast.makeText(MapsActivity.this,selectedText,Toast.LENGTH_LONG).show();
                    showsevenmile();

                }
                else if(selectedText.equals("100 mile")){


                    mMap.clear();
                    Toast.makeText(MapsActivity.this,selectedText,Toast.LENGTH_LONG).show();
                    showhounderdmile();

                }
                else if(selectedText.equals("5 km")){


                    mMap.clear();
                    Toast.makeText(MapsActivity.this,selectedText,Toast.LENGTH_LONG).show();
                    showhfivesmall();

                }
                else if(selectedText.equals("Tury by tury diretion")){


//                    mMap.clear();
                    Toast.makeText(MapsActivity.this,routes,Toast.LENGTH_LONG).show();
//                    showhfivesmall();

                }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }


//    protected void showInputDialog_enter() {
//
//        // get prompts.xml view
//        LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
//        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapsActivity.this);
//        alertDialogBuilder.setView(promptView);
//
//        final EditText editText_name_enter = (EditText) promptView.findViewById(R.id.edittext_contact_enter_name);
//        final EditText editText_phonenumber_enter = (EditText) promptView.findViewById(R.id.editText_contac_phonenumber);
//        // setup a dialog window
//        alertDialogBuilder.setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//
//
//
//
//
//                    }
//                })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create an alert dialog
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }
//
//
//
//    protected void showInputDialog_rapair() {
//
//        // get prompts.xml view
//        LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
//        View promptView = layoutInflater.inflate(R.layout.input_dialog_rapair, null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapsActivity.this);
//        alertDialogBuilder.setView(promptView);
//
//        final EditText editText_name_enter = (EditText) promptView.findViewById(R.id.edittext_contact_rapair_name);
//        final EditText editText_phonenumber_enter = (EditText) promptView.findViewById(R.id.editText_rapair_phonenumber);
//        // setup a dialog window
//        alertDialogBuilder.setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
////                        resultText.setText("Hello, " + editText.getText());
//                    }
//                })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create an alert dialog
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }
//
//    protected void showInputDialog_pickup() {
//
//        // get prompts.xml view
//        LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
//        View promptView = layoutInflater.inflate(R.layout.input_dialog_pickup, null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapsActivity.this);
//        alertDialogBuilder.setView(promptView);
//
//        final EditText editText_name_enter = (EditText) promptView.findViewById(R.id.edittext_contact_pickup_name);
//        final EditText editText_phonenumber_enter = (EditText) promptView.findViewById(R.id.editText_puckup_phonenumber);
//        // setup a dialog window
//        alertDialogBuilder.setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
////                        resultText.setText("Hello, " + editText.getText());
//                    }
//                })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create an alert dialog
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.setMyLocationEnabled(true);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.440649, -122.235912)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        showentire();

}
//    50 mile route

    public void showfivemile(){


        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.417206, -122.147011)).title("Start/Finish"));
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.503070, -122.335789)).title("Reset Stop"));
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.311951, -122.069203)).title("Reset Stop"));
        lineOptions = new PolylineOptions();
        lineOptions.color(Color.rgb(249,168,46));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(37.477978, -122.310435)).title("second position"));
        LatLng startmark=new LatLng( 37.417206, -122.147011);
        LatLng secondmark=new LatLng(37.409484, -122.160053);
        DrawArrowHead(mMap, secondmark,startmark );
        LatLng thirdmark=new LatLng(37.448335, -122.230864);
        LatLng four_mark=new LatLng(37.448256, -122.231913);
        DrawArrowHead(mMap, four_mark,thirdmark );
        LatLng fif_mark=new LatLng(37.435498, -122.259658);
        LatLng six_mark=new LatLng(37.503070, -122.335789);
        DrawArrowHead(mMap, six_mark,fif_mark );
        LatLng seven_mark=new LatLng(37.474200, -122.307152);
        LatLng seven_mark_temp=new LatLng(37.454061, -122.283613);
        LatLng mark_8=new LatLng(37.428544, -122.257860);
        DrawArrowHead(mMap, mark_8,seven_mark );
        LatLng mark_9=new LatLng(37.404318, -122.249009);
        LatLng mark_10=new LatLng(37.372308, -122.208962);
        DrawArrowHead(mMap, mark_10,mark_9 );
        LatLng mark_11=new LatLng(37.387215, -122.164021);
        LatLng mark_12=new LatLng(37.385033, -122.151718);
        DrawArrowHead(mMap, mark_12,mark_11 );
        LatLng mark_13=new LatLng(37.366582, -122.134897);//update start
        LatLng mark_14=new LatLng(37.364673, -122.116274);
        DrawArrowHead(mMap, mark_14,mark_13 );//update end
        LatLng mark_15=new LatLng(37.366901, -122.106103);
        LatLng mark_16=new LatLng(37.315871, -122.070054);
        DrawArrowHead(mMap, mark_16,mark_15 );
        LatLng mark_17=new LatLng(37.312641, -122.070040);
        LatLng mark_18=new LatLng(37.311951, -122.069203);
        DrawArrowHead(mMap, mark_18,mark_17 );
        LatLng mark_19=new LatLng(37.339746, -122.070328);//
        LatLng mark_20=new LatLng(37.369972, -122.109850);
        DrawArrowHead(mMap, mark_20,mark_19 );
        LatLng mark_21=new LatLng(37.405813, -122.154896);
        LatLng mark_22=new LatLng(37.424424, -122.191126);
        DrawArrowHead(mMap, mark_22,mark_21 );
// draw ruote by each position
        String url = getUrl(startmark, secondmark);
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
//
//
//
        String url_1 = getUrl(secondmark, thirdmark);
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl_1 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_1.execute(url_1);

        String url_2 = getUrl(thirdmark,four_mark );
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl_2 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_2.execute(url_2);


        String url_3 = getUrl(four_mark,fif_mark );
        FetchUrl FetchUrl_3 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_3.execute(url_3);

        String url_4 = getUrl(fif_mark,six_mark );
        FetchUrl FetchUrl_4 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_4.execute(url_4);

        String url_5 = getUrl(six_mark,seven_mark );
        FetchUrl FetchUrl_5 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_5.execute(url_5);

        String url_21 = getUrl(seven_mark,seven_mark_temp );
        FetchUrl FetchUrl_21 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_21.execute(url_21);

        String url_6 = getUrl(seven_mark_temp, mark_8);
        FetchUrl FetchUrl_6 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_6.execute(url_6);

        String url_7 = getUrl(mark_8, mark_9);
        FetchUrl FetchUrl_7 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_7.execute(url_7);

        String url_8 = getUrl(mark_9, mark_10);
        FetchUrl FetchUrl_8= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_8.execute(url_8);

        String url_9 = getUrl(mark_10,mark_11 );
        FetchUrl FetchUrl_9= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_9.execute(url_9);

        String url_10 = getUrl(mark_11,mark_12 );
        FetchUrl FetchUrl_10= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_10.execute(url_10);



        String url_11 = getUrl(mark_12,mark_13 );
        FetchUrl FetchUrl_11= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_11.execute(url_11);


        String url_12 = getUrl(mark_13,mark_14 );
        FetchUrl FetchUrl_12= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_12.execute(url_12);



        String url_13 = getUrl(mark_14,mark_15 );
        FetchUrl FetchUrl_13= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_13.execute(url_13);

        String url_14 = getUrl(mark_15,mark_16 );
        FetchUrl FetchUrl_14= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_14.execute(url_14);


        String url_15 = getUrl(mark_16,mark_17 );
        FetchUrl FetchUrl_15= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_15.execute(url_15);

        String url_16 = getUrl(mark_17,mark_18 );
        FetchUrl FetchUrl_16= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_16.execute(url_16);


        String url_17 = getUrl(mark_18,mark_19 );
        FetchUrl FetchUrl_17= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_17.execute(url_17);

        String url_18 = getUrl(mark_19,mark_20 );
        FetchUrl FetchUrl_18= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_18.execute(url_18);



        String url_19 = getUrl(mark_20,mark_21 );
        FetchUrl FetchUrl_19= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_19.execute(url_19);



        String url_20 = getUrl(mark_21,mark_22 );
        FetchUrl FetchUrl_20= new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_20.execute(url_20);



//        PolylineOptions options = new PolylineOptions();
//
//        options.color( Color.RED);
//        options.width( 10 );
//        options.visible( true );
//        options.add( new LatLng( 37.417206, -122.147011 ) );
//        options.add( new LatLng( 37.406458, -122.155903 ) );
//        options.add( new LatLng( 37.407955, -122.157943 ) );






//        mMap.addPolyline( options );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.406458, -122.155903)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));


    }

    public void showfivekiro(){
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.413153, -122.143819)).title("Finish"));
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.412181, -122.144594)).title("Start"));
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.503070, -122.335789)).title("Reset Stop"));
        lineOptions = new PolylineOptions();
        lineOptions.color(Color.rgb(249,168,46));
        LatLng startmark=new LatLng( 37.412181, -122.144594);
        LatLng secondmark=new LatLng(37.409484, -122.160053);
        DrawArrowHead(mMap, secondmark,startmark );
        LatLng thirdmark=new LatLng(37.448335, -122.230864);
        LatLng four_mark=new LatLng(37.448256, -122.231913);
        DrawArrowHead(mMap, four_mark,thirdmark );
        LatLng fif_mark=new LatLng(37.435498, -122.259658);
        LatLng six_mark=new LatLng(37.503070, -122.335789);
        DrawArrowHead(mMap, six_mark,fif_mark );
        LatLng seven_mark=new LatLng(37.474200, -122.307152);
        LatLng seven_mark_temp=new LatLng(37.454061, -122.283613);

        LatLng mark_8=new LatLng(37.428544, -122.257860);
        LatLng mark_9=new LatLng(37.404318, -122.249009);
        DrawArrowHead(mMap, mark_9,mark_8 );
        LatLng mark_10=new LatLng(37.372308, -122.208962);
        LatLng mark_11=new LatLng(37.424475, -122.191655);
        DrawArrowHead(mMap, mark_11,mark_10 );
        LatLng mark_12=new LatLng(37.417156, -122.146884);
        LatLng mark_13=new LatLng(37.413153, -122.143819);//update start
        DrawArrowHead(mMap, mark_13,mark_12 );
        String url = getUrl(startmark, secondmark);
        FetchUrl FetchUrl = new FetchUrl();
        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);

        String url_1 = getUrl(secondmark, thirdmark);
        FetchUrl FetchUrl_1 = new FetchUrl();
        // Start downloading json data from Google Directions API
        FetchUrl_1.execute(url_1);

        String url_2 = getUrl(thirdmark,four_mark );
        FetchUrl FetchUrl_2 = new FetchUrl();
        // Start downloading json data from Google Directions API
        FetchUrl_2.execute(url_2);


        String url_3 = getUrl(four_mark,fif_mark );
        FetchUrl FetchUrl_3 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_3.execute(url_3);

        String url_4 = getUrl(fif_mark,six_mark );
        FetchUrl FetchUrl_4 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_4.execute(url_4);

        String url_5 = getUrl(six_mark,seven_mark );
        FetchUrl FetchUrl_5 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_5.execute(url_5);

        String url_25 = getUrl(seven_mark,seven_mark_temp );
        FetchUrl FetchUrl_25 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_25.execute(url_25);

        String url_6 = getUrl(seven_mark_temp, mark_8);
        FetchUrl FetchUrl_6 = new FetchUrl();

//         Start downloading json data from Google Directions API
        FetchUrl_6.execute(url_6);

        String url_7 = getUrl(mark_8, mark_9);
        FetchUrl FetchUrl_7 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_7.execute(url_7);

        String url_8 = getUrl(mark_9, mark_10);
        FetchUrl FetchUrl_8= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_8.execute(url_8);

        String url_9 = getUrl(mark_10,mark_11 );
        FetchUrl FetchUrl_9= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_9.execute(url_9);

        String url_10 = getUrl(mark_11,mark_12 );
        FetchUrl FetchUrl_10= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_10.execute(url_10);



        String url_11 = getUrl(mark_12,mark_13 );
        FetchUrl FetchUrl_11= new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl_11.execute(url_11);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.440649, -122.235912)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    }

public void showsevenmile(){




    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.413153, -122.143819)).title("Finish"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.412181, -122.144594)).title("Start"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.424973, -122.313846)).title("Reset Stop"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.369439, -122.383567)).title("Reset Stop"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.503070, -122.335789)).title("Reset Stop"));
    lineOptions = new PolylineOptions();
    lineOptions.color(Color.rgb(0,0,255));
    LatLng startmark=new LatLng( 37.412181, -122.144594);
    LatLng secondmark=new LatLng(37.409484, -122.160053);
    DrawArrowHead(mMap, secondmark,startmark );
    LatLng thirdmark=new LatLng(37.448335, -122.230864);
    LatLng four_mark=new LatLng(37.448256, -122.231913);
    DrawArrowHead(mMap, four_mark,thirdmark );
    LatLng fif_mark=new LatLng(37.435498, -122.259658);
    LatLng six_mark=new LatLng(37.503070, -122.335789);
    DrawArrowHead(mMap, six_mark,fif_mark );
    LatLng seven_mark=new LatLng(37.474200, -122.307152);
    LatLng seven_mark_temp=new LatLng(37.454061, -122.283613);
    LatLng mark_8=new LatLng(37.428544, -122.257860);//

    LatLng mark_8_temp=new LatLng(37.429484, -122.271554);//
    DrawArrowHead(mMap, mark_8,seven_mark );
    LatLng mark_14=new LatLng(37.424973, -122.313846);
    LatLng mark_15=new LatLng(37.369439, -122.383567);
    DrawArrowHead(mMap, mark_15,mark_14 );
    LatLng mark_16=new LatLng(37.331190, -122.389575);
    LatLng mark_17=new LatLng(37.345418, -122.271906);
    DrawArrowHead(mMap, mark_17,mark_16 );
    LatLng mark_18=new LatLng(37.387644, -122.266154);
    LatLng mark_19=new LatLng(37.424958, -122.313784);
    LatLng mark_20=new LatLng(37.427681, -122.303919);
    LatLng mark_9=new LatLng(37.404318, -122.249009);//
    LatLng mark_10=new LatLng(37.372308, -122.208962);
    DrawArrowHead(mMap, mark_10,mark_9 );
    LatLng mark_11=new LatLng(37.424475, -122.191655);
    LatLng mark_12=new LatLng(37.417156, -122.146884);
    LatLng mark_13=new LatLng(37.413153, -122.143819);//update start

// draw ruote by each position
    String url = getUrl(startmark, secondmark);
    FetchUrl FetchUrl = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl.execute(url);
    String url_1 = getUrl(secondmark, thirdmark);
    Log.d("onMapClick", url.toString());
    FetchUrl FetchUrl_1 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_1.execute(url_1);

    String url_2 = getUrl(thirdmark,four_mark );
    Log.d("onMapClick", url.toString());
    FetchUrl FetchUrl_2 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_2.execute(url_2);


    String url_3 = getUrl(four_mark,fif_mark );
    FetchUrl FetchUrl_3 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_3.execute(url_3);

    String url_4 = getUrl(fif_mark,six_mark );
    FetchUrl FetchUrl_4 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_4.execute(url_4);

    String url_5 = getUrl(six_mark,seven_mark );
    FetchUrl FetchUrl_5 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_5.execute(url_5);

    String url_25 = getUrl(seven_mark,seven_mark_temp );
    FetchUrl FetchUrl_25 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_25.execute(url_25);

    String url_6 = getUrl(seven_mark_temp, mark_8);
    FetchUrl FetchUrl_6 = new FetchUrl();

//         Start downloading json data from Google Directions API
    FetchUrl_6.execute(url_6);

    String url_26 = getUrl(mark_8, mark_8_temp);
    FetchUrl FetchUrl_26 = new FetchUrl();

//         Start downloading json data from Google Directions API
    FetchUrl_26.execute(url_26);
    String url_11 = getUrl(mark_8_temp,mark_14 );
    FetchUrl FetchUrl_11= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_11.execute(url_11);

    String url_12 = getUrl(mark_14,mark_15 );
    FetchUrl FetchUrl_12= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_12.execute(url_12);

    String url_13 = getUrl(mark_15,mark_16 );
    FetchUrl FetchUrl_13= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_13.execute(url_13);


    String url_14= getUrl(mark_16,mark_17 );
    FetchUrl FetchUrl_14= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_14.execute(url_14);




    String url_15= getUrl(mark_17,mark_18 );
    FetchUrl FetchUrl_15= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_15.execute(url_15);

    String url_16= getUrl(mark_18,mark_19 );
    FetchUrl FetchUrl_16= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_16.execute(url_16);


    String url_17= getUrl(mark_19,mark_20 );
    FetchUrl FetchUrl_17= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_17.execute(url_17);



    String url_7 = getUrl(mark_20, mark_9);
    FetchUrl FetchUrl_7 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_7.execute(url_7);

    String url_8 = getUrl(mark_9, mark_10);
    FetchUrl FetchUrl_8= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_8.execute(url_8);

    String url_9 = getUrl(mark_10,mark_11 );
    FetchUrl FetchUrl_9= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_9.execute(url_9);

    String url_10 = getUrl(mark_11,mark_12 );
    FetchUrl FetchUrl_10= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_10.execute(url_10);



    String url_30 = getUrl(mark_12,mark_13 );
    FetchUrl FetchUrl_30= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_30.execute(url_30);

    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.440649, -122.235912)));
    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));




}
public  void showhounderdmile(){


    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.413153, -122.143819)).title("Finish"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.412181, -122.144594)).title("Start"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.424973, -122.313846)).title("Reset Stop"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.369439, -122.383567)).title("Reset Stop"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.503070, -122.335789)).title("Reset Stop"));
    mMap.addMarker(new MarkerOptions().position(new LatLng( 37.255954, -122.378421)).title("Reset Stop"));
    lineOptions = new PolylineOptions();
    lineOptions.color(Color.rgb(255,51,255));
    mMap.addPolyline(lineOptions);


    LatLng startmark=new LatLng( 37.412181, -122.144594);
    LatLng secondmark=new LatLng(37.409484, -122.160053);
    DrawArrowHead(mMap, secondmark,startmark );
    LatLng thirdmark=new LatLng(37.448335, -122.230864);
    LatLng four_mark=new LatLng(37.448256, -122.231913);
    DrawArrowHead(mMap, four_mark,thirdmark );
    LatLng fif_mark=new LatLng(37.435498, -122.259658);
    LatLng six_mark=new LatLng(37.503070, -122.335789);
    DrawArrowHead(mMap, six_mark,fif_mark );
    LatLng seven_mark=new LatLng(37.474200, -122.307152);
    LatLng seven_mark_temp=new LatLng(37.454061, -122.283613);
    LatLng mark_8=new LatLng(37.428544, -122.257860);//
    DrawArrowHead(mMap, mark_8,seven_mark );
    LatLng mark_14=new LatLng(37.424973, -122.313846);
    LatLng mark_15=new LatLng(37.369439, -122.383567);
    LatLng mark_16=new LatLng(37.166761, -122.360582);
    DrawArrowHead(mMap, mark_16,mark_15 );
    LatLng mark_20=new LatLng(37.212376, -122.349397);
    LatLng mark_21=new LatLng(37.254976, -122.369248);
    DrawArrowHead(mMap, mark_21,mark_20 );
    LatLng mark_22=new LatLng(37.255954, -122.378421);
    LatLng mark_23=new LatLng(37.260961, -122.384495);
    DrawArrowHead(mMap, mark_23,mark_22 );
    LatLng mark_24=new LatLng(37.289810, -122.380217);
    LatLng mark_25=new LatLng(37.303478, -122.384158);
    DrawArrowHead(mMap, mark_25,mark_24 );
    LatLng mark_26=new LatLng(37.319642, -122.388873);
    LatLng mark_17=new LatLng(37.345418, -122.271906);
    DrawArrowHead(mMap, mark_17,mark_26 );
    LatLng mark_18=new LatLng(37.387644, -122.266154);
    LatLng mark_19=new LatLng(37.424958, -122.313784);
    LatLng mark_30=new LatLng(37.427681, -122.303919);
    DrawArrowHead(mMap, mark_19,mark_18 );
    LatLng mark_9=new LatLng(37.404318, -122.249009);//
    LatLng mark_10=new LatLng(37.372308, -122.208962);
    DrawArrowHead(mMap, mark_10,mark_9 );
    LatLng mark_11=new LatLng(37.424475, -122.191655);
    LatLng mark_12=new LatLng(37.417156, -122.146884);
    LatLng mark_13=new LatLng(37.413153, -122.143819);//update start

// draw ruote by each position
    String url = getUrl(startmark, secondmark);
    FetchUrl FetchUrl = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl.execute(url);
//
//
//
    String url_1 = getUrl(secondmark, thirdmark);
    Log.d("onMapClick", url.toString());
    FetchUrl FetchUrl_1 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_1.execute(url_1);

    String url_2 = getUrl(thirdmark,four_mark );
    Log.d("onMapClick", url.toString());
    FetchUrl FetchUrl_2 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_2.execute(url_2);


    String url_3 = getUrl(four_mark,fif_mark );
    FetchUrl FetchUrl_3 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_3.execute(url_3);

    String url_4 = getUrl(fif_mark,six_mark );
    FetchUrl FetchUrl_4 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_4.execute(url_4);

    String url_5 = getUrl(six_mark,seven_mark );
    FetchUrl FetchUrl_5 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_5.execute(url_5);



    String url_6 = getUrl(seven_mark, seven_mark_temp);
    FetchUrl FetchUrl_6 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_6.execute(url_6);


    String url_26 = getUrl(seven_mark_temp, mark_8);
    FetchUrl FetchUrl_26 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_26.execute(url_26);


    String url_11 = getUrl(mark_8,mark_14 );
    FetchUrl FetchUrl_11= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_11.execute(url_11);

    String url_12 = getUrl(mark_14,mark_15 );
    FetchUrl FetchUrl_12= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_12.execute(url_12);

    String url_13 = getUrl(mark_15,mark_16 );
    FetchUrl FetchUrl_13= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_13.execute(url_13);

    String url_18 = getUrl(mark_16,mark_20 );
    FetchUrl FetchUrl_18= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_18.execute(url_18);
    String url_19 = getUrl(mark_20,mark_21 );
    FetchUrl FetchUrl_19= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_19.execute(url_19);

    String url_20 = getUrl(mark_21,mark_22 );
    FetchUrl FetchUrl_20= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_20.execute(url_20);


    String url_21 = getUrl(mark_22,mark_23 );
    FetchUrl FetchUrl_21= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_21.execute(url_21);


    String url_22 = getUrl(mark_23,mark_24 );
    FetchUrl FetchUrl_22= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_22.execute(url_22);


    String url_23 = getUrl(mark_24,mark_25 );
    FetchUrl FetchUrl_23= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_23.execute(url_23);

    String url_24 = getUrl(mark_25,mark_26 );
    FetchUrl FetchUrl_24= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_24.execute(url_24);

  String url_14= getUrl(mark_26,mark_17 );
    FetchUrl FetchUrl_14= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_14.execute(url_14);




    String url_15= getUrl(mark_17,mark_18 );
    FetchUrl FetchUrl_15= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_15.execute(url_15);

    String url_16= getUrl(mark_18,mark_19 );
    FetchUrl FetchUrl_16= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_16.execute(url_16);


    String url_30= getUrl(mark_19,mark_30 );
    FetchUrl FetchUrl_30= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_30.execute(url_30);

    String url_31= getUrl(mark_30,mark_8 );
    FetchUrl FetchUrl_31= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_31.execute(url_31);

    String url_7 = getUrl(mark_8, mark_9);
    FetchUrl FetchUrl_7 = new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_7.execute(url_7);

    String url_8 = getUrl(mark_9, mark_10);
    FetchUrl FetchUrl_8= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_8.execute(url_8);

    String url_9 = getUrl(mark_10,mark_11 );
    FetchUrl FetchUrl_9= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_9.execute(url_9);

    String url_10 = getUrl(mark_11,mark_12 );
    FetchUrl FetchUrl_10= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_10.execute(url_10);



    String url_17 = getUrl(mark_12,mark_13 );
    FetchUrl FetchUrl_17= new FetchUrl();

    // Start downloading json data from Google Directions API
    FetchUrl_17.execute(url_17);

    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.440649, -122.235912)));
    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));


}
  public void  showentire(){


      mMap.addMarker(new MarkerOptions().position(new LatLng( 37.413153, -122.143819)).title("Finish"));
      mMap.addMarker(new MarkerOptions().position(new LatLng( 37.412181, -122.144594)).title("Start"));
      mMap.addMarker(new MarkerOptions().position(new LatLng( 37.424973, -122.313846)).title("Reset Stop"));
      mMap.addMarker(new MarkerOptions().position(new LatLng( 37.369439, -122.383567)).title("Reset Stop"));
      mMap.addMarker(new MarkerOptions().position(new LatLng( 37.503070, -122.335789)).title("Reset Stop"));
      mMap.addMarker(new MarkerOptions().position(new LatLng( 37.255954, -122.378421)).title("Reset Stop"));
      lineOptions = new PolylineOptions();
      lineOptions.color(Color.rgb(255,51,255));
      mMap.addPolyline(lineOptions);


      LatLng startmark=new LatLng( 37.412181, -122.144594);
      LatLng secondmark=new LatLng(37.409484, -122.160053);

      LatLng thirdmark=new LatLng(37.448335, -122.230864);
      LatLng four_mark=new LatLng(37.448256, -122.231913);

      LatLng fif_mark=new LatLng(37.435498, -122.259658);
      LatLng six_mark=new LatLng(37.503070, -122.335789);

      LatLng seven_mark=new LatLng(37.474200, -122.307152);
      LatLng seven_mark_temp=new LatLng(37.454061, -122.283613);
      LatLng mark_8=new LatLng(37.428544, -122.257860);//

      LatLng mark_14=new LatLng(37.424973, -122.313846);
      LatLng mark_15=new LatLng(37.369439, -122.383567);
      LatLng mark_16=new LatLng(37.166761, -122.360582);

      LatLng mark_20=new LatLng(37.212376, -122.349397);
      LatLng mark_21=new LatLng(37.254976, -122.369248);

      LatLng mark_22=new LatLng(37.255954, -122.378421);
      LatLng mark_23=new LatLng(37.260961, -122.384495);

      LatLng mark_24=new LatLng(37.289810, -122.380217);
      LatLng mark_25=new LatLng(37.303478, -122.384158);

      LatLng mark_26=new LatLng(37.319642, -122.388873);
      LatLng mark_17=new LatLng(37.345418, -122.271906);

      LatLng mark_18=new LatLng(37.387644, -122.266154);
      LatLng mark_19=new LatLng(37.424958, -122.313784);
      LatLng mark_30=new LatLng(37.427681, -122.303919);

      LatLng mark_9=new LatLng(37.404318, -122.249009);//
      LatLng mark_10=new LatLng(37.372308, -122.208962);

      LatLng mark_11=new LatLng(37.424475, -122.191655);
      LatLng mark_12=new LatLng(37.417156, -122.146884);
      LatLng mark_13=new LatLng(37.413153, -122.143819);//update start

// draw ruote by each position
      String url = getUrl(startmark, secondmark);
      FetchUrl FetchUrl = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl.execute(url);
//
//
//
      String url_1 = getUrl(secondmark, thirdmark);
      Log.d("onMapClick", url.toString());
      FetchUrl FetchUrl_1 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_1.execute(url_1);

      String url_2 = getUrl(thirdmark,four_mark );
      Log.d("onMapClick", url.toString());
      FetchUrl FetchUrl_2 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_2.execute(url_2);


      String url_3 = getUrl(four_mark,fif_mark );
      FetchUrl FetchUrl_3 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_3.execute(url_3);

      String url_4 = getUrl(fif_mark,six_mark );
      FetchUrl FetchUrl_4 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_4.execute(url_4);

      String url_5 = getUrl(six_mark,seven_mark );
      FetchUrl FetchUrl_5 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_5.execute(url_5);



      String url_6 = getUrl(seven_mark, seven_mark_temp);
      FetchUrl FetchUrl_6 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_6.execute(url_6);


      String url_26 = getUrl(seven_mark_temp, mark_8);
      FetchUrl FetchUrl_26 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_26.execute(url_26);


      String url_11 = getUrl(mark_8,mark_14 );
      FetchUrl FetchUrl_11= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_11.execute(url_11);

      String url_12 = getUrl(mark_14,mark_15 );
      FetchUrl FetchUrl_12= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_12.execute(url_12);

      String url_13 = getUrl(mark_15,mark_16 );
      FetchUrl FetchUrl_13= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_13.execute(url_13);

      String url_18 = getUrl(mark_16,mark_20 );
      FetchUrl FetchUrl_18= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_18.execute(url_18);
      String url_19 = getUrl(mark_20,mark_21 );
      FetchUrl FetchUrl_19= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_19.execute(url_19);

      String url_20 = getUrl(mark_21,mark_22 );
      FetchUrl FetchUrl_20= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_20.execute(url_20);


      String url_21 = getUrl(mark_22,mark_23 );
      FetchUrl FetchUrl_21= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_21.execute(url_21);


      String url_22 = getUrl(mark_23,mark_24 );
      FetchUrl FetchUrl_22= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_22.execute(url_22);


      String url_23 = getUrl(mark_24,mark_25 );
      FetchUrl FetchUrl_23= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_23.execute(url_23);

      String url_24 = getUrl(mark_25,mark_26 );
      FetchUrl FetchUrl_24= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_24.execute(url_24);

      String url_14= getUrl(mark_26,mark_17 );
      FetchUrl FetchUrl_14= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_14.execute(url_14);




      String url_15= getUrl(mark_17,mark_18 );
      FetchUrl FetchUrl_15= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_15.execute(url_15);

      String url_16= getUrl(mark_18,mark_19 );
      FetchUrl FetchUrl_16= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_16.execute(url_16);


      String url_30= getUrl(mark_19,mark_30 );
      FetchUrl FetchUrl_30= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_30.execute(url_30);

      String url_31= getUrl(mark_30,mark_8 );
      FetchUrl FetchUrl_31= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_31.execute(url_31);

      String url_7 = getUrl(mark_8, mark_9);
      FetchUrl FetchUrl_7 = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_7.execute(url_7);

      String url_8 = getUrl(mark_9, mark_10);
      FetchUrl FetchUrl_8= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_8.execute(url_8);

      String url_9 = getUrl(mark_10,mark_11 );
      FetchUrl FetchUrl_9= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_9.execute(url_9);

      String url_10 = getUrl(mark_11,mark_12 );
      FetchUrl FetchUrl_10= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_10.execute(url_10);



      String url_17 = getUrl(mark_12,mark_13 );
      FetchUrl FetchUrl_17= new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl_17.execute(url_17);

      mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.440649, -122.235912)));
      mMap.animateCamera(CameraUpdateFactory.zoomTo(10));


  }

    public  void showhfivesmall(){
mMap.clear();

        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.413153, -122.143819)).title("Finish"));
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.412181, -122.144594)).title("Start"));
        mMap.addMarker(new MarkerOptions().position(new LatLng( 37.399229, -122.134102)).title("Reset/Stop"));

        PolylineOptions options = new PolylineOptions();

        options.color( Color.rgb(153,51,255));
        options.width( 10 );
        options.visible( true );
        options.add( new LatLng( 37.412181, -122.144594 ) );
        options.add( new LatLng( 37.414113, -122.143025 ) );
        DrawArrowHead(mMap, new LatLng( 37.414113, -122.143025 ),new LatLng( 37.412181, -122.144594 ) );
        options.add( new LatLng( 37.413303, -122.141405 ) );
        options.add( new LatLng( 37.413319, -122.141259 ) );
        options.add( new LatLng( 37.413798, -122.140793 ) );
        options.add( new LatLng( 37.412633, -122.140282 ) );
        options.add( new LatLng( 37.412598, -122.140121 ) );
        options.add( new LatLng( 37.412536, -122.140120 ) );
        DrawArrowHead(mMap, new LatLng( 37.412536, -122.140120 ),new LatLng( 37.412598, -122.140121 ) );
        options.add( new LatLng( 37.411768, -122.139769 ) );
        options.add( new LatLng( 37.411546, -122.139689 ) );
        options.add( new LatLng( 37.411173, -122.139667 ) );
        options.add( new LatLng( 37.410498, -122.139423 ) );
        options.add( new LatLng( 37.409428, -122.138892 ) );
        options.add( new LatLng( 37.409070, -122.138657 ) );
        options.add( new LatLng( 37.408887, -122.138601 ) );
        options.add( new LatLng( 37.408528, -122.138531 ) );
        options.add( new LatLng( 37.407710, -122.138211 ) );
        options.add( new LatLng( 37.407181, -122.137930 ) );
        options.add( new LatLng( 37.406326, -122.137618 ) );
        options.add( new LatLng( 37.405910, -122.137439 ) );
        options.add( new LatLng( 37.405453, -122.137132 ) );
        options.add( new LatLng( 37.404816, -122.136972 ) );
        options.add( new LatLng( 37.404495, -122.136845 ) );
        options.add( new LatLng( 37.404362, -122.136760 ) );
        options.add( new LatLng( 37.404056, -122.136542 ) );
        options.add( new LatLng( 37.403863, -122.136463 ) );
        options.add( new LatLng( 37.403445, -122.136342 ) );
        DrawArrowHead(mMap, new LatLng( 37.403445, -122.136342 ),new LatLng( 37.403863, -122.136463 ) );
        options.add( new LatLng( 37.402778, -122.136028 ) );
        options.add( new LatLng( 37.399683, -122.134381 ) );
        options.add( new LatLng( 37.399554, -122.134269 ) );
        options.add( new LatLng( 37.399472, -122.134286 ) );
        options.add( new LatLng( 37.399228, -122.134091 ) );
        options.add( new LatLng( 37.399228, -122.134091 ) );
        options.add( new LatLng( 37.398586, -122.135653 ) );
        options.add( new LatLng( 37.397404, -122.137825 ) );
        options.add( new LatLng( 37.397212, -122.138291 ) );
        options.add( new LatLng( 37.396111, -122.140465 ) );
        options.add( new LatLng( 37.396022, -122.140820 ) );
        options.add( new LatLng( 37.395847, -122.141175 ) );
        options.add( new LatLng( 37.395504, -122.141672 ) );
        options.add( new LatLng( 37.394749, -122.143292 ) );
        options.add( new LatLng( 37.403618, -122.146098 ) );
        options.add( new LatLng( 37.407287, -122.145291 ) );
        options.add( new LatLng( 37.407476, -122.145287 ) );
        options.add( new LatLng( 37.407791, -122.145337 ) );
        DrawArrowHead(mMap, new LatLng( 37.407791, -122.145337 ),new LatLng( 37.407476, -122.145287 ) );
        options.add( new LatLng( 37.408120, -122.145568 ) );
        options.add( new LatLng( 37.408372, -122.145926 ) );
        options.add( new LatLng( 37.409023, -122.147216 ) );
        options.add( new LatLng( 37.410831, -122.145725 ) );
        options.add( new LatLng( 37.410984, -122.146007 ) );
        options.add( new LatLng( 37.411724, -122.145395 ) );





        mMap.addPolyline( options );




        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.413319, -122.141259)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));


    }

//
private static final double degreesPerRadian = 180.0 / Math.PI;

    public  void DrawArrowHead(GoogleMap mMap, LatLng from, LatLng to){
        // obtain the bearing between the last two points
        double bearing =GetBearing(from, to);

        // round it to a multiple of 3 and cast out 120s
        double adjBearing = Math.round(bearing / 3) * 3;
        while (adjBearing >= 120) {
            adjBearing -= 120;
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get the corresponding triangle marker from Google
        /*
        URL url;
        Bitmap image = null;

        try {
            url = new URL("http://www.google.com/intl/en_ALL/mapfiles/dir_" + String.valueOf((int)adjBearing) + ".png");
            try {
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
        //if (image != null)
        {

            // Anchor is ratio in range [0..1] so value of 0.5 on x and y will center the marker image on the lat/long
            float anchorX = 0.5f;
            float anchorY = 0.5f;

            int offsetX = 0;
            int offsetY = 0;

            // images are 24px x 24px
            // so transformed image will be 48px x 48px

            //315 range -- 22.5 either side of 315
            if (bearing >= 292.5 && bearing < 335.5){
                offsetX = 24;
                offsetY = 24;
            }
            //270 range
            else if (bearing >= 247.5 && bearing < 292.5){
                offsetX = 24;
                offsetY = 12;
            }
            //225 range
            else if (bearing >= 202.5 && bearing < 247.5){
                offsetX = 24;
                offsetY = 0;
            }
            //180 range
            else if (bearing >= 157.5 && bearing < 202.5){
                offsetX = 12;
                offsetY = 0;
            }
            //135 range
            else if (bearing >= 112.5 && bearing < 157.5){
                offsetX = 0;
                offsetY = 0;
            }
            //90 range
            else if (bearing >= 67.5 && bearing < 112.5){
                offsetX = 0;
                offsetY = 12;
            }
            //45 range
            else if (bearing >= 22.5 && bearing < 67.5){
                offsetX = 0;
                offsetY = 24;
            }
            //0 range - 335.5 - 22.5
            else {
                offsetX = 12;
                offsetY = 24;
            }

            Bitmap wideBmp;
            Canvas wideBmpCanvas;
      //      Bitmap bmp = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.bag);
            Bitmap bmp  = getBitmapFromAsset(MapsActivity.this, "arrow.png");
            Matrix mat = new Matrix();
            mat.postRotate((float) bearing + 180);
            mat.postTranslate(offsetX, offsetY);
            Bitmap bmpRotate = Bitmap.createBitmap(bmp, 0, 0,
                    bmp.getWidth(), bmp.getHeight(),
                    mat, true);

            mMap.addMarker(new MarkerOptions()
                    .position(to)
                    .icon(BitmapDescriptorFactory.fromBitmap(bmpRotate))
                    .anchor(anchorX, anchorY));
        }
    }
    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }
    private static double GetBearing(LatLng from, LatLng to){
        double lat1 = from.latitude * Math.PI / 180.0;
        double lon1 = from.longitude * Math.PI / 180.0;
        double lat2 = to.latitude * Math.PI / 180.0;
        double lon2 = to.longitude * Math.PI / 180.0;

        // Compute the angle.
        double angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );

        if (angle < 0.0)
            angle += Math.PI * 2.0;

        // And convert result to degrees.
        angle = angle * degreesPerRadian;

        return angle;
    }

//


    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
//            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
//                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
//                lineOptions.color(Color.rgb(237, 95, 247));
//                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
////        stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}
