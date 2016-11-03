package com.crap.booked.AllBooks;

/**
 * Created by Rashi on 20-08-2016.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crap.booked.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;


public class UserBooksAdapter extends RecyclerView.Adapter<UserBooksAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {




    private List<BooksData> itemlist;
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }





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



    public UserBooksAdapter(List<BooksData> itemList) {
        this.itemlist = itemList;
    }


    @Override
    public UserBooksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_books_layout , parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserBooksAdapter.MyViewHolder holder, final int position) {

      /* final BooksModel booksModel = itemlist.get(position);
               holder.book_name.setText(booksModel.getBook_name());
               holder.book_author.setText(booksModel.getBook_author());
               holder.book_description.setText(booksModel.getBook_author());
               holder.book_date_posted.setText(booksModel.getBook_date_posted());
               holder.book_exchange_donate.setText(booksModel.getBook_exchange_donate());

*/


        final BooksData booksModel = itemlist.get(position);
        holder.book_name.setText(booksModel.book_name);
        holder.book_author.setText(booksModel.book_author);
        holder.book_description.setText(booksModel.book_author);
        holder.book_date_posted.setText(booksModel.book_date_posted);
        holder.book_exchange_donate.setText(booksModel.book_exchange_donate);



        String image_url = booksModel.book_image;
         Context context = holder.book_image.getContext();



        Picasso.with(context).load(image_url)
                .resize(100,150)
                .onlyScaleDown()
                .centerCrop()
               .skipMemoryCache()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.book_image);




        /*Glide.with(context).load(image_url)

                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .crossFade()
                .into(holder.book_image);*/

      /*  final Uri imageUri = Uri.parse(image_url);
        holder.book_image.setImageURI(imageUri);
*/
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}