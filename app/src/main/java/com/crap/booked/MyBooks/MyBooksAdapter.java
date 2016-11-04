package com.crap.booked.MyBooks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crap.booked.AllBooks.AllBooksAdapter;
import com.crap.booked.AllBooks.BooksData;
import com.crap.booked.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rashi on 04-11-2016.
 */

public class MyBooksAdapter extends RecyclerView.Adapter<com.crap.booked.MyBooks.MyBooksAdapter.MyViewHolder>  {




        private List<BooksData> itemlist;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView book_name , book_author , book_description ,  book_exchange_donate, book_date_posted;
            ImageView book_image;

            public MyViewHolder(View view) {
                super(view);
                book_name = (TextView) view.findViewById(R.id.mbl_bookname);

                book_author = (TextView) view.findViewById(R.id.mbl_bookauthor);

                book_description = (TextView) view.findViewById(R.id.mbl_bookdescription);

                book_image = (ImageView) view.findViewById(R.id.mbl_bookimage);

                book_exchange_donate = (TextView) view.findViewById(R.id.mbl_exchange_donate);

                book_date_posted = (TextView) view.findViewById(R.id.mbl_date_posted);

            }
        }



        public MyBooksAdapter(List<BooksData> itemList) {
            this.itemlist = itemList;
            Log.e("Sizeeeeee",itemList.size()+"yo");
        }


        @Override
        public com.crap.booked.MyBooks.MyBooksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_books_layout , parent, false);

            return new com.crap.booked.MyBooks.MyBooksAdapter.MyViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


            Log.e("Called","Yoyoyoyooyoy" + position);

            final BooksData booksModel = itemlist.get(position);
            holder.book_name.setText(booksModel.book_name);
            holder.book_author.setText(booksModel.book_author);
            holder.book_description.setText(booksModel.book_author);
            holder.book_date_posted.setText(booksModel.book_date_posted);
            holder.book_exchange_donate.setText(booksModel.book_exchange_donate);



            String image_url = booksModel.book_image;
            Context context = holder.book_image.getContext();



            Picasso.with(context).load(image_url)

                    //.centerCrop()
                    .skipMemoryCache()

                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(holder.book_image);
/*
        Glide.with(context).load(image_url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .crossFade()
                .into(holder.book_image);*/
/*
        final Uri imageUri = Uri.parse(image_url);
        holder.book_image.setImageURI(imageUri);*/


            Log.d(booksModel.book_name + booksModel.book_author , image_url);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(booksModel.book_name + booksModel.book_author , booksModel.book_image);
                    showInputDialog(v , booksModel.book_name , booksModel.book_author , booksModel.book_image
                            , booksModel.first_name ,booksModel.last_name ,booksModel.contact ,
                            booksModel.latitude , booksModel.longitude
                    );

                }
            });


        }

        @Override
        public int getItemCount() {
            Log.e("Sizee",itemlist.size()+"yo");
            return itemlist.size();
        }





        protected void showInputDialog(View v , final String bname , String bauthor , String url , final String fn, String ln
                , final String contact , final String latitude , final String longitude) {

            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
            View promptView = layoutInflater.inflate(R.layout.books_on_click_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

            Log.e("Detailss" , contact );

            Log.e("Detailss" , latitude );
            Log.e("Detailss" , longitude );

            alertDialogBuilder.setView(promptView);
            TextView book_name = (TextView) promptView.findViewById(R.id.dialog_book_name);
            TextView owner = (TextView) promptView.findViewById(R.id.dialog_owner);

            ImageView book_img = (ImageView) promptView.findViewById(R.id.dialog_image);
            ImageView icon_locate = (ImageView) promptView.findViewById(R.id.ab_icon_locate);
            ImageView icon_msg = (ImageView) promptView.findViewById(R.id.ab_icon_msg);
            ImageView icon_call = (ImageView) promptView.findViewById(R.id.ab_icon_call);

            book_name.setText(bname);
            owner.setText(fn + " " + ln);

            Picasso.with(book_img.getContext()).load(url)
                    //.onlyScaleDown()
                    // .centerCrop()
                    // .skipMemoryCache()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(book_img);

            // setup a dialog window
            alertDialogBuilder.setCancelable(true);

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

            icon_locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + Float.valueOf(latitude)
                            + ">,<" + Float.valueOf(longitude) +
                            ">?q=<" + Float.valueOf(latitude)  + ">,<"
                            + Float.valueOf(longitude) + ">(" + bname + ")"));
                    v.getContext().startActivity(intent);
                }
            });

            icon_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+contact));
                    v.getContext().startActivity(intent);
                }
            });

            icon_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri sms_uri = Uri.parse("smsto:"+contact);
                    Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                    sms_intent.putExtra("sms_body", "Hey "+ fn +" ! I would like to exchange your book , "+ bname
                            +"  with mine. Kindly Contact.");
                    v.getContext().startActivity(sms_intent);
                }
            });

        }

    }

