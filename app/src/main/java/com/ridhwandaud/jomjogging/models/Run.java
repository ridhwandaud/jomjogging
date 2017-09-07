package com.ridhwandaud.jomjogging.models;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Run {
    public String uid;
    public double distance;
    public long time;
    public Date date;
    private ArrayList<LatLng> points = new ArrayList<LatLng>();

    public Run() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Run(String uid, double distance, long time, Date date) {
        this.uid = uid;
        this.distance = distance;
        this.time = time;
        this.date = date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("distance", distance);
        result.put("time", time);
        result.put("date",date);
        return result;
    }
}
