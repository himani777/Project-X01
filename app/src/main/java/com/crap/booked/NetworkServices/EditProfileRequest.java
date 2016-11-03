package com.crap.booked.NetworkServices;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 21-08-2016.
 */
public class EditProfileRequest extends StringRequest{

    private static final String Reg_req_url = "http://booked.16mb.com/profile.php";
    private Map<String, String > params;

    public EditProfileRequest(String first_name, String last_name, String university, String contact, String address, String sex , String email , Response.Listener<String> listener){
        super(Request.Method.GET, Reg_req_url , listener , null);
        params= new HashMap<>();
        params.put("fname",first_name);
        params.put("lname",last_name);
        params.put("university",university);
        params.put("contact",contact);
        params.put("address",address);
        params.put("sex",sex);
        params.put("email",email);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}