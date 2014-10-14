package com.ubicom.ruta;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;





public class MainActivity extends Activity 
                          implements GooglePlayServicesClient.ConnectionCallbacks,
                                    GooglePlayServicesClient.OnConnectionFailedListener,
                                    LocationListener
{
    private GoogleMap mMap; 
    private LocationClient mLocationClient;
    private LocationRequest mLocationRequest;
    Location mCurrentLocation;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(!servicesAvaliable())
            finish();

        setContentView(R.layout.main);
        //get  Device Location
        mLocationClient = new LocationClient(this,this,this);
        // Create and define the LocationRequest
        mLocationRequest = LocationRequest.create();

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        //Create ui objects
        mMap =   ((MapFragment)  getFragmentManager().findFragmentById(R.id.map)).getMap();
                
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		mMap.setMyLocationEnabled(true);
        
        mMap.setOnMarkerClickListener(new OnMarkerClickListener(){
            
            @Override
            public boolean onMarkerClick(Marker marker){
             mMap.clear();
             return true;  
            }
        });
    }

   


    @Override
    public void onStart(){
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    public void onStop(){
        mLocationClient.disconnect();    
        super.onStop();
    }

    @Override 
    public void onConnected(Bundle dataBundle){
        if(servicesAvaliable()){

            mCurrentLocation = mLocationClient.getLastLocation();

            if(mCurrentLocation != null){

            mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude())).title("Hello"));
            Toast.makeText(this,"Connected ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onDisconnected(){
        
        Toast.makeText(this,"Disconnected ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
   
       
        Toast.makeText(this,"Connection Failed.  ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public boolean servicesAvaliable(){
        Log.i("ServicesAvaliable" ,"services availabe");
            int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);

            return (ConnectionResult.SUCCESS == resultCode);
                
        }

 }  



