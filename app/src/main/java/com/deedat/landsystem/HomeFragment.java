package com.deedat.landsystem;
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

    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private List<LandInfo> landInfoList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private land_dets_adapter mAdapter;
    private ProgressDialog progressDialog;
    public MaterialSearchBar searchBar;


    private Button fav;

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
        fav=view.findViewById(R.id.btn_fav);
        swipeRefreshLayout=view.findViewById(R.id.swipe);
        progressDialog = new ProgressDialog(getActivity());














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







        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("LOG_TAG", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        //myUpdateOperation();
                    }
                }
        );
       // progressDialog = new ProgressDialog(getActivity());
        //navigation=view.findViewById(R.id.navigationtop);
        //navigation.setVisibility(View.VISIBLE);
       // recyclerView = view.findViewById(R.id.recycler_view_tag);
        //  coordinatorLayout = findViewById(R.id.coordinator_layout);
      fetchlands();
        addToFavorite();

       // tags();

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
        landInfoList.add(new LandInfo("GH-98676763","90 X 90","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));
        landInfoList.add(new LandInfo("GH-98676763","90 X 90","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));
        landInfoList.add(new LandInfo("GH-98676763","90 X 90","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));


    }

    public void addToFavorite(){
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
       final String uid=auth.getUid();

        mAdapter.onfavoriteClick(new land_dets_adapter.onfavoriteClickListener() {

            @Override

            public void onfavClick(final int position) {
                progressDialog.setMessage("Adding to favourites");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String key = ref.push().getKey();

                            //ref.child(uid).child(key).setValue(landInfoList.get(position).getLandcode());
                        Map<String, Object> land = new HashMap<>();
                        land.put("land_code",landInfoList.get(position).getLandcode());
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/favorites/" + uid+"/"+landInfoList.get(position).getLandcode(),land);
                        ref.updateChildren(childUpdates);
                        progressDialog.dismiss();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void showDialogbox(boolean state){
        progressDialog.setMessage("Adding to favourites");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(state);
    }

}
