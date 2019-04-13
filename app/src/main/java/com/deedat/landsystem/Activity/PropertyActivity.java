package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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

import java.util.ArrayList;
import java.util.List;

public class PropertyActivity extends AppCompatActivity {
Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<LandInfo> landInfoList;
    private land_dets_adapter mAdapter;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    ArrayList<my_land_data> my_land_dataArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        firebaseAuth=FirebaseAuth.getInstance();
        final String uid=firebaseAuth.getUid();
        progressBar = findViewById(R.id.progress_bar);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("myland_linked_to_account");
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
        relativeLayout=findViewById(R.id.empty);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("My Properties");
        toolbar.setElevation(10f);
        recyclerView = findViewById(R.id.recycler_view);
        //coordinatorLayout = findViewById(R.id.coordinator_layout);
        landInfoList = new ArrayList<>();

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(mAdapter);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                my_land_dataArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    my_land_data mld = snapshot.getValue(my_land_data.class);

                    my_land_dataArrayList.add(mld);

                }
                mAdapter = new land_dets_adapter(PropertyActivity.this, landInfoList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PropertyActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

//                mAdapter.setOnItemClickListener(new ArticleListAdapter.onItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        Intent intent=new Intent(getActivity(),Article_content.class);
//                        intent.putExtra("article_data", articleList.get(position));
//                        startActivity(intent);
//                    }
//                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(mAdapter.getItemCount()!=0){
            relativeLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

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
