package com.coursera.aad.capstoneapp.retrofit;

import com.coursera.aad.capstoneapp.models.History;
import com.coursera.aad.capstoneapp.models.Statistic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({
            "x-rapidapi-host: covid-193.p.rapidapi.com",
            "x-rapidapi-key: ff1417d9aamsh7ad772ab84380cdp15109fjsndd74c6e36b50"
    })
    @GET("countries")
    Call<ApiResponse<List<String>>> getCountries();

    @Headers({
            "x-rapidapi-host: covid-193.p.rapidapi.com",
            "x-rapidapi-key: ff1417d9aamsh7ad772ab84380cdp15109fjsndd74c6e36b50"
    })
    @GET("statistics")
    Call<ApiResponse<List<Statistic>>> getStatistics();

    @Headers({
            "x-rapidapi-host: covid-193.p.rapidapi.com",
            "x-rapidapi-key: ff1417d9aamsh7ad772ab84380cdp15109fjsndd74c6e36b50"
    })
    @GET("history?")
    Call<ApiResponse<List<History>>> getHistory(
            @Query("country") String country,
            @Query("day") String day
    );
}
