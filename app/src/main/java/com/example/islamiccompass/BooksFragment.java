package com.example.islamiccompass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;


public class BooksFragment extends Fragment {
    private RecyclerView booksRecView;
    View view;

    public BooksFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);

        booksRecView = view.findViewById(R.id.booksRecView);

        Map<Integer, Book> books = new HashMap<>();
        books.put(0, new Book("Book1", "This is book 1", "Ubaid"));
        books.put(1, new Book("Book2", "This is book 2", "Ubaid"));
        books.put(2, new Book("Book3", "This is book 3", "Ubaid"));
        books.put(3, new Book("Book4", "This is book 4", "Ubaid"));
        books.put(4, new Book("Book5", "This is book 5", "Ubaid"));

        BooksRecViewAdapter adapter = new BooksRecViewAdapter();
        adapter.setBooks(books);

        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }
}