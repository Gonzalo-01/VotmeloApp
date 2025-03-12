package com.example.prueba3.data.remote;

import com.example.prueba3.domain.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyApi {
    @GET("v1/search")
    Call<SearchResponse> searchSong(
            @Query("q") String query,
            @Query("type") String type,
            @Query("limit") int limit,
            @Header("Authorization") String authorization
    );
}
