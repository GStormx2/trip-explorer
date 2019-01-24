package com.example.omega.geobangla2;

import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class LandingPage extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Home");


        //setting back button in action bar
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResortHomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new ResortHomeFragment();
                            actionBar.setTitle("Home");
                            break;
                        case R.id.nav_favorite:
                            selectedFragment = new FavoriteFragment();
                            actionBar.setTitle("Favorites");
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            actionBar.setTitle("Explore");
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            actionBar.setTitle("Profile");
                            break;
                        case R.id.nav_credit:
                            selectedFragment = new CreditFragment();
                            actionBar.setTitle("Credits");
                            break;
                    }


                   // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().remove(selectedFragment);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
}
