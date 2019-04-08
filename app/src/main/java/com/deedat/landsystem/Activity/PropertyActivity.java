package com.deedat.landsystem.Activity;

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
import android.widget.RelativeLayout;

import com.deedat.landsystem.Adapter.LandInfoAdapter;
import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.HomeFragment;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PropertyActivity extends AppCompatActivity {
Toolbar toolbar;
FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<LandInfo> landInfoList;
    private land_dets_adapter mAdapter;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
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
        toolbar.setTitle("My Properties");
        toolbar.setElevation(10f);
        toolbar.setTitleTextColor(Color.WHITE);
        recyclerView = findViewById(R.id.recycler_view);
        //coordinatorLayout = findViewById(R.id.coordinator_layout);
        landInfoList = new ArrayList<>();
        mAdapter = new land_dets_adapter(this, landInfoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //progressDialog.setTitle("LogIn");

       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
       // recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(6), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

//        landInfoList.add(new LandInfo("GH-242324334","1200 by 800 sq.ft","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
//        landInfoList.add(new LandInfo("GH-977B8284","1320 by 300 sq.ft","GR-Achimota","Deedat Idriss Billa","https://horizon-media.s3-eu-west-1.amazonaws.com/s3fs-public/styles/large/public/field/image/Kenyan%20landscape%20cropped%20-%20shutterstock_216892456%20-%20Maciej%20Czekajewski.jpg?itok=7LOkfAm1"));
//        landInfoList.add(new LandInfo("GH-98676763","1100 by 800 sq.ft","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));
//        landInfoList.add(new LandInfo("GH-242324334","1200 by 800 sq.ft","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
//        landInfoList.add(new LandInfo("GH-977B8284","1320 by 300 sq.ft","GR-Achimota","Deedat Idriss Billa","https://horizon-media.s3-eu-west-1.amazonaws.com/s3fs-public/styles/large/public/field/image/Kenyan%20landscape%20cropped%20-%20shutterstock_216892456%20-%20Maciej%20Czekajewski.jpg?itok=7LOkfAm1"));
//        landInfoList.add(new LandInfo("GH-98676763","1100 by 800 sq.ft","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));
        if(mAdapter.getItemCount()!=0){
            relativeLayout.setVisibility(View.GONE);
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
