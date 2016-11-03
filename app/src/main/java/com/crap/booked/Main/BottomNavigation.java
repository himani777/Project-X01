package com.crap.booked.Main;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.crap.booked.AllBooks.AllBooks;
import com.crap.booked.MyBooks.MyBooks;
import com.crap.booked.NearbyBooks.NearbyBooks;
import com.crap.booked.Profile.ProfileFragment;
import com.crap.booked.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;

/**
 * Created by Weirdo on 03-08-2016.
 */
public class BottomNavigation extends AppCompatActivity {


    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    int last_position = 2;

    String email ;

    Toolbar toolbar;
    HomeScreen homescreen ;
    MyBooks mybooks ;
    NearbyBooks nearbybooks ;
    AllBooks allbooks ;
    ProfileFragment profilefragment ;
    android.app.FragmentManager fragmentManager;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Share");




        Bundle extras = getIntent().getExtras();
        username= extras.getString("username");

        SharedPreferences sharedPref = this.getSharedPreferences("Login",Context.MODE_PRIVATE);
        final String fname = sharedPref.getString("fname","A");



       /* SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String ecopy =  sharedPreferences.getString("username","NoValue");


        System.out.println(ecopy);*/
        Log.d("eeeeeeeeeeeeeee",username);
        Log.d("gvcsdgcvs","dggvcgdvcgdvcgvdgcvdgc");

        fragmentManager = getFragmentManager();


        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        HomeScreen.newInstance().setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,HomeScreen.newInstance());
        fragmentTransaction.commit();




        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Nearby Books", R.drawable.ic_location_on_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("All Books",R.drawable.ic_library_books_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("My Shelf", R.drawable.ic_collections_bookmark_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile",  R.drawable.ic_face_black_24dp, R.color.tabcolor);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setCurrentItem(2);
        bottomNavigation.setAccentColor(Color.parseColor("#3B494C"));
        bottomNavigation.setInactiveColor(Color.parseColor("#90F63D2B"));




        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

                switch (position){
                    case 0 :
                        if(position == last_position) break;
                        last_position = position;

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("username",username);
                        NearbyBooks.newInstance().setArguments(bundle1);

                        fragmentTransaction.replace(R.id.fragmentContainer,NearbyBooks.newInstance());

                        fragmentTransaction.commit();
                        break;

                    case 1 :
                        if(position == last_position) break;
                        last_position = position;

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("username",username);
                        AllBooks.newInstance().setArguments(bundle2);
                        fragmentTransaction.replace(R.id.fragmentContainer, AllBooks.newInstance() );
                        fragmentTransaction.commit();
                        break;

                    case 2 :
                        if(position == last_position) break;
                        last_position = position;

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("username",username);
                        HomeScreen.newInstance().setArguments(bundle3);

                        fragmentTransaction.replace(R.id.fragmentContainer,HomeScreen.newInstance());
                        fragmentTransaction.commit();
                        break;

                    case 3 :
                        if(position == last_position) break;
                        last_position = position;

                        Bundle bundle6 = new Bundle();
                        bundle6.putString("username",username);
                        MyBooks.newInstance().setArguments(bundle6);
                        fragmentTransaction.replace(R.id.fragmentContainer, MyBooks.newInstance());
                        fragmentTransaction.commit();
                        break;

                    case 4 :
                        if(position == last_position) break;
                        last_position = position;

                        Bundle bundle5 = new Bundle();
                        bundle5.putString("username",username);
                        bundle5.putString("fname",fname);
                        ProfileFragment.newInstance().setArguments(bundle5);
                        fragmentTransaction.replace(R.id.fragmentContainer,  ProfileFragment.newInstance());
                        fragmentTransaction.commit();
                        break;


                }
                return true;
            }
        });


    }





    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }









    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        final View view = findViewById(R.id.fr);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
