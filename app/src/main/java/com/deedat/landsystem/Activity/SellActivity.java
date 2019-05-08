package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deedat.landsystem.Model.User;
import com.deedat.landsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SellActivity extends AppCompatActivity {
Toolbar toolbar;
Button generate,start_transction;
EditText mykey,assasecode,buyer_pbkey;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;
DatabaseReference reference;
ArrayList<User> user;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        generate=findViewById(R.id.genPubkey);
        start_transction=findViewById(R.id.start_transaction);
        mykey=findViewById(R.id.pkeyseller);
        buyer_pbkey=findViewById(R.id.pkeybuyer);
        assasecode=findViewById(R.id.asaasecode);
        user=new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        String landcode = null;
        if(bundle.getString("asaasecode")!= null) {
            //TODO here get the string stored in the string variable and do
            // setText() on userName
            landcode=bundle.getString("asaasecode");
            assasecode.setText(landcode);
        }

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth=FirebaseAuth.getInstance();
                String uid=firebaseAuth.getUid();
                firebaseDatabase=FirebaseDatabase.getInstance();
                reference=firebaseDatabase.getReference("users");
                reference.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ///user.clear();
                       // for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            User data=dataSnapshot.getValue(User.class);
                           final String publickey=data.publicKey;
                           // user.add(data);

                       // }

                        SellActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mykey.setText(publickey);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Sell");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        progressDialog = new ProgressDialog(this);
        final HashMap<String,String> params=new HashMap<>();
        final String finalLandcode = landcode;
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url="https://hashland.herokuapp.com/makeTransaction";
        queue.start();
        start_transction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("loading.. please wait,");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                String buyerkey = buyer_pbkey.getText().toString().trim();
                if (TextUtils.isEmpty(buyerkey)) {
                    Toast.makeText(getApplicationContext(), "please enter buyer public key!", Toast.LENGTH_SHORT).show();
                    return;
                }
                params.put("asaasecode", finalLandcode);
                params.put("senderkey",mykey.getText().toString());
                params.put("reciepientkey",buyer_pbkey.getText().toString());
                Log.v("myparams",params.toString());
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {



                                    Toast.makeText(SellActivity.this,"transaction success",Toast.LENGTH_SHORT).show();



                                progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.v("api_error",error.toString());
                        Toast.makeText(SellActivity.this,"transaction failed",Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsObjRequest);
            }
        });


    }
}
