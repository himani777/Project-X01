package com.crap.booked.Profile;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crap.booked.Login.Authentication;
import com.crap.booked.R;

/**
 * Created by Weirdo on 11-08-2016.
 */
public class ProfileFragment extends android.app.Fragment{
    TextView detail , text1;
    ImageView detailimg;
    android.app.FragmentManager fragmentManager;
    EditDetails editDetails;
    Toolbar toolbar;
    static ProfileFragment profileFragment=null;

    public static ProfileFragment newInstance() {

        if(profileFragment!=null) return profileFragment;

        profileFragment = new ProfileFragment();

        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.profile  ,container ,false);

        detail = (TextView)v.findViewById(R.id.detail);
        text1 = (TextView)v.findViewById(R.id.text1);
        detailimg = (ImageView)v.findViewById(R.id.detailimg);
        fragmentManager = getFragmentManager();
        Log.d("Hey","Hmmm");
        getActivity().setTitle("Profile");



       /* Bundle bundle = this.getArguments();
        String ecopy = bundle.getString("username");
        System.out.println(ecopy);
        Log.d("eeeeeeeeeeeeeee",ecopy);*/

        /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
        String fname=sharedPreferences.getString("fname","U");
        String lname = sharedPreferences.getString("lname"," ");

        char p = fname.toUpperCase().charAt(0);
        char s = lname.toUpperCase().charAt(0);
        */

        //s= sharedPreferences.getString("email","abc");
        TextView profile_name = (TextView) v.findViewById(R.id.profile_name);
        profile_name.setText(String.valueOf("You"));


        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hey","Hmmmasasasasasasas");

                Intent i = new Intent(getActivity(), EditDetails.class);
                startActivity(i);

            }
        });
        detailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hey","Hmmmasasasasasasas");

                Intent i = new Intent(getActivity(), EditDetails.class);
                startActivity(i);

            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hey","Hmmmasasasasasasas");

                Intent i = new Intent(getActivity(), EditDetails.class);
                startActivity(i);

            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profilemenu,menu);

        MenuItem logout = menu.findItem(R.id.logout);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.logout){
           buildAlertMessage();

        }
        return super.onOptionsItemSelected(item);
    }




    private void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Would You Like to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", "NoValue");
                        editor.putString("password", "NoValue");
                        editor.putString("fname","NoValue");
                        editor.putString("lname","NoValue");
                        editor.commit();

                        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Location",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("latitude","0");
                        editor1.putString("longitude","0");
                        dialog.dismiss();
                        dialog.cancel();

                        Intent i = new Intent(getActivity(), Authentication.class);
                        startActivity(i);                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
