package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.Adapter.categoryAdapter;
import com.deedat.landsystem.Adapter.suggestions_adapter;
import com.deedat.landsystem.HomeFragment;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.Model.User;
import com.deedat.landsystem.Model.category;
import com.deedat.landsystem.ProfileActivity;
import com.deedat.landsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
//import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class MainActivity extends AppCompatActivity implements  MaterialSearchBar.OnSearchActionListener,View.OnClickListener {
    Toolbar toolbar;
    Fragment fragment;
    FirebaseAuth auth;
    TextView userEmail;
    DatabaseReference ref;
    public MaterialSearchBar searchBar;
    private DrawerLayout mDrawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private RecyclerView recyclerView,recyclerView_horizontal;

    private List<LandInfo> landInfoList;
    private List<category>  categoryList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private land_dets_adapter mAdapter;
    private  categoryAdapter mAdapter_category;
    private ProgressDialog progressDialog;
    private SuggestionsAdapter customSuggestionsAdapter;


    private Button fav;

    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        String uid = auth.getUid();
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar);
        database=FirebaseDatabase.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.inflateMenu(R.menu.main_menu);

        Log.d("LOG_TAG", getClass().getSimpleName() + ": text " + searchBar.getText());
        searchBar.setCardViewElevation(10);
        recyclerView = findViewById(R.id.recycler_view_vertical);
        recyclerView_horizontal = findViewById(R.id.recycler_view_horizontal);
        progressBar = findViewById(R.id.progress_bar);
        fav = findViewById(R.id.btn_fav);
        progressDialog = new ProgressDialog(this);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navemail = (TextView) headerView.findViewById(R.id.user_eamil);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        final TextView profileInitials = (TextView) headerView.findViewById(R.id.initials);


        navemail.setText(email);

        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                navUsername.setText(user.fullname);
                profileInitials.setText(getShortName(user.fullname));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_activity:
                                // toolbar.setTitle("Activity");
                                // fragment = new ActivityFragment();
                                //loadFragment(fragment);

                                //  navigation.setSelectedItemId(R.id.navigation_activity);

                                return true;


                            case R.id.nav_profile:
                               profile();
                                return true;
                            case R.id.nav_settings:
                                // toolbar.setTitle("Settings");
                                //fragment = new HomeFragment();
                                // loadFragment(fragment);

                                return true;
                            case R.id.nav_properties:

                                property();

                                return true;

                            case R.id.nav_fav:
                                fav();
                                return true;

                            case R.id.action_logout:
                                auth.signOut();
                                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                                return true;
                            case R.id.action_transactions:
                                transactions();
                            // Add code here to update the UI based on the item selected
                            // For example, swap UI fragments here
                        }


                        return true;

                    }
                });
        landInfoList = new ArrayList<>();
        categoryList = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new suggestions_adapter(inflater);
        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Find Land..");
        customSuggestionsAdapter.setSuggestions(landInfoList);
        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                // send the entered text to our filter and let it manage everything
                customSuggestionsAdapter.getFilter().filter(searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        fetchlands();
        addToFavorite();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(this,"log out success",Toast.LENGTH_SHORT).show();
            auth.signOut();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static String getShortName(String name) {
        String[] strings = name.split(" ");//no i18n
        String shortName;
        if (strings.length == 1) {
            shortName = strings[0].substring(0, 2);
        } else {
            shortName = strings[0].substring(0, 1) + strings[1].substring(0, 1);
        }
        return shortName.toUpperCase();
    }



    public void property(){
        Intent intent=new Intent(this, PropertyActivity.class);
        startActivity(intent);
    }
    public void profile(){
        Intent intent=new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    public void fav(){
       // Pair[] pair=new Pair[2];
        //pair[1]=new Pair<View,String>(searchBar,"search");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent=new Intent(this, FavouriteActivity.class);

        startActivity(intent,options.toBundle());
    }

    public void transactions(){
        // Pair[] pair=new Pair[2];
        //pair[1]=new Pair<View,String>(searchBar,"search");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent=new Intent(this, TransactionsActivity.class);

        startActivity(intent,options.toBundle());
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }

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
        mAdapter = new land_dets_adapter(this, landInfoList,false);
        mAdapter_category = new categoryAdapter(this, categoryList);
        RecyclerView.LayoutManager mLayoutManager_h = new LinearLayoutManager(MainActivity.this);
        recyclerView_horizontal.setLayoutManager(mLayoutManager_h);
        LinearLayoutManager layoutManager
               = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_horizontal.setLayoutManager(layoutManager);
        recyclerView_horizontal.setAdapter(mAdapter_category);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        categoryList.add(new category("Rural Lands","https://images.pexels.com/photos/259637/pexels-photo-259637.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryList.add(new category("Urban Lands","https://s3.amazonaws.com/images.wpr.com/thumb-new/Accra.jpg"));
        categoryList.add(new category("Government Lands","data:image/jpeg;base64,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"));
        databaseReference=database.getReference("Lands");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://hashland.herokuapp.com/allLandsForSale";

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                landInfoList.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    LandInfo landInfo = snapshot.getValue(LandInfo.class);
                                    landInfoList.add(landInfo);
                                }




                                mAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setAdapter(mAdapter);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.setOnItemClickListener(new land_dets_adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                Intent intent=new Intent(MainActivity.this, LandInfoActivity.class);
                intent.putExtra("land_data", landInfoList.get(position));
                startActivity(intent,options.toBundle());
            }
        });
        recyclerView.setAdapter(mAdapter);
        //landInfoList.add(new LandInfo("GH-32543232","90 X 90","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));

    }

    public void addToFavorite(){
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        final String uid=auth.getUid();

        mAdapter.onfavoriteClick(new land_dets_adapter.onfavoriteClickListener() {

            @Override

            public void onfavClick(final int position) {
//                progressDialog.setMessage("please wait");
//                progressDialog.show();
//                progressDialog.setCanceledOnTouchOutside(false);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        boolean isFavourite = readState();
//
//
//                            Map<String, String> land = new HashMap<>();
//                            land.put("landarea",landInfoList.get(position).getLandarea());
//                            land.put("landregion",landInfoList.get(position).getLandregion());
//                            land.put("thumbnail",landInfoList.get(position).getThumbnail());
//                            land.put("landcode",landInfoList.get(position).getLandcode());
//                            land.put("width",landInfoList.get(position).getWidth());
//                            land.put("length",landInfoList.get(position).getLength());
//                            land.put("favorited",String.valueOf(isFavourite));
//
//                            Map<String, Object> childUpdates = new HashMap<>();
//                            childUpdates.put("/favorites/" + uid+"/"+landInfoList.get(position).getLandcode(),land);
//                            ref.updateChildren(childUpdates);
//                            progressDialog.dismiss();


                        //ref.child(uid).child(key).setValue(landInfoList.get(position).getLandcode());



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private boolean readState() {
        SharedPreferences aSharedPreferences = this.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        return aSharedPreferences.getBoolean("State", true);
    }

    @Override
public void onStart(){
        super.onStart();
        if(auth!=null){

        }

}

    @Override
    public void onClick(View view) {
      //  customSuggestionsAdapter.addSuggestion(new LandInfo("GH-242324334","1200 by 800 sq.ft","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
    }




}
