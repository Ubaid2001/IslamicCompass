package com.example.islamiccompass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder> {

    private Map<Integer, Book> books = new HashMap<Integer, Book>();
    public BooksRecViewAdapter() {
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
        //System.out.println("Book: " + books.get(position).getName());
        //holder.bookName.setText(books.get(position).getName());
        for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            Integer key = entry.getKey();
            Book value = entry.getValue();
            System.out.println("key " + key + " value.getName " + value.getName() + " position " + position);
            if(key == position){
                System.out.println(key + " == " + position);
                System.out.println("Book Name: " + value.getName());
                holder.bookName.setText(value.getName());
            }
            position += 1;
            System.out.println("NOT MATCH!!!!");
        }

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(Map<Integer, Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
        }
    }
}
