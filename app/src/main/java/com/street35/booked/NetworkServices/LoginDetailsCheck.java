package com.street35.booked.NetworkServices;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Weirdo on 23-12-2016.
 */

public class LoginDetailsCheck extends StringRequest {

    private static final String FirstEntry_url = "http://booked.16mb.com/FirstEntry.php";
    private Map<String, String > params;

    public LoginDetailsCheck(String email , String fname , String lname , Response.Listener<String> listener){
        super(Request.Method.POST, FirstEntry_url , listener, null);
        params= new HashMap<>();
        params.put("email",email);
        params.put("fname",fname);
        params.put("lname",lname);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
