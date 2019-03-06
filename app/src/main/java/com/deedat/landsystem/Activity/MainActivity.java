package com.deedat.landsystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.deedat.landsystem.HomeFragment;
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
//import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    Toolbar toolbar;
    Fragment fragment;
    FirebaseAuth auth;
    TextView userEmail;
    DatabaseReference ref;
    public MaterialSearchBar searchBar;
    private DrawerLayout mDrawerLayout;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
//    BottomNavigationView navigation;
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                   // toolbar.setTitle("Home");
//                    fragment = new HomeFragment();
//                    loadFragment(fragment);
//
//                    return true;
//                case R.id.navigation_activity:
//                    //toolbar.setTitle("Activity");
//                    fragment = new ActivityFragment();
//                   loadFragment(fragment);
//
//
//                    return true;
//                case R.id.navigation_profile:
//                   // toolbar.setTitle("Profile");
//                    fragment = new ProfileActivity();
//                    loadFragment(fragment);
//
//                    return true;
//
//                case R.id.navigation_fav:
//                    //toolbar.setTitle("Favorites");
//                    fragment = new ActivityFragment();
//                    loadFragment(fragment);
//
//                    return true;
//            }
//            return false;
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        String email=auth.getCurrentUser().getEmail();
        String uid=auth.getUid();
        coordinatorLayout=findViewById(R.id.container);
//        navigation = (BottomNavigationView) findViewById(R.id.navigation);
//
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        //setSupportActionBar(toolbar);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
       ref=database.getReference("users");
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.inflateMenu(R.menu.main_menu);
        searchBar.setText("Search lands...");
        Log.d("LOG_TAG", getClass().getSimpleName() + ": text " +  searchBar.getText());
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
        fragment = new HomeFragment();
        loadFragment(fragment);

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

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
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



    private void loadFragment(Fragment fragment) {
        // load fragment

        int count = getSupportFragmentManager().getBackStackEntryCount();
        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();

    }
    public void property(){
        Intent intent=new Intent(this, PropertyActivity.class);
        startActivity(intent);
    }

    public void fav(){
        Intent intent=new Intent(this, FavouriteActivity.class);
        startActivity(intent);
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


}