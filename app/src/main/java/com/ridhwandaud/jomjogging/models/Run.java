package com.ridhwandaud.jomjogging.models;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Run {
    public String uid;
    public float distance;
    public float time;
    private ArrayList<LatLng> points = new ArrayList<LatLng>();

    public Run() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Run(String uid, float distance, float time) {
        this.uid = uid;
        this.distance = distance;
        this.time = time;
    }
}
