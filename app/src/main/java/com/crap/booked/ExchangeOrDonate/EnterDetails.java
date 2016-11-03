package com.crap.booked.ExchangeOrDonate;

/**
 * Created by Rashi on 31-08-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crap.booked.Main.HomeScreen;
import com.crap.booked.NetworkServices.EnterDetailsRequest;
import com.crap.booked.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class EnterDetails extends AppCompatActivity {

    String isbncode , url , p=null;

    private static int RESULT_LOAD_IMAGE = 1;
    Button resultset;
    EnterDetailsRequest set;
    private EditText bookd;

    private TextView authorText, titleText, descriptionText, bookp ;
    private LinearLayout starLayout;
    private ImageButton thumbView;
    String value;
    Toolbar toolbar;
    String username;
    int flag =0;



    void getImage(String ur){
        Log.d("asasasa", ur);
        ImageRequest imgRequest = new ImageRequest(ur,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        thumbView.setImageBitmap(response);
                    }
                }, 0, 0, null , null);

        Volley.newRequestQueue(this).add(imgRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        Bundle extras = getIntent().getExtras();
        value = extras.getString("ED");

        Log.d("ED",value);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Share");

        SharedPreferences sharedPreferences = this.getSharedPreferences("Login",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","NoValue");
        Log.d("username",username);



        resultset= (Button) findViewById(R.id.resultSubmit);
        authorText = (TextView)findViewById(R.id.book_author);
        titleText = (TextView)findViewById(R.id.book_title);
        bookp = (TextView)findViewById(R.id.book_publisher);
        thumbView = (ImageButton) findViewById(R.id.thumb);
        bookd =(EditText) findViewById(R.id.des);

        thumbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        resultset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //  Toast.makeText(getApplication(),"Result Button",Toast.LENGTH_LONG).show();


                if(username=="NoValue"){
                    Snackbar.make(getCurrentFocus(),"Login again to enter book details",Snackbar.LENGTH_LONG).show();
                }
                else{


                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                Log.d("response3",response);

                                if (success) {
                                    Snackbar.make(getCurrentFocus(),"Succesfully entered details",Snackbar.LENGTH_LONG).show();


                                    flag =1;
                                    //newActivity(flag);
                                /*Intent i = new Intent(getApplication() , HomeScreen.class);
                                startActivity(i);*/

                                } else {
                                    Toast.makeText(getApplication(), "Woops An Error Occurred", Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };





                    set = new EnterDetailsRequest(username, p,titleText.getText().toString(),
                            authorText.getText().toString() , bookd.getText().toString() ,value, listener);

                    RequestQueue queue = Volley.newRequestQueue(getApplication());
                    queue.add(set);

                }



            }
        });

        Intent iin= getIntent();
        final Bundle[] b = {iin.getExtras()};
        Log.d("ABC","y1");
        if(b[0] !=null)
        {
            Log.d("ABC","y2");
            isbncode = b[0].getString("ABCD");
            Log.d("ABC",isbncode + "isbn ends");
            url = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbncode;
            getInfo(url);

        }





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            thumbView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }



    //Getting Info : Title, Name, Author

    void getInfo(String url){


        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        String b=response;
                        try {

                            Log.d("aaaaaa", b);
                            JSONObject obj = new JSONObject(b);
                            JSONArray items = obj.getJSONArray("items");
                            JSONObject info = items.getJSONObject(0);
                            JSONObject abc= info.getJSONObject("volumeInfo");
                            JSONArray author = abc.getJSONArray("authors");
                            JSONObject imagelink = abc.getJSONObject("imageLinks");
                            String ur = imagelink.getString("thumbnail");
                            p=ur;
                            StringBuilder aut = new StringBuilder();
                            for(int i = 0; i < author.length(); i++)
                            {
                                aut.append(author.get(i));
                            }


                            titleText.setText(abc.getString("title"));
                            bookp.setText( abc.getString("publisher"));
                            authorText.setText(aut.toString());
                            getImage(ur);



                        } catch (JSONException e) {
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });


        // Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void newActivity(int flag){
        if(flag==1){
            Intent i = new Intent(this,HomeScreen.class);
            startActivity(i);
        }
    }








}
