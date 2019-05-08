package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deedat.landsystem.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddLandActivity extends AppCompatActivity {
Toolbar toolbar;
EditText asaasecode,securitycode;
String asaase_code,security_code;
FirebaseAuth firebaseAuth;
FirebaseDatabase database;
DatabaseReference databaseReference;
FloatingActionButton fab;
    private ProgressDialog progressDialog;

    private Gson gson;
    JSONObject obj = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_land);
        toolbar = findViewById(R.id.toolbar);
        fab=findViewById(R.id.fab);
        progressDialog = new ProgressDialog(this);
        asaasecode=findViewById(R.id.asaasecode);
        securitycode=findViewById(R.id.securitycode);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Add Property");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        final String uid=firebaseAuth.getUid();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("myland_linked_to_account");


        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url="https://hashland.herokuapp.com/addLandToAccount/";
        queue.start();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("loading.. please wait,");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                HashMap<String, String> params = new HashMap<String,String>();
                // the entered data as the JSON body.
                params.put("asaasecode", asaasecode.getText().toString());
                params.put("securitynumber", securitycode.getText().toString());// the entered data as the JSON body.
               // Toast.makeText(AddLandActivity.this,"khfjbsfd",Toast.LENGTH_SHORT);
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {


                                try {
                                    saveAsaasecode(asaasecode.getText().toString());
                                    JSONObject location=response.getJSONObject("response");
                                    JSONObject dimens=location.getJSONObject("sizeofLand");
                                    final String landcode=location.getString("landcode");
                                    String landregion=location.getString("landregion");
                                    String landarea=location.getString("landarea");
                                    String width=dimens.getString("width");
                                    String length=dimens.getString("length");
                                    final HashMap<String,String> land_data=new HashMap<>();
                                    land_data.put("landcode",landcode);
                                    land_data.put("landarea",landarea);
                                    land_data.put("landregion",landregion);
                                    land_data.put("width",width);
                                    land_data.put("length",length);
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            databaseReference.child(uid).child(landcode).setValue(land_data);
                                            Intent intent=new Intent(AddLandActivity.this,PropertyActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    Toast.makeText(AddLandActivity.this,"land linked successfully",Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.v("api_error",error.toString());
                        Toast.makeText(AddLandActivity.this,"security code or asaasecode not valid",Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsObjRequest);
                //queue.stop();
            }
        });



    }

    private void saveAsaasecode(String asaase_code) {
        SharedPreferences aSharedPreferences = this.getSharedPreferences(
                "asaasecode", Context.MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = aSharedPreferences
                .edit();
        aSharedPreferencesEdit.putString("code", asaase_code);
        aSharedPreferencesEdit.commit();
    }




}
