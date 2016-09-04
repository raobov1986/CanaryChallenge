package com.gari.canarychallenge.contentefragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.gari.canarychallenge.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {



    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    final public static int SEND_SMS = 101;




    public SupportFragment() {
        // Required empty public constructor
    }
    EditText name_edittext;
    EditText phonename_edittext;
    EditText description_edittext;
    Button submit_button;
    String location_data;
    String current_data;
    View contenteview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contenteview=inflater.inflate(R.layout.fragment_support, container, false);
        Bundle bundle = this.getArguments();
        String locationreceivedata = bundle.getString("locationdata");
        location_data="Latitude:Longitude "+locationreceivedata;
        Log.d("hello",locationreceivedata);
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        int hours=c.get(Calendar.HOUR);
        Log.d("hi_ihave",locationreceivedata);
        Log.d("Currenttime",Integer.toString(hours)+":"+Integer.toString(seconds));
        current_data="H:S  "+Integer.toString(hours)+":"+Integer.toString(seconds);
        name_edittext=(EditText)contenteview.findViewById(R.id.name_editText);
        phonename_edittext=(EditText)contenteview.findViewById(R.id.phonenumber_editText);
        description_edittext=(EditText)contenteview.findViewById(R.id.description_editText);
        submit_button=(Button)contenteview.findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=name_edittext.getText().toString();
                String phone_number=phonename_edittext.getText().toString();
                String description=description_edittext.getText().toString();
                String message="Name:"+name+"\n"+"Rider PhoneNumber:"+phone_number+"\n"+"Contact Details:"+description+"\n"+"Location "+location_data;
                String send_phonenumber="6503880495";
//                sendSms(send_phonenumber,message);
                showalert(send_phonenumber,message);

            }
        });
        checkAndroidVersion();

        return contenteview;
    }

    public void checkAndroidVersion(){
//        this.mobile= mobile;
//        this.message=message;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.SEND_SMS);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},SEND_SMS);
                return;
            }else{
//                sendSms(mobile,message);
            }
        } else {
//            sendSms(mobile,message);
        }
    }

public  void showalert(final String phonenumber, final String message){



    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setMessage("Are you sure,You wanted to submit request help?");

    alertDialogBuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {

            sendSms(phonenumber,message);
        }
    });

    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            dialog.cancel();

        }
    });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();



}



    public void sendSms(String edt_phoneNo,String message)
    {
        Log.d("Phone Number",edt_phoneNo);
        Log.d("Message",message);
        //Uri uri = Uri.parse("smsto:" + edt_phoneNo);
        //Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);

//        int randomPIN = (int)(Math.random()*9000) + 1000;
        String PINString = message;

        //smsSIntent.putExtra("sms_body", PINString);

        try {

            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(edt_phoneNo, null, PINString, null, null);

            resultalert();

        } catch (Exception ex) {
//            Toast.makeText(SmsActivity.this, "Your sms has failed...",
//                    Toast.LENGTH_LONG).show();
            View view=new View(getContext());
            Snackbar.make(contenteview, "Your sms has failed..", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


            ex.printStackTrace();

        }


    }




    public  void resultalert(){



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Your request have sent successfully!");



        alertDialogBuilder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



    }


}
