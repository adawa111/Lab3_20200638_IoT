package com.example.lab3_20200638;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TypicodeService {


    /* https://www.omdbapi.com/?apikey=bf81d461&i=*/
    @GET("/")
    Call<Peliculas> obtenerPeliculas(@Query("apikey") String apikey,@Query("i") String busqueda);




}
