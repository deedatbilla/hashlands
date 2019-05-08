package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class mylandsActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    String formattedDate;
    String landcode;
    String current_date;
    LandInfo landInfo;
    Button trx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylands);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = findViewById(R.id.image);
        TextView owner = findViewById(R.id.name);
        TextView dimen = findViewById(R.id.dimen);
        TextView code = findViewById(R.id.code);
        TextView landcode = findViewById(R.id.landcode);
        TextView region = findViewById(R.id.landregion);

        toolbar.inflateMenu(R.menu.land_detail_menu);

        Intent intent = getIntent();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        progressDialog = new ProgressDialog(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
         landInfo = intent.getParcelableExtra("land_data");
        Glide.with(this)
                .load(landInfo.getThumbnail())
                .into(imageView);
        //  owner.setText(landInfo.getOwner_name());
        code.setText(landInfo.getLandcode());
        landcode.setText(landInfo.getLandcode());
        region.setText(landInfo.getLandregion() + "-" + landInfo.getLandarea());
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        //collapsingToolbarLayout.setTitle(landInfo.getLandcode());
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.isTitleEnabled();
        trx=findViewById(R.id.trx);
        trx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mylandsActivity.this, SellActivity.class);
                intent.putExtra("asaasecode", readAsaasecode());
                startActivity(intent);
            }
        });
        ToggleButton toggle = (ToggleButton) findViewById(R.id.sale_status);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    forsale();
                    Toast.makeText(mylandsActivity.this, "has been set for sale", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    remove();
                    Toast.makeText(mylandsActivity.this, "has been removed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void forsale() {
        List<LandInfo> landInfoList;
        final String uid;
        FirebaseAuth firebaseAuth;
        firebaseAuth= FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid();
        final String url = "https://hashland.herokuapp.com/setForSale";
        final RequestQueue queue = Volley.newRequestQueue(this);
        FirebaseDatabase database;
        final DatabaseReference databaseReference,databaseReference_sale,setsale;
        database=FirebaseDatabase.getInstance();
        progressDialog.setMessage("loading.. please wait,");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        databaseReference_sale = database.getReference("LandsForSale");
        setsale = database.getReference("myland_linked_to_account");
        landInfoList = new ArrayList<>();

        int year;
        int month;
        int dayOfMonth;
        Calendar calendar;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        current_date = dayOfMonth + "/" + (month + 1) + "/" + year;
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate = dateFormat.format(date);
        landcode = landInfo.getLandcode();
        final HashMap<String, String> params = new HashMap<String, String>();


        params.put("landid", landcode);
        params.put("date", current_date);
        params.put("time", formattedDate);

        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Toast.makeText(mylandsActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mylandsActivity.this, "volley error", Toast.LENGTH_SHORT).show();


                progressDialog.dismiss();
               // Log.v("api_error", String.valueOf(landInfoList.size()));
                //  Log.v("mydatalist",params2.toString());
                //Toast.makeText(AddLandActivity.this,"security code or asaasecode not valid",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }
    public void remove() {
        List<LandInfo> landInfoList;
        final String uid;
        FirebaseAuth firebaseAuth;
        firebaseAuth= FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid();
        final String url = "https://hashland.herokuapp.com/removeFromSale";
        final RequestQueue queue = Volley.newRequestQueue(this);
        FirebaseDatabase database;
        final DatabaseReference databaseReference,databaseReference_sale,setsale;
        database=FirebaseDatabase.getInstance();
        progressDialog.setMessage("loading.. please wait,");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        databaseReference_sale = database.getReference("LandsForSale");
        setsale = database.getReference("myland_linked_to_account");
        landInfoList = new ArrayList<>();

        int year;
        int month;
        int dayOfMonth;
        Calendar calendar;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        current_date = dayOfMonth + "/" + (month + 1) + "/" + year;
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate = dateFormat.format(date);
        landcode = landInfo.getLandcode();
        final HashMap<String, String> params = new HashMap<String, String>();


        params.put("landid", landcode);


        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Toast.makeText(mylandsActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mylandsActivity.this, "volley error", Toast.LENGTH_SHORT).show();


                progressDialog.dismiss();
                // Log.v("api_error", String.valueOf(landInfoList.size()));
                //  Log.v("mydatalist",params2.toString());
                //Toast.makeText(AddLandActivity.this,"security code or asaasecode not valid",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }

    private String readAsaasecode() {
        SharedPreferences aSharedPreferences = this.getSharedPreferences(
                "asaasecode", Context.MODE_PRIVATE);
        return aSharedPreferences.getString("code",null );
    }

    }

