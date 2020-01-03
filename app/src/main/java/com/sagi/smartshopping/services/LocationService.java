package com.sagi.smartshopping.services;

import android.Manifest;
import android.app.AliasActivity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service {

    private FusedLocationProviderClient mFusedLocationProviderClient;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        LocationRequest locationRequest = new LocationRequest()
                .setFastestInterval(1000)
                .setInterval(1000)
                .setSmallestDisplacement(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, mCallbackLocation,null);
    }

    LocationCallback mCallbackLocation =new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            if (locationResult==null)
                return;

            for (Location location:locationResult.getLocations()){
                getCityFromLocation(location);
                Log.d("LocationService",location.getLatitude()+", "+location.getLongitude());
            }

        }
    };

    private void getCityFromLocation(Location location) {
        // TODO change to english
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses!=null && addresses.size() > 0) {
            String myCity=addresses.get(0).getLocality();
            SharedPreferencesHelper.getInstance().setLastCityIThere(myCity);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
