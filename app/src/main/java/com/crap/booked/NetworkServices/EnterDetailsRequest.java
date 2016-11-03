package com.crap.booked.NetworkServices;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 31-08-2016.
 */
public class EnterDetailsRequest extends StringRequest
{



        private static final String Log_req_url = "http://test1995.net23.net/EnterBooks.php";
        private Map<String, String > params;

        public EnterDetailsRequest( String username , String imageurl ,String bookname , String bookauthor, String bookdescription , Response.Listener<String> listener){
            super(Request.Method.POST, Log_req_url , listener, null);
            params= new HashMap<>();
            params.put("username",username);
            params.put("imageurl",imageurl);
            params.put("bookname",bookname);
            params.put("bookauthor",bookauthor);
            params.put("bookdescription",bookdescription);


        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }


}
