package com.crap.booked.Login;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.crap.booked.Main.BottomNavigation;
import com.crap.booked.NetworkServices.EditProfileRequest;
import com.crap.booked.NetworkServices.EmailExist;
import com.crap.booked.NetworkServices.EnterProfileDetails;
import com.crap.booked.NetworkServices.NotConnected;
import com.crap.booked.NetworkServices.RegisterUser;
import com.crap.booked.NetworkServices.VolleySingleton;
import com.crap.booked.Profile.AddressRequest;
import com.crap.booked.Profile.EditDetails;
import com.crap.booked.Profile.GeocodingLocation;
import com.crap.booked.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rashi on 06-10-2016.
 */
public class Authentication extends AppCompatActivity{
    Login login = new Login();
    Register register = new Register();
    Button signup;
    TextView forgootpass;
    ProgressDialog progressDialog;
    String un,ps;
    String ema;
    int em_val=0;

    ImageView done;
    android.app.FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.uthentication);

        boolean conn = isConnected(getApplicationContext());
        Log.d("abs", String.valueOf(conn));
        if (!conn) {
            Intent i = new Intent(this, NotConnected.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
            finish();

        } else {
            final SharedPreferences sharedPref = this.getSharedPreferences("Login", Context.MODE_PRIVATE);


            un = sharedPref.getString("username", "NoValue");
            ps = sharedPref.getString("password", "NoValue");
            // Get lat and long from sharedpref
            SharedPreferences sharedPreferences = this.getSharedPreferences("Location", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("latitude", "0");
            editor.putString("longitude", "0");
            editor.commit();


            Log.d("sssssssssssssssss", "ddddddddddddddddddddddddd");
            Log.d(un, ps);
            Log.d(un, ps);
            Log.d(un, ps);
            Log.d(un, ps);
            Log.d("sssssssssssssssss", "ddddddddddddddddddddddddd");


            if (!un.equalsIgnoreCase("NoValue") || !ps.equalsIgnoreCase("NoValue")) {
                // Snackbar.make( getApplication().onConfigurationChanged;, "Welcome buddy !", Snackbar.LENGTH_LONG).show();
                Intent i = new Intent(Authentication.this, BottomNavigation.class);
                i.putExtra("username", un);
                startActivity(i);
                finish();
            }else{

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.add(R.id.upper, login);
                fragmentTransaction.commit();

                signup = (Button) findViewById(R.id.signup);
                forgootpass = (TextView) findViewById(R.id.forgotpass);
                done = (ImageView) findViewById(R.id.done);

                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (signup.getText() == "LOGIN") {
                            System.out.println(signup.getText());
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.upper, login);
                            fragmentTransaction.commit();
                            signup.setText("SIGN UP");
                            forgootpass.setText("Forgot Password?");
                        } else {
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.upper, register);
                            fragmentTransaction.commit();
                            signup.setText("LOGIN");
                            forgootpass.setText("");
                        }

                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        android.app.Fragment f = fragmentManager.findFragmentById(R.id.upper);
                        if (f instanceof Login) {

                            final EditText email_h, password_h;
                            email_h = (EditText) findViewById(R.id.login_email);
                            password_h = (EditText) findViewById(R.id.login_password);


                            final String email = email_h.getText().toString();
                            final String password = password_h.getText().toString();

                            final Response.Listener<String> listener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("hey", response);
                                        if (response.contains("1")) {


                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("username", email);
                                            editor.putString("password", password);
                                            editor.commit();
                                            String s = sharedPref.getString("username", "NoValue");


                                            Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();
                                            Intent i = new Intent(Authentication.this, BottomNavigation.class);
                                            i.putExtra("username", un);
                                            startActivity(i);


                                        } else {
                                            Log.d("bc", "ls" + response + "ls");
                                            Snackbar.make(getCurrentFocus(), "Wrong Email/password" + response, Snackbar.LENGTH_LONG).show();

                                            //t.setText(String.valueOf(sum));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            EmailCheckRequest a = new EmailCheckRequest(email, password, listener);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);


                        } else {


                            final EditText email, password, fname, lname, location,contact;
                            email = (EditText) findViewById(R.id.register_email);
                            password = (EditText) findViewById(R.id.register_password);
                            fname = (EditText) findViewById(R.id.register_fname);
                            lname = (EditText) findViewById(R.id.register_lname);
                            location = (EditText) findViewById(R.id.register_location);
                            contact = (EditText) findViewById(R.id.register_contact);

                            final String em = email.getText().toString();
                            ema = em;
                            final String pass = password.getText().toString();
                            final String firstname = fname.getText().toString();
                            final String lastname = lname.getText().toString();
                            final String loc = location.getText().toString();
                            final String phone = contact.getText().toString();


                            Log.d("em", em);

                            final boolean[] valid = {false};


                            Response.Listener<String> listener1 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response2) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response2);
                                        Boolean smtp = jsonObject.getBoolean("smtp_check");


                                        System.out.println(String.valueOf(smtp));

                                        if (smtp == false) {
                                            Snackbar.make(getCurrentFocus(), "Enter Valid Email address !", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            valid[0] = true;
                                            System.out.println("Valid email");

                                                    /*SharedPreferences sharedPref = getParent().getPreferences(Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.putString("username" , String.valueOf(email));
                                                    editor.putString("password" , key);
                                                    editor.apply();*/


                                            registerUser(em, pass, firstname, lastname, loc, email, password, fname, lname, location,phone);



                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            };
                            Validation a = new Validation(em, listener1);
                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);


                        }

                    }
                });
            }

        }



    }


    public void registerUser(final String em, final String pass, final String firstname , final String lastname, final String loc, EditText email, EditText password, final EditText fname, final EditText lname, EditText location, final String phone){

        if(TextUtils.isEmpty(firstname) ||TextUtils.isEmpty(lastname) || TextUtils.isEmpty(loc)||TextUtils.isEmpty(pass)|| TextUtils.isEmpty(phone) ){
            Snackbar.make(getCurrentFocus(),"Fill All the Details ",Snackbar.LENGTH_SHORT).show();

        }

        else{
            final SharedPreferences sharedPref = this.getSharedPreferences("Login",Context.MODE_PRIVATE);


            // check if user is already registered
            final Response.Listener<String> listener3 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //   Log.d("hey" , response);
                        if (response.contains("0")) {

                            Log.d("Registering","register");
                            GetLoc(firstname,lastname,em,pass,loc,phone,sharedPref);

                        }
                        else {
                            //Log.d("bc", "ls"+response+"ls");
                            Snackbar.make(getCurrentFocus(), "User Already Registered ! " , Snackbar.LENGTH_LONG).show();

                            //t.setText(String.valueOf(sum));
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };
            EmailExist a = new EmailExist(em,pass,listener3);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);






            //String s = sharedPref.getString("username","NoValue");
           /* Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();
            Intent i = new Intent(Authentication.this , BottomNavigation.class);
            i.putExtra("username",em);
            startActivity(i);*/

        }


    }
    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }







    public void GetLoc(final String firstname, final String lastname, final String em, final String pass, String loc,String phone, final SharedPreferences sharedPref)
    {

        Geocoder coder = new Geocoder(Authentication.this);
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(loc, 5);
            if (addresses == null) {
            }
            Address location = addresses.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.i("Lat",""+lat);
            Log.i("Lng",""+lng);
            SetData(firstname,lastname,em,pass,loc,lat,lng,phone,sharedPref);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void SetData(final String firstname, final String lastname, final String em, final String pass, String loc,double lat, double lon ,String phone, final SharedPreferences sharedPref){


        // Enter details of the user
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String resp) {
                try {

                    Log.d("heyyyyyyyyyyyy", resp);

                    JSONObject jsonObject = new JSONObject(resp);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "s", Toast.LENGTH_SHORT).show();


                        // Add to shared preferences


                        Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", em);
                        editor.putString("password", pass);
                        editor.putString("fname", firstname);
                        editor.putString("lname", lastname);
                        editor.commit();
                        Intent i = new Intent(Authentication.this , BottomNavigation.class);
                        i.putExtra("username",em);
                        startActivity(i);



                    } else {
                        Toast.makeText(getApplicationContext(), "n", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterUser registerUser = new RegisterUser(firstname, lastname, em, pass, loc,lat, lon ,phone, listener);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(registerUser);


    }

/*
    private class GeocoderHandler1 extends Handler {
        @Override
        public void handleMessage(Message message) {
            ArrayList<String> locationAddress = new ArrayList<>();
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getStringArrayList("address");
                    break;
                default:
                    locationAddress = null;
            }
            // t1.setText(locationAddress.get(0));
            String latitude = locationAddress.get(0);
            String longitude = locationAddress.get(1);
            Float lat = Float.valueOf(latitude);
            Float log = Float.valueOf(longitude);
            Log.d("bhhhhhhhhhhhhhh",latitude);

            SharedPreferences sharedPreferences = getSharedPreferences("Location",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude",latitude);
            editor.putString("longitude",longitude);



            if(latitude.length()>0){

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {
                                Toast.makeText(getApplicationContext(),"sucess1",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"abscd",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddressRequest addressRequest= new AddressRequest(ema,latitude,longitude,listener);
               */
/* RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(addressRequest);*//*


                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(addressRequest);


            }


        }
    }
*/



}