package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.deedat.landsystem.Adapter.land_dets_adapter;
import com.deedat.landsystem.Adapter.suggestions_adapter;
import com.deedat.landsystem.HomeFragment;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.Model.User;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private land_dets_adapter mAdapter;
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
        databaseReference=database.getReference("LandsForSale");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.inflateMenu(R.menu.main_menu);
        Log.d("LOG_TAG", getClass().getSimpleName() + ": text " + searchBar.getText());
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        recyclerView = findViewById(R.id.recycler_view_vertical);

        progressBar = findViewById(R.id.progress_bar);
        fav = findViewById(R.id.btn_fav);
        progressDialog = new ProgressDialog(this);

        // floatingActionButton.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fff")));


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
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {


                            case R.id.nav_home:
                                //toolbar.setTitle("Home");
                                //fragment = new HomeFragment();
                                //loadFragment(fragment);
                                // navigation.setSelectedItemId(R.id.navigation_home);

                                return true;
                            case R.id.nav_activity:
                                // toolbar.setTitle("Activity");
                                // fragment = new ActivityFragment();
                                //loadFragment(fragment);

                                //  navigation.setSelectedItemId(R.id.navigation_activity);

                                return true;


                            case R.id.nav_profile:
                                //toolbar.setTitle("Settings");
                                //fragment = new ProfileActivity();
                                // loadFragment(fragment);
                                // navigation.setSelectedItemId(R.id.navigation_profile);
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
                            // Add code here to update the UI based on the item selected
                            // For example, swap UI fragments here
                        }
                        return true;

                    }
                });
        landInfoList = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new suggestions_adapter(inflater);

        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Find Land..");

        // for (int i = 1; i < 11; i++) {
        //   suggestions.add(new Product(products[i -1], i * 10));
        //}


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

    public void fav(){
       // Pair[] pair=new Pair[2];
        //pair[1]=new Pair<View,String>(searchBar,"search");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent=new Intent(this, FavouriteActivity.class);

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


        mAdapter = new land_dets_adapter(this, landInfoList,true);

          RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
          recyclerView.setLayoutManager(mLayoutManager);
         recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
//        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        RecyclerView myList = (RecyclerView) findViewById(R.id.my_recycler_view);
//        myList.setLayoutManager(layoutManager);

        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));
        landInfoList.add(new LandInfo("GH-242324334","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750","GA","1200","800","Agogo"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAdapter.notifyDataSetChanged();
        //progressDialog.dismiss();
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.setOnItemClickListener(new land_dets_adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"clicked",Toast.LENGTH_SHORT).show();
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
                progressDialog.setMessage("please wait");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String key = ref.push().getKey();
                     if(!dataSnapshot.child("favorites").child(uid).child(landInfoList.get(position).getLandcode()).exists()){
                         Map<String, Boolean> land = new HashMap<>();
                         land.put("favorited",true);
                         Map<String, Object> childUpdates = new HashMap<>();
                         childUpdates.put("/favorites/" + uid+"/"+landInfoList.get(position).getLandcode(),land);
                         ref.updateChildren(childUpdates);
                         progressDialog.dismiss();
                         mAdapter.clicked=true;

                            }

                       String favorites =dataSnapshot.child("favorites").child(uid).child(landInfoList.get(position).getLandcode()).getValue().toString();
                      // Log.v("testingv",test);
                        if(favorites.contains("true")){

                            Map<String, Boolean> land = new HashMap<>();
                            land.put("favorited",false);
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/favorites/" + uid+"/"+landInfoList.get(position).getLandcode(),land);
                            ref.updateChildren(childUpdates);
                            progressDialog.dismiss();
                            mAdapter.clicked=false;

                        }
                        else {

                            Map<String, Boolean> land = new HashMap<>();
                            land.put("favorited",true);
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/favorites/" + uid+"/"+landInfoList.get(position).getLandcode(),land);
                            ref.updateChildren(childUpdates);
                            progressDialog.dismiss();
                            mAdapter.clicked=true;
                        }
                        //ref.child(uid).child(key).setValue(landInfoList.get(position).getLandcode());



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
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