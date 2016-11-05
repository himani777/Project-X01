package com.crap.booked.AllBooks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.crap.booked.ExchangeOrDonate.EnterDetails;
import com.crap.booked.NetworkServices.AllBooksViaEmail;
import com.crap.booked.NetworkServices.VolleySingleton;
import com.crap.booked.R;
import com.facebook.drawee.gestures.GestureDetector;

import org.json.JSONArray;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rashi on 20-08-2016.
 */public class AllBooks extends android.app.Fragment{


    String ecopy;
    private static List<BooksData> itemList;
    RecyclerView recyclerView;
    private static AllBooksAdapter mAdapter;
    private static AllBooks allBooks;
    private MaterialDialog dialog;




    public static AllBooks newInstance() {

        if(allBooks!=null) return allBooks;

        allBooks= new AllBooks();
        return allBooks;
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.all_books, container, false);

        dialog = new MaterialDialog.Builder(view.getContext())
                .title("Fetching Data")
                .content("And Its Almost There")
                .progress(true, 0)
                .cancelable(false)
                .show();


        Bundle bundle = this.getArguments();
        ecopy = bundle.getString("username");
        Log.d("ecopy",ecopy);

        itemList= new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);

        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");
        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");
        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");
        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");
        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");
        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");
        Log.d("qqqqqqqq","qqqqqqqqqqqqqqqqqq");




        recyclerView.setNestedScrollingEnabled(false);
        // ecopy = getArguments().getString("email");
        getActivity().setTitle("All Books");


      /*  SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String ecopy =  sharedPreferences.getString("username","NoValue");
        Log.d("ecopy",ecopy);*/


        //ecopy = "bhdgds";
        mAdapter = new AllBooksAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVerticalScrollBarEnabled(true);
        //recyclerView.setItemAnimator(new SlideInLeftAnimator());



        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONArray j = jsonArray.getJSONArray(i);

                        /*
                        String image = j.getString(3);
                        String name = j.getString(4);
                        String author = j.getString(5);
                        String description = j.getString(6);
                        String exchange_donate = j.getString(7);
                        String date_posted = j.getString(8);
                        BooksModel item = new BooksModel(image,name,author,description,exchange_donate,date_posted);
                        itemList.add(item);
                        */



                        BooksData item = new BooksData();
                        item.first_name = j.getString(0);
                        item.last_name = j.getString(1);
                        item.contact = j.getString(2);
                        item.latitude = j.getString(3);
                        item.longitude = j.getString(4);

                        item.book_image = j.getString(5);
                        item.book_name = j.getString(6);

                        //Log.d("book names",item.book_image);
                        item.book_author = j.getString(7);
                        item.book_description = j.getString(7);
                        item.book_exchange_donate = j.getString(8);
                        item.book_date_posted = j.getString(9);
                        itemList.add(item);

                        Log.d("bookname",item.book_name);



                    }


                    //t.setText(String.valueOf(sum));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        };

        AllBooksViaEmail a = new AllBooksViaEmail(ecopy,listener);
        /*
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);
        */
        a.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(a);


        mAdapter.notifyDataSetChanged();






        return view;
    }





    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       /* inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.search123);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                *//*Intent searchIntent = new Intent(FirstPage.this,Search.class);
                //send the keyword to the next screen
                searchIntent.putExtra("key",keyword);
                startActivity(searchIntent);*//*
                Bundle bundle = new Bundle();
                bundle.putString("key", keyword);
// set Fragmentclass Arguments
                Search search = new Search();
                search.setArguments(bundle);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.firstpage, search);
                transaction.commit();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String keyword) {
                Bundle bundle = new Bundle();
                bundle.putString("key", keyword);
// set Fragmentclass Arguments
                Search search = new Search();
                search.setArguments(bundle);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.firstpage, search);
                transaction.commit();
                return true;
            }
        });*/

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public void onResume() {
        super.onResume();

    }
}