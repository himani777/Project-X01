package com.crap.booked.MyBooks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.crap.booked.AllBooks.BooksData;
import com.crap.booked.AllBooks.UserBooksAdapter;
import com.crap.booked.NetworkServices.MyBooksViaEmail;
import com.crap.booked.NetworkServices.VolleySingleton;
import com.crap.booked.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rashi on 20-08-2016.
 */
public class MyBooks extends android.app.Fragment{


    String ecopy;
    private List<BooksData> itemList;
    private RecyclerView recyclerView;
    private UserBooksAdapter mAdapter;
    static MyBooks myBooks;
    private MaterialDialog dialog;


    public static MyBooks newInstance() {

        if(myBooks!=null) return myBooks;
        MyBooks myBooks= new MyBooks();
        Log.d("Hey","Sad");


        return myBooks;
    }






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.my_books, container, false);
        //ecopy = "goel.rashi48@gmail.com";
        //ecopy = getArguments().getString("username");
        //Log.d("mybooks",ecopy);


        dialog = new MaterialDialog.Builder(view.getContext())
                .title("Fetching Data")
                .content("And Its Almost There")
                .progress(true, 0)
                .cancelable(false)
                .show();

        itemList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerView.setNestedScrollingEnabled(false);
        getActivity().setTitle("My Shelf");
//        ecopy = getArguments().getString("email");

       SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String ecopy =  sharedPreferences.getString("username","");


        System.out.println(ecopy);
        Log.d("ecopy",ecopy);





       mAdapter = new UserBooksAdapter(itemList);
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

                    Log.d(response,response);

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++)
                    {
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

                        JSONArray j = jsonArray.getJSONArray(i);

                        BooksData item = new BooksData();
                        item.book_image = j.getString(3);
                        item.book_name = j.getString(4);
                        item.book_author = j.getString(5);
                        item.book_description = j.getString(6);
                        item.book_exchange_donate = j.getString(7);
                        item.book_date_posted = j.getString(8);
                        item.bid = j.getInt(0);
                        itemList.add(item);

                    }


                    //t.setText(String.valueOf(sum));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Log.d("Data Set Chanhed","Data Set Cganhed");
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
            }
        };

      //  mAdapter.notifyDataSetChanged();

        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        MyBooksViaEmail a = new MyBooksViaEmail(ecopy,listener);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
        a.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(a);





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
