package com.deedat.landsystem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Property;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.deedat.landsystem.Activity.PropertyActivity;
import com.deedat.landsystem.Adapter.FilterAdapter;
import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.Model.filter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends androidx.fragment.app.Fragment {

    private RecyclerView recyclerView;
    BottomNavigationView navigation;
    private List<LandInfo> landInfoList;
    private List<filter> filter_tags;
    private land_dets_adapter mAdapter;
    private FilterAdapter mAdapter2;
    ProgressBar progressBar;
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
        recyclerView = view.findViewById(R.id.recycler_view_vertical);
        progressBar=view.findViewById(R.id.progress_bar);
       // progressDialog = new ProgressDialog(getActivity());
        //navigation=view.findViewById(R.id.navigationtop);
        //navigation.setVisibility(View.VISIBLE);
       // recyclerView = view.findViewById(R.id.recycler_view_tag);
        //  coordinatorLayout = findViewById(R.id.coordinator_layout);
      fetchlands();

       // tags();

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

    public void fetchlands(){

        landInfoList = new ArrayList<>();
        mAdapter = new land_dets_adapter(getActivity(), landInfoList);

      //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
      //  recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //progressDialog.setTitle("LogIn");


        mAdapter.notifyDataSetChanged();
        //progressDialog.dismiss();
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.setOnItemClickListener(new land_dets_adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(), PropertyActivity.class);
                intent.putExtra("land_data", landInfoList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        landInfoList.add(new LandInfo("GH-242324334","190 X 90","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
        landInfoList.add(new LandInfo("GH-977B8284","90 X 90","GR-Achimota","Deedat Idriss Billa","https://horizon-media.s3-eu-west-1.amazonaws.com/s3fs-public/styles/large/public/field/image/Kenyan%20landscape%20cropped%20-%20shutterstock_216892456%20-%20Maciej%20Czekajewski.jpg?itok=7LOkfAm1"));
        landInfoList.add(new LandInfo("GH-98676763","90 X 90","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));


    }
public void tags(){
    filter_tags = new ArrayList<>();
    mAdapter2 = new FilterAdapter(getActivity(), filter_tags);
     RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
      recyclerView.setLayoutManager(mLayoutManager);
      //recyclerView.setItemAnimator(new DefaultItemAnimator());
      //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
      recyclerView.setAdapter(mAdapter2);
      filter_tags.add(new filter("verified"));
      filter_tags.add(new filter("For sale"));
      filter_tags.add(new filter("Arround me"));
      filter_tags.add(new filter("verified"));
      filter_tags.add(new filter("verified"));


}
}
