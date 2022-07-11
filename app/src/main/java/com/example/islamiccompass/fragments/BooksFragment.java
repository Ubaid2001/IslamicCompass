package com.example.islamiccompass.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamiccompass.recycleview.BooksRecViewAdapter;
import com.example.islamiccompass.helper.DataPassListener;
import com.example.islamiccompass.serverapi.NodeServerApi;
import com.example.islamiccompass.R;
import com.example.islamiccompass.model.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BooksFragment extends Fragment {

    DataPassListener mCallback;


    private RecyclerView booksRecView;
    private List<String> bookName = new ArrayList<>();
    private  List<String>  bookDesc = new ArrayList<>();
    private  List<String>  bookAuthor = new ArrayList<>();
    private  List<Integer>  bookImageId = new ArrayList<>();
    private BooksRecViewAdapter adapter;

    View view;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (DataPassListener) context;

        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement DataPassListener");
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);

        booksRecView = view.findViewById(R.id.booksRecView);

        // The adapter can be made inside this method or at the top of the class before the
        //constructor, either methods works and will not display an error message.
        //BooksRecViewAdapter adapter = new BooksRecViewAdapter();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        adapter = new BooksRecViewAdapter(new BooksRecViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                System.out.println("Item position: " + position);
//                System.out.println("Item view: " + view.getId());

                    Book book = adapter.getBookItem(position);
                        String name = book.getName();
                        String desc = book.getDesc();
                        String author = book.getAuthor();

                        int imageId = book.getImageId();
                        System.out.println(imageId);


                Book book2 = new Book(name, desc, author, null, imageId);
                        System.out.println(book2);

                        String jsonString =  gson.toJson(book2);
                        mCallback.passData(jsonString);


            }
        });

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.0.63:3001")
                .baseUrl("http://ec2-3-8-201-100.eu-west-2.compute.amazonaws.com:3001")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        NodeServerApi nodeServerApi = retrofit.create(NodeServerApi.class);

        Call<List<Book>> call = nodeServerApi.getBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                System.out.println("The Android is connected to the Server!!!");
                List<Book> booksList = response.body();

                Map<Integer, Book> books = new HashMap<>();
                int num = 0;

                for (Book book : booksList) {

                    bookName.add(book.getName());
                    bookDesc.add(book.getDesc());
                    bookAuthor.add(book.getAuthor());
                    bookImageId.add(book.getImageId());

                    String name = book.getName();
                    String author = book.getAuthor();
                    String desc = book.getDesc();
                    int resId = book.getImageId();

                    books.put(num, new Book(name, desc, author, getContext().getDrawable(resId), resId));

                            num++;

                }
                adapter.setBooks(books);

//                booksRecView.setAdapter(adapter);
//                booksRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }


            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

}