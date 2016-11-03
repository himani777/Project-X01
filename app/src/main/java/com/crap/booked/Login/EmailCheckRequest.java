package com.crap.booked.Login;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 07-10-2016.
 */
public class EmailCheckRequest extends StringRequest {
    private static final String emailcheck_url = "http://booked.16mb.com/emilcheck.php?email=";
   // private Map<String, String > params;


    public EmailCheckRequest(String email , String password, Response.Listener<String> listener){
        super(Method.GET, emailcheck_url+email+"&password="+password , listener, null);
/*
        params= new HashMap<>();
        params.put("email",email);
        params.put("password",password);
*/
    }

   /* @Override
    public Map<String, String> getParams() {
        return params;
    }*/

}
