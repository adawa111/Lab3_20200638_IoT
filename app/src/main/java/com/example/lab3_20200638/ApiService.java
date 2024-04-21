package com.example.lab3_20200638;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    /* https://www.omdbapi.com/?apikey=bf81d461&i=*/
    @GET("/")
    Call<List<Peliculas>> obtenerPeliculas(@Query("busqueda") String busqueda,
    @Query("apikey") String apikey);
}
