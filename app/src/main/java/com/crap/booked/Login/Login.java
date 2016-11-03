package com.crap.booked.Login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.crap.booked.R;

/**
 * Created by Rashi on 06-10-2016.
 */
public class Login extends Fragment {
    EditText email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final View v = inflater.inflate(R.layout.loginin,container,false);
        email = (EditText) v.findViewById(R.id.login_email);
        String key=email.getText().toString();
        System.out.println(key);

        return v;
    }
}
