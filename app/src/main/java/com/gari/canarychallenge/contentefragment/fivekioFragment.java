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
public class fivekioFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    PolylineOptions lineOptions;
    LocationRequest mLocationRequest;
    public fivekioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fivekio, container, false);
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
        showhfivesmall();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
