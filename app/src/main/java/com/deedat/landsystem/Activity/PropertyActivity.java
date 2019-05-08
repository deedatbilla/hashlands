package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deedat.landsystem.Adapter.LandInfoAdapter;
import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.HomeFragment;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;
import com.deedat.landsystem.Model.my_land_data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PropertyActivity extends AppCompatActivity {
Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private land_dets_adapter mAdapter;
    RelativeLayout relativeLayout;
    TextView message;
    ProgressBar progressBar;
    ArrayList<LandInfo> my_land_dataArrayList=new ArrayList<>();
    private ProgressDialog progressDialog;
     String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        firebaseAuth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid();
        progressBar = findViewById(R.id.progress_bar);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("myland_linked_to_account");
        recyclerView = findViewById(R.id.recycler_view);

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PropertyActivity.this,AddLandActivity.class);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        //floatingActionButton=findViewById(R.id.fab);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        progressDialog = new ProgressDialog(this);

        relativeLayout=findViewById(R.id.empty);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("My Properties");
        toolbar.setElevation(10f);

        final RequestQueue queue = Volley.newRequestQueue(this);

        final String url_remove="https://hashland.herokuapp.com/removeFromSale";
        queue.start();

        //coordinatorLayout = findViewById(R.id.coordinator_layout);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                my_land_dataArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LandInfo mld = snapshot.getValue(LandInfo.class);

                    my_land_dataArrayList.add(mld);

                }
                mAdapter = new land_dets_adapter(PropertyActivity.this, my_land_dataArrayList,true);
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PropertyActivity.this);
//                recyclerView.setLayoutManager(mLayoutManager);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(PropertyActivity.this, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new PropertyActivity.GridSpacingItemDecoration(2, dpToPx(8), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
               // mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
              if(my_land_dataArrayList.isEmpty()){
                  relativeLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
               }
                mAdapter.setOnItemClickListener(new land_dets_adapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(PropertyActivity.this);
                        Intent intent=new Intent(PropertyActivity.this, mylandsActivity.class);
                        intent.putExtra("land_data", my_land_dataArrayList.get(position));
                        startActivity(intent,options.toBundle());
//
                        //queue.stop();
                    }
                });






            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }



        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}
