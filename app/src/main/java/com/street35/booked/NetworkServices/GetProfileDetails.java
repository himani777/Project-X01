package com.street35.booked.NetworkServices;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Weirdo on 03-10-2016.
 */

public class GetProfileDetails extends StringRequest {

    private static final String get_details_url = "http://booked.16mb.com/GetDetailsViaEmail.php?email=";
    private Map<String, String > params;

    public GetProfileDetails(String email , Response.Listener<String> listener){
        super(Request.Method.GET, get_details_url+email , listener , null);

    }



}
