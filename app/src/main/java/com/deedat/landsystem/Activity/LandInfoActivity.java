package com.deedat.landsystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class LandInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_info);
        Toolbar toolbar=findViewById(R.id.toolbar);
        ImageView imageView=findViewById(R.id.image);
        TextView owner=findViewById(R.id.name);
        TextView dimen=findViewById(R.id.dimen);
        TextView code=findViewById(R.id.code);
        TextView region=findViewById(R.id.landregion);

        toolbar.inflateMenu(R.menu.land_detail_menu);
        Intent intent=getIntent();
         toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
        LandInfo landInfo=intent.getParcelableExtra("land_data");
        Glide.with(this)
                .load(landInfo.getThumbnail())
                .into(imageView);
        owner.setText(landInfo.getOwner_name());
        code.setText(landInfo.getLandcode());
        region.setText(landInfo.getLandregion()+" by "+landInfo.getLandarea());
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingToolBar);
        collapsingToolbarLayout.setTitle(landInfo.getLandcode());
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.isTitleEnabled();







    }

    @Override
    public boolean onNavigateUp() {
        finishAfterTransition();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.land_detail_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_fav:
                Toast.makeText(this,"favourited", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_share:
                Toast.makeText(this,"share action clicked", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_note:
                Toast.makeText(this," note action clicked", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
