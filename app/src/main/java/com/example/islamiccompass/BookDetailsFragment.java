package com.example.islamiccompass;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BookDetailsFragment extends Fragment {
    final static String DATA_RECEIVE = "data_received";
    private int notif_id = 1;
    private TextView book_detail_author;
    private TextView book_detail_name;
    private TextView book_detail_desc;
    private ImageView book_detail_Image;
    private ImageView download_Image;
    private TextView name_of_file;
    private TextView view_txt;

    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            hashmap -> {
                for (Map.Entry<String, Boolean> entry : hashmap.entrySet()) {
                    String permissionName = entry.getKey();
                    Boolean isGranted = entry.getValue();
                    System.out.println("Permission: " + permissionName + " isGranted: " + isGranted);

                    if (isGranted) {
                        Toast.makeText(getActivity(), " Storage Permission! IN USE!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Need Storage Permission!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    View view;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BookDetailsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BookDetailsFragment newInstance(String param1, String param2) {
//        BookDetailsFragment fragment = new BookDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_details, container, false);
        book_detail_name = view.findViewById(R.id.book_detail_name);
        book_detail_author = view.findViewById(R.id.book_detail_author);
        book_detail_Image = view.findViewById(R.id.book_detail_Image);
        book_detail_desc = view.findViewById(R.id.book_detail_desc);
        download_Image = view.findViewById(R.id.download_image);
        name_of_file = view.findViewById(R.id.pdffile);
        view_txt = view.findViewById(R.id.view_txt);

        Bundle args = getArguments();
//        Gson gson = new Gson();
//        MyObject = gson.fromJson(decodedString , MyObjectClass.class);

        Gson gson = new GsonBuilder()
                .create();

        String s1 = args.getString(DATA_RECEIVE);

        Book book = gson.fromJson(s1, Book.class);
//        System.out.println("S1: " + s1);


        System.out.println("book: " + book);
        int resId = book.getImageId();

        Picasso.get().load(R.mipmap.download1pdf)
                .resize(150, 150)
                .into(download_Image);


        String book_name = book.getName();
        if (book_name.contains("|")) {
            book_name = book_name.replace("|", "-");
        }

        String finalBook_name = book_name;
        download_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFilepath(finalBook_name);
            }
        });

        // getExternalStoragePublicDirectory for backup (NOT REALLY AS IT DOES NOT WORK EVEN THOUGH ITS API LEVEL 23)
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + book_name + ".pdf");
        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), File.separator + book_name + ".pdf");
        //System.out.println(file);

        view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDF(file);
            }
        });


        download_Image.setVisibility(View.VISIBLE);
        view_txt.setVisibility(View.GONE);

        if (args != null) {

            book_detail_name.setText(book.getName());
            book_detail_author.setText(book.getAuthor());
            book_detail_desc.setText(book.getDesc());
            name_of_file.setText(book.getName() + ".pdf");
            //book_detail_Image.setImageDrawable(drawable);
            Picasso.get().load(resId)
                    .resize(450, 800)
                    .into(book_detail_Image);
        }
        return view;
    }

    public void getFilepath(String book_name) {
        System.out.println("new book name: " + book_name);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.63:3001")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                NodeServerApi nodeServerApi = retrofit.create(NodeServerApi.class);
                //System.out.println("new book name: " + book_name);

                Call<ResponseBody> call = nodeServerApi.getBookPDF(book_name);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "server contacted and has file");


                            System.out.println(book_name);
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), book_name);
                            System.out.println("response body: " + response.body());

                            if (!writtenToDisk) {
                                Toast.makeText(getActivity(), "Download Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Download Completed", Toast.LENGTH_SHORT).show();
                            }

                            Log.d(TAG, "file download was a success? " + writtenToDisk);
                        } else {
                            Log.d(TAG, "server contact failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "error");

                    }
                });
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String book_name) {

        try {
            // todo change the file location/name according to your needs
            //File futureFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), File.separator + book_name + ".pdf");
            File futureFile = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + book_name + ".pdf");
            System.out.println(futureFile);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);

                    if (fileSizeDownloaded == fileSize) {
                        download_Image.setVisibility(View.GONE);
                        view_txt.setVisibility(View.VISIBLE);
                    }
                }


                outputStream.flush();

                return true;
            } catch (IOException e) {
                System.out.println("e.getMessage(): " + e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("e.getMessage(): " + e.getMessage());
            return false;
        }
    }

    public void openPDF(File pdfFile){
        Uri uri = FileProvider.getUriForFile(getContext(),"com.example.islamiccompass" + ".provider",pdfFile);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(uri, "application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(i);

//        try {
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getActivity(), "Need To Download PDF File", Toast.LENGTH_SHORT).show();
//        }
    }
}