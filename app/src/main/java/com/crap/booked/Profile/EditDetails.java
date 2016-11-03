package com.crap.booked.Profile;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crap.booked.Login.Authentication;
import com.crap.booked.NetworkServices.EditProfileRequest;
import com.crap.booked.NetworkServices.GetProfileDetails;
import com.crap.booked.NetworkServices.VolleySingleton;
import com.crap.booked.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
       // setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");

        SharedPreferences sharedPreferences = getSharedPreferences("Login",Context.MODE_PRIVATE);
        s =  sharedPreferences.getString("username","");
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
                String d1 = contact.getText().toString();
                String d2 = address.getText().toString();
                String d3 = university.getText().toString();
                String d4 = fname.getText().toString();
                String d5 = lname.getText().toString();

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



        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Profile Data")
                .content("And Its Almost There")
                .progress(true, 0)
                .show();



        contact=(EditText)findViewById(R.id.ep_contact);
        address=(EditText)findViewById(R.id.ep_address);
        university=(EditText)findViewById(R.id.ep_university);
        fname=(EditText)findViewById(R.id.ep_first_name);
        lname=(EditText)findViewById(R.id.ep_last_name);
        submit = (Button)findViewById(R.id.ep_submit);
        male= (RadioButton) findViewById(R.id.ep_male);
        female= (RadioButton) findViewById(R.id.ep_female);
        others = (RadioButton) findViewById(R.id.ep_others);




        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    dialog.dismiss();
                    Log.d("aa","sdsdsdsdsdsdsdsdsdsdsd");
                    Log.d("aa",response);
                    JSONArray jj = new JSONArray(response);
                        JSONArray j = jj.getJSONArray(0);

                        String First_name = j.getString(0);
                        String Last_name = j.getString(1);
                        String Sex = j.getString(2);

                        String University = j.getString(3);
                        String Contact = j.getString(4);
                        String Address = j.getString(5);
                      //  profileModel = new ProfileModel(first_name , last_name , sex ,
                      //          university , contact , address , email);
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


                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {

                }
            }
        };

        GetProfileDetails a = new GetProfileDetails(email,listener);
        VolleySingleton.getInstance(EditDetails.this).addToRequestQueue(a);


    }





}


