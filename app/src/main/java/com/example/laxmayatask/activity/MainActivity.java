package com.example.laxmayatask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laxmayatask.R;
import com.example.laxmayatask.adapter.RecyclerAdapter;
import com.example.laxmayatask.callBack.ApiClient;
import com.example.laxmayatask.callBack.ApiInterface;
import com.example.laxmayatask.model.NewsApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchEditText;
    RecyclerAdapter adapter;
    public  static ApiInterface apiInterface;
    public static ArrayList<NewsApiResponse.Article> articlesListResponse = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        newsApiCall();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        searchItem();
    }

    private void searchItem() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString());
            }
        });
    }

    private void initializeRecyclerView() {
        try {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,
                            false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter = new RecyclerAdapter(MainActivity.this, articlesListResponse);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("RecyclerView: ", String.valueOf(e));
        }
    }

    private void newsApiCall() {
        final ProgressDialog pg = new ProgressDialog(this);
        pg.setMessage("Loading...");
        pg.show();
        pg.setCanceledOnTouchOutside(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NewsApiResponse> newsApiResponseCall = apiInterface.getNewsApiListData();
        newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {

            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {

                try {

                    if (response.code() == 200
                            && response.body() != null
                            && response.body().getStatus() != null
                            && response.body().getStatus().equalsIgnoreCase("ok")) {
                        articlesListResponse = (ArrayList<NewsApiResponse.Article>) response.body().getArticles();
                        Log.d("Laxmaya", "articlesListResponse == " + articlesListResponse);
                        initializeRecyclerView();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                    pg.dismiss();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    pg.dismiss();
                }
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                Log.d("Laxmaya Code","Error==" + t.toString());
                pg.dismiss();
            }

        });
    }

}