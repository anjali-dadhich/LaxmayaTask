package com.example.laxmayatask.callBack;


import com.example.laxmayatask.model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("v2/everything?q=tesla&from=2021-10-26&sortBy=publishedAt&apiKey=3630c124e17b469594767c894254bf0e")
    Call<NewsApiResponse> getNewsApiListData();
}
