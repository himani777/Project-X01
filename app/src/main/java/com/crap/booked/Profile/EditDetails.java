package com.crap.booked.Profile;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crap.booked.NetworkServices.EditProfileRequest;
import com.crap.booked.NetworkServices.GetProfileDetails;
import com.crap.booked.NetworkServices.VolleySingleton;
import com.crap.booked.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


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
        Log.d("vzxgsvcgscgsvcd",s);




        //Get details from the database of the user
        GetUserDetails(s);
        //GetUserDetails(s);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                String d1=contact.getText().toString();
                String d2=address.getText().toString();
                String d3=university.getText().toString();
                String d4=fname.getText().toString();
                String d5=lname.getText().toString();

                if(male.isSelected())  sex="M";
                else if(female.isSelected()) sex = "F";
                else sex="O";


                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {
                                Toast.makeText(getApplicationContext(),"dbdvch",Toast.LENGTH_SHORT).show();
                                flag=1;
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"abscd",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditProfileRequest profileRequest= new EditProfileRequest(d4,d5,d3,d1,d2,sex,s,listener);
                /*RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(profileRequest);
                */

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(profileRequest);


                if(d2.length()>0){
                    Toast.makeText(getApplicationContext(),"After Check",Toast.LENGTH_SHORT).show();
                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(d2,getApplicationContext(), new GeocoderHandler());

                }
            }

        });
    }



    private void GetUserDetails(final String email){

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
                        if(Sex.endsWith("1")) male.setChecked(true);
                        else
                        if(Sex.endsWith("2")) female.setChecked(true);
                        else
                        if(Sex.endsWith("3")) others.setChecked(true);
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

    private class GeocoderHandler extends Handler {
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
            latitude = locationAddress.get(0);
            longitude = locationAddress.get(1);
            Float lat = Float.valueOf(latitude);
            Float log = Float.valueOf(longitude);
            Log.d("b",latitude);

            SharedPreferences sharedPreferences = getSharedPreferences("Location",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude",latitude);
            editor.putString("longitude",longitude);



            setLatitude(latitude);
            setLongitude(longitude);


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
                AddressRequest addressRequest= new AddressRequest(s,latitude,longitude,listener);
               /* RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(addressRequest);*/

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(addressRequest);


            }


        }
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}


