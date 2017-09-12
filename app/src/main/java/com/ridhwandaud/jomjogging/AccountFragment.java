package com.ridhwandaud.jomjogging;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ridhwandaud.jomjogging.models.Run;
import com.ridhwandaud.jomjogging.RunViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";
    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Run, RunViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.account_fragment, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = (RecyclerView) rootView.findViewById(R.id.runnings_list);
        mRecycler.setHasFixedSize(true);

        System.out.println("OnCreateView");
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query runsQuery = getQuery(mDatabase);
        System.out.println("getQuery" + runsQuery);
        mAdapter = new FirebaseRecyclerAdapter<Run, RunViewHolder>(Run.class, R.layout.item_run,
                RunViewHolder.class, runsQuery) {
            @Override
            protected void populateViewHolder(RunViewHolder viewHolder, Run model, int position) {

                final DatabaseReference runRef = getRef(position);

                // Set click listener for the whole post view
                final String runKey = runRef.getKey();

                viewHolder.bindToRun(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

        System.out.println("onActivityCreated");
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-runs")
                .child(getUid());
    }
}
