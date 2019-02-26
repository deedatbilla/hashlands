package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deedat.landsystem.Model.User;
import com.deedat.landsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    Toolbar toolbar;
    android.widget.ProgressBar ProgressBar;
    FirebaseAuth auth;
    private ProgressDialog progressDialog;
    Button register;
    EditText fullname, email, password;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         toolbar=findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        toolbar.setTitle("Register");

        progressDialog = new ProgressDialog(this);
         toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        register = findViewById(R.id.register);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        // login=findViewById(R.id);

        ProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //login=findViewById(R.id.);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_fullname = fullname.getText().toString().trim();
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                if (TextUtils.isEmpty(user_fullname)) {
                    Toast.makeText(getApplicationContext(), "Enter full name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(user_email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(user_password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (user_password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //progressDialog.setTitle("LogIn");
                progressDialog.setMessage("Signing up, Pleas Wait,");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                // ProgressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(user_email, user_password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //ProgressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    onAuthSuccess(task.getResult().getUser());

                                }
                            }
                        });


            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar.setVisibility(View.GONE);
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        String user_fullname = fullname.getText().toString().trim();
        // Write new user
        writeNewUser(user.getUid(), user_fullname, username, user.getEmail());

        // Go to MainActivity
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void writeNewUser(String userId, String fullname, String name, String email) {
        User user = new User(fullname, name, email);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(userId).setValue(user);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public String getPublicKey() {
         String pubKey="";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://d0d35903.ngrok.io/getKeys";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       // pubKey=response;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("tag", "that didnt work");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return pubKey;
    }
}



