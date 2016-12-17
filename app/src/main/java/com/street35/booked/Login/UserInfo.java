package com.street35.booked.Login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.street35.booked.Main.BottomNavigation;
import com.street35.booked.NetworkServices.EditProfileRequest;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Rashi on 06-10-2016.
 */
public class UserInfo extends AppCompatActivity {
    EditText address,contact,university;
    String fname,lname,email;
    String add,con,uni;
    SharedPreferences sharedPref;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        setContentView(R.layout.user_info);
        super.onCreate(savedInstanceState);

        sharedPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("Location", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor1 = sharedPreferences.edit();
        final SharedPreferences.Editor editor = sharedPref.edit();


        address = (EditText)findViewById(R.id.ui_address);
        university = (EditText)findViewById(R.id.ui_university);
       contact = (EditText)findViewById(R.id.ui_contact);
        final Button enter = (Button)findViewById(R.id.done);


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add = address.getText().toString();
                uni = university.getText().toString();
                con = contact.getText().toString();
                if (TextUtils.isEmpty(add) && TextUtils.isEmpty(con)) {
                    Snackbar.make(getCurrentFocus(), "Enter details", Snackbar.LENGTH_LONG).show();
                } else {


                    fname = sharedPref.getString("fname", "NoValue");
                    lname = sharedPref.getString("lname", "NoValue");
                    email = sharedPref.getString("email", "goel.rashi48@gmail.com");
                    //email = "goel.rashi48@gmail.com";

                    Log.d("eeeeemmmmmmmllll",email);


                    Geocoder coder = new Geocoder(getApplicationContext());
                    List<Address> addresses;
                    try {
                        addresses = coder.getFromLocationName(add, 5);

                        Address location = addresses.get(0);
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();
                        Log.i("Lat", "" + lat);
                        Log.i("Lng", "" + lng);
                        editor1.putString("latitude",String.valueOf(lat));
                        editor1.putString("longitude",String.valueOf(lng));
                        editor1.commit();


                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("abbbbbbbbbbbbbbbbb","bbbbbbbbbbbbbbbbbbb");



                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    Log.d(response, response);
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        editor.putString("address", add);
                                        editor.putString("university", uni);
                                        editor.putString("contact", con);
                                        editor.putString("sex","2");
                                        editor.commit();
                                        Log.d("ffffffffffffffffff",fname+lname+uni+con+add+"2"+email);

                                        Intent i = new Intent(UserInfo.this, BottomNavigation.class);
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };



                        EditProfileRequest profileRequest = new EditProfileRequest(fname, lname, uni, con, add, "2",
                                email, String.valueOf(lat), String.valueOf(lng), listener);

                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(profileRequest);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
         });

        }





}
