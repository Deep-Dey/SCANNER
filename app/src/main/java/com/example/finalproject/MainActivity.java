package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.accounts.Account;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    CircularImageView circularImageView;
    TextView textView;

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String CHECK = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        circularImageView=findViewById(R.id.nav_img);
        textView=findViewById(R.id.nav_name);

        navigationView=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawer);
        navigationView.setItemIconTintList(null);   //To make the icon visible
        toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_toggle_24);

        View hView =  navigationView.getHeaderView(0);
        circularImageView = hView.findViewById(R.id.nav_img);
        textView = (TextView)hView.findViewById(R.id.nav_name);

        LoadSharedPreferences();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentOtherDoc()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_Account:
                        temp=new FragmentAccount();
                        break;

                    case R.id.item_info:
                        temp=new FragmentSite();
                        break;

                    case R.id.item_pdf:
                        temp=new Fragment_PDF();
                        break;

                    case R.id.item_aadhaar:
                        temp=new FragmentAadhar();
                        break;

                    case R.id.item_QR:
                        temp=new FragmentQRScanner();
                        break;

                    case R.id.item_other:
                        temp=new FragmentOtherDoc();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                LoadSharedPreferences();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LoadSharedPreferences();
    }

    public void LoadSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, "");
        String id = sharedPreferences.getString(ID, "");
        String check = sharedPreferences.getString(CHECK, "");
        if(check.length()==3){
            String image_url="https://graph.facebook.com/"+id+"/picture?type=large&width=720&height=720";
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.dontAnimate();
            Glide.with(this).load(image_url).into(circularImageView);
            textView.setText(name);
        }
        else{
            textView.setText("SCANNER");
            circularImageView.setImageResource(R.drawable.icon);
        }
    }
}