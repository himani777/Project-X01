package com.street35.booked.NetworkServices;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 27-08-2016.
 */
public class LatLongViaEmail extends StringRequest {

    private static final String LatLong_url = "http://booked.16mb.com/BooksAndLocation.php";
    private Map<String, String > params;

    public LatLongViaEmail(String email , Response.Listener<String> listener){
        super(Request.Method.GET, LatLong_url , listener, null);
        params= new HashMap<>();
        params.put("email",email);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
