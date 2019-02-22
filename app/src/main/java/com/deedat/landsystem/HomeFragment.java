package com.deedat.landsystem;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deedat.landsystem.Adapter.FilterAdapter;
import com.deedat.landsystem.Adapter.LandInfoAdapter;
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
    private LandInfoAdapter mAdapter;
    private FilterAdapter mAdapter2;
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
        //recyclerView = view.findViewById(R.id.recycler_view_horizontal);
        //navigation=view.findViewById(R.id.navigationtop);
        //navigation.setVisibility(View.VISIBLE);
       // recyclerView = view.findViewById(R.id.recycler_view_tag);
        //  coordinatorLayout = findViewById(R.id.coordinator_layout);
     // fetchlands();

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
        mAdapter = new LandInfoAdapter(getActivity(), landInfoList);

      //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
      //  recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        landInfoList.add(new LandInfo("24234","190 X 90","kasoa","deedat"));
        landInfoList.add(new LandInfo("24234","90 X 90","kasoa","deedat"));
        landInfoList.add(new LandInfo("23542","90 X 90","kasoa","mike"));
        landInfoList.add(new LandInfo("23524","190 X 90","kasoa","Mae"));
        landInfoList.add(new LandInfo("9874","90 X 90","kasoa","Josh"));
        landInfoList.add(new LandInfo("40943","90 X 90","kasoa","milky"));
        landInfoList.add(new LandInfo("983454","90 X 90","kasoa","paul"));
        landInfoList.add(new LandInfo("24234","90 X 90","kasoa","mens"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","aaron"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","nana"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","top"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","kamw"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","idol"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","deedat"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","josh"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","qay"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","yawe"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","max"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","big"));
        landInfoList.add(new LandInfo("24234","90 X 90","west","late"));
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
