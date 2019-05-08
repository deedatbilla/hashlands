package com.deedat.landsystem.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MyCustomObject {
    // Listener defined earlier
    public interface MyCustomObjectListener {
        public void onObjectReady(String title);
        public void onDataLoaded(String data);
    }

    // Member variable was defined earlier
    private MyCustomObjectListener listener;
private Context context;
    // Constructor where listener events are ignored
    public MyCustomObject(Context context) {
        this.context=context;
        // set null or default listener or accept as argument to constructor
        this.listener = null;
        getpubkey();
    }

    // ... setter defined here as shown earlier
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }


    public void getpubkey(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://hashland.herokuapp.com/getKeys";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                     String key=response;
                        if (listener != null)
                            listener.onDataLoaded(key);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("tag", "that didnt work");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}