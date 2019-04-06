package com.deedat.landsystem;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deedat.landsystem.Activity.LandInfoActivity;
import com.deedat.landsystem.Activity.PropertyActivity;
import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends androidx.fragment.app.Fragment {

    //SwipeRefreshLayout swipeContainer;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);















//
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("food");
//        Query query=ref.orderByChild("category").equalTo("heavy food");
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot datas: dataSnapshot.getChildren()){
//                    String name=datas.child("name").getValue().toString();
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });








       // progressDialog = new ProgressDialog(getActivity());
        //navigation=view.findViewById(R.id.navigationtop);
        //navigation.setVisibility(View.VISIBLE);
       // recyclerView = view.findViewById(R.id.recycler_view_tag);
        //  coordinatorLayout = findViewById(R.id.coordinator_layout);


       // tags();

    }








}
