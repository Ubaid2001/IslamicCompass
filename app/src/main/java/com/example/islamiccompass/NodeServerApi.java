package com.example.islamiccompass;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NodeServerApi {


    @GET("/books")
    Call<List<Book>> getBooks();

    @GET("/books/{Book_Name}")
    Call<ResponseBody> getBookPDF(@Path("Book_Name") String book_name);


}
