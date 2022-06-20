package com.example.islamiccompass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder> {

    private Map<Integer, Book> books = new HashMap<>();
    onItemClickListener onItemClickListener;


    public BooksRecViewAdapter(BooksRecViewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            Integer key = entry.getKey();
            Book value = entry.getValue();
            if(key == position){
//                System.out.println(key + " == " + position);
//                System.out.println("Book Name: " + value.getName());
                holder.bookName.setText(value.getName());
                holder.authorName.setText(value.getAuthor());
                holder.bookImage.setImageDrawable(value.getImage());
            } else {
               System.out.println("NOT MATCH!!!!");
            }
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(Map<Integer, Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public Book getBookItem(int position){
        //System.out.println(books.get(position));
        return books.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookName;
        private TextView authorName;
        private ImageView bookImage;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            authorName = itemView.findViewById(R.id.authorName);
            bookImage = itemView.findViewById(R.id.bookImage);

            //Can use the parent to make toast and onclick actions
            parent = itemView.findViewById(R.id.parent);

        }
    }

    public interface onItemClickListener{
        void onItemClick(View view , int position);
    }
}

