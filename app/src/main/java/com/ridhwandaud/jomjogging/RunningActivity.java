package com.ridhwandaud.jomjogging;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class RunningActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;

    private TextView timerValue;
    private Handler customHandler;

    private GoogleMap mMap;
    LocationManager locationManager;

    Polyline line;
    private ArrayList<LatLng> points = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        timerValue = (TextView) findViewById(R.id.time_text);
        customHandler = new Handler();
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        initilizeMap();

        Button startRunButton = (Button) findViewById(R.id.end_button);

        startRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endRun(v);
            }
        });
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins / 60;
            secs = secs % 60;
            if (hours > 0) {
                timerValue.setText(String.format("%02d", hours) + ":"
                        + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
            } else {
                timerValue.setText(String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
            }
            customHandler.postDelayed(this, 1000);
        }
    };

    private void initilizeMap()
    {

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment =  SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.mapContainer, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);
        System.out.println("initilizeMap");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(17.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setMyLocationEnabled(true);

        LatLng bota = new LatLng(4.3553558,100.8603148);
        mMap.addMarker(new MarkerOptions().position(bota).title("Marker in Bota"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bota));

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider, 10000, 10, this);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            currentLocation(location);
        } else {
            System.out.println("Location not found");
        }
    }

    public void currentLocation(Location location){
        LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
        points.add(myCoordinates);
        redrawLine();
    }

    private void redrawLine(){
        PolylineOptions options = new PolylineOptions().width(10).color(Color.GRAY).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        line = mMap.addPolyline(options); //add Polyline
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void endRun(View view){
        String message, title, btnText;
//        customHandler.removeCallbacks(updateTimerThread);
//        locationManager.removeUpdates(this);

        title = "Do you really want to end your run?";
        btnText = "END RUN";
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        customHandler.removeCallbacks(updateTimerThread);
                        // TODO save data and go to activity Activity
                    }
                })
                .setNegativeButton("RESUME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case android.R.id.home:
                onUpEndButtonPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onUpEndButtonPressed(){
        String message, title, btnText;
        title = "Athelete";
        message = "Giving up is not an option";
        btnText = "CANCEL RUN";
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        customHandler.removeCallbacks(updateTimerThread);
                        // TODO save data and go to activity Activity
                        Intent backIntent = new Intent(RunningActivity.this,MainActivity.class);
                        startActivity(backIntent);
                    }
                })
                .setNegativeButton("RESUME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
}
