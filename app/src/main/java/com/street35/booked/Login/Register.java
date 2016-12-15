package com.street35.booked.Login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.street35.booked.R;

/**
 * Created by Rashi on 06-10-2016.
 */
public class Register extends Fragment {
    EditText email, password, fname,lname,location;

    Button signin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.register,container,false);
        email = (EditText)v.findViewById(R.id.register_email);
        password = (EditText)v.findViewById(R.id.register_password);
        fname = (EditText)v.findViewById(R.id.register_fname);
        lname = (EditText)v.findViewById(R.id.register_lname);
        location = (EditText)v.findViewById(R.id.register_location);



        return v;

    }
}

