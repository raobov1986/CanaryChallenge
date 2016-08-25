package com.gari.canarychallenge.contentefragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gari.canarychallenge.R;
import com.gari.canarychallenge.Utilities.DataParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FiftenkioFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    PolylineOptions lineOptions;
    LocationRequest mLocationRequest;

    public FiftenkioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fiftenkio, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        MapFragment mapFragment=getChildFragmentManager().findFragmentById(R.id.map);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
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
        showfivekiro();

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
            Bitmap bmp  = getBitmapFromAsset(getContext().getApplicationContext(), "arrow.png");
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
}
