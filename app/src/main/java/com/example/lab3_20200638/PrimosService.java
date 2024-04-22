package com.example.lab3_20200638;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrimosService {


    @GET("/primeNumbers")
    Call<List<Numeros>> obtenerPrimos(@Query("len") String len, @Query("order") String order);
}
