package com.deedat.landsystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toolbar;

import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;
import com.deedat.landsystem.contants.Constants;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private List<LandInfo> landInfoList;
    private land_dets_adapter mAdapter;
    private ProgressDialog progressDialog;
    Toolbar toolbar;
    Constants.TransitionType type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Favorites");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        recyclerView = findViewById(R.id.recycler_view);
        progressDialog = new ProgressDialog(this);
        init();
        fetchlands();

    }

    public void initAnimation(){

    }

    public void init(){
        type= (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    public void fetchlands(){

        landInfoList = new ArrayList<>();
        mAdapter = new land_dets_adapter(this, landInfoList);

        //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        //  recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //progressDialog.setTitle("LogIn");


        mAdapter.notifyDataSetChanged();
        //progressDialog.dismiss();
        progressDialog.setMessage("loading");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.setOnItemClickListener(new land_dets_adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(FavouriteActivity.this, PropertyActivity.class);
                intent.putExtra("land_data", landInfoList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
//        landInfoList.add(new LandInfo("GH-242324334","190 X 90","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
//        landInfoList.add(new LandInfo("GH-977B8284","90 X 90","GR-Achimota","Deedat Idriss Billa","https://horizon-media.s3-eu-west-1.amazonaws.com/s3fs-public/styles/large/public/field/image/Kenyan%20landscape%20cropped%20-%20shutterstock_216892456%20-%20Maciej%20Czekajewski.jpg?itok=7LOkfAm1"));
progressDialog.dismiss();
    }
}
