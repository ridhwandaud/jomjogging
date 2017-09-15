package com.ridhwandaud.jomjogging;

import java.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ridhwandaud.jomjogging.models.Run;

import java.util.Date;

public class RunViewHolder extends RecyclerView.ViewHolder{

    public TextView dateView;
    public TextView distanceView;
    public TextView timeView;
    public TextView bodyView;

    public RunViewHolder(View itemView) {
        super(itemView);

        dateView = (TextView) itemView.findViewById(R.id.date_text);
        distanceView = (TextView) itemView.findViewById(R.id.distance_text);
        timeView = (TextView) itemView.findViewById(R.id.total_time_text);
    }

    public void bindToRun(Run run, View.OnClickListener starClickListener) {

        // format data

        int secs = (int) (run.time / 1000);
        int mins = secs / 60;
        int hours = mins / 60;
        secs = secs % 60;

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy @ hh:mm");
        Date resultdate = new Date(run.date);
        dateView.setText(String.valueOf(sdf.format(resultdate)));
        distanceView.setText(String.valueOf(run.distance));

        if (hours > 0) {
            timeView.setText(String.format("%02d", hours) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
        } else {
            timeView.setText(String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
        }
    }
}
