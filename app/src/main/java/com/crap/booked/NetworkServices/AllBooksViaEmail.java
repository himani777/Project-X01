package com.crap.booked.NetworkServices;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 21-08-2016.
 */
public class AllBooksViaEmail extends StringRequest {

    private static final String Roll_url = "http://booked.16mb.com/allbooks.php";
    private Map<String, String > params;

    public AllBooksViaEmail(String email , Response.Listener<String> listener){
        super(Request.Method.POST, Roll_url , listener, null);
        params= new HashMap<>();
        params.put("email",email);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


