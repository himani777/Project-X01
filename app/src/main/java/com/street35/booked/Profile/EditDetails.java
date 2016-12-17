package com.street35.booked.Profile;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.street35.booked.NetworkServices.EditProfileRequest;
import com.street35.booked.NetworkServices.GetProfileDetails;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class EditDetails extends AppCompatActivity {
    private TextView contact, address, university , fname, lname;
    private Button submit;
    private String s;
    private int flag;
    private RadioButton male , female , others;
    private String sex;
    String latitude,longitude;
    ProfileModel profileModel ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        sharedPref = this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
       // setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");

        SharedPreferences sharedPreferences = getSharedPreferences("Login",Context.MODE_PRIVATE);
        s =  sharedPreferences.getString("email","");
        Log.d("Email Id : ",s);




        //Get details from the database of the user
        GetUserDetails(s);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog abc = new MaterialDialog.Builder(v.getContext())
                        .title("Updating Data")
                        .content("And Its Almost There")
                        .progress(true, 0)
                        .show();



                flag = 0;
                final String d1 = contact.getText().toString();
                final String d2 = address.getText().toString();
                final String d3 = university.getText().toString();
                final String d4 = fname.getText().toString();
                final String d5 = lname.getText().toString();

                if (male.isSelected()) sex = "0";
                else if (female.isSelected()) sex = "1";
                else sex = "2";


                Geocoder coder = new Geocoder(v.getContext());
                List<Address> addresses;
                try {
                    addresses = coder.getFromLocationName(d2, 5);

                    Address location = addresses.get(0);
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    Log.i("Lat", "" + lat);
                    Log.i("Lng", "" + lng);

                    SharedPreferences preferences = getSharedPreferences("Location",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences.edit();
                    editor1.putString("latitude",String.valueOf(lat));
                    editor1.putString("longitude",String.valueOf(lng));





                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    abc.dismiss();
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    Log.d(response,response);
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                                        editor.putString("address", d2);
                                        editor.putString("university", d3);
                                        editor.putString("contact", d1);
                                        editor.putString("fname", d4);
                                        editor.putString("lname", d5);

                                        flag = 1;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Log.e(d1+d2+d3+d4,d5+sex+s);
                        EditProfileRequest profileRequest = new EditProfileRequest(d4, d5, d3, d1, d2, sex,
                                s, String.valueOf(lat), String.valueOf(lng), listener);

                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(profileRequest);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            });

        }






    private void GetUserDetails(final String email){



      /*  final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Profile Data")
                .content("And Its Almost There")
                .progress(true, 0)
                .show();*/

        Log.d("vvvvvvvvvvvvv","vvvvvvvvvvvvvvvvv");


        contact=(EditText)findViewById(R.id.ep_contact);
        address=(EditText)findViewById(R.id.ep_address);
        university=(EditText)findViewById(R.id.ep_university);
        fname=(EditText)findViewById(R.id.ep_first_name);
        lname=(EditText)findViewById(R.id.ep_last_name);
        submit = (Button)findViewById(R.id.ep_submit);
        male= (RadioButton) findViewById(R.id.ep_male);
        female= (RadioButton) findViewById(R.id.ep_female);
        others = (RadioButton) findViewById(R.id.ep_others);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Login",Context.MODE_PRIVATE);
        String First_name = sharedPreferences.getString("fname","");
        String Last_name = sharedPreferences.getString("lname","");
        String University = sharedPreferences.getString("university","");
        String Contact = sharedPreferences.getString("contact","");
        String Address = sharedPreferences.getString("address","");
        String Sex = sharedPreferences.getString("sex","2");







        fname.setText(First_name);
                        lname.setText(Last_name);
                        if(Sex.endsWith("0")) male.setChecked(true);
                        else
                        if(Sex.endsWith("1")) female.setChecked(true);
                        else
                        if(Sex.endsWith("2")) others.setChecked(true);
                        university.setText(University);
                        contact.setText(Contact);
                        address.setText(Address);


                    //t.setText(String.valueOf(sum));





    }

    /*boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            finish();
            return;
        }
    }*/





}


