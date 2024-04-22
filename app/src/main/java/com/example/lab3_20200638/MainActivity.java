package com.example.lab3_20200638;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {




    TypicodeService typicodeService;

    PrimosService primosService;
    UUID uuid;

    List<Numeros> listanumeros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);




        if (isConnected()) {
            showToast("Success Toast");
        } else {
            showToast("Error Toast");
        }
        showToast("Menú Principal");

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeService.class);

        primosService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrimosService.class);

        String len = "999";
        String order = "1";
        if(isConnected()){
            Log.d("serialno","SE HA CONECTADO CON ÉXITO A INTERNET");

            primosService.obtenerPrimos(len,order).enqueue(new Callback<List<Numeros>>() {
                @Override
                public void onResponse(Call<List<Numeros>> call, Response<List<Numeros>> response) {
                    Log.d("serialno","PETICIÓN HECHA");
                    if(response.isSuccessful()){
                        Log.d("serialno","Entrando a Call");
                        try {
                           listanumeros = response.body();
                            assert listanumeros != null;
                            if(listanumeros != null){
                                Log.d("serialno","number:" + listanumeros.get(0).getNumber());
                                Log.d("serialno", "order: " + listanumeros.get(0).getOrder());


                                UUID uuid = UUID.randomUUID();

                                ArrayList<Integer> listnumber = new ArrayList<>();
                                for (Numeros listanumero : listanumeros) {
                                    listnumber.add(listanumero.getNumber());  // Añade el número a la lista
                                }

                                int[] intArray = new int[listnumber.size()];
                                for (int i = 0; i < listnumber.size(); i++) {
                                    intArray[i] = listnumber.get(i);
                                }

                                Data dataBuilder = new Data.Builder()
                                        .putIntArray("listanumeros", intArray)  // Utiliza putIntegerArrayList para pasar la lista
                                        .build();

                                WorkRequest workRequest = new OneTimeWorkRequest.Builder(ContadorPrimoWorker.class)
                                        .setId(uuid)
                                        .setInputData(dataBuilder)
                                        .build();
                                WorkManager.getInstance(MainActivity.this)
                                        .enqueue(workRequest);
                            }else {
                                showToast("No se han encontrado números primos en el rango");
                            }
                        } catch (Exception e) {
                            /*showToast("Error: " + e.getMessage());*/
                            Log.d("serialno","Error: " +e.getMessage());
                        }
                    }else {
                        showToast("Ha ocurrido un error :(");
                    }
                }

                @Override
                public void onFailure(Call<List<Numeros>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }


    }

    public void iraPrimos(View view) {
        Intent intent = new Intent(this, Primos.class);
        intent.putExtra("listanumeros", (Serializable) listanumeros);

        intent.putExtra("uuid", (Serializable) uuid);

        startActivity(intent);
    }

    private EditText editTextText;

    public void iraPelis(View view) {
        editTextText = findViewById(R.id.editTextText);
        String idpeliculas =  editTextText.getText().toString().trim();
        String apikey = "bf81d461";
        Intent intent = new Intent(this, Pelis.class);
        if(isConnected()){
            Log.d("serialno","SE HA CONECTADO CON ÉXITO A INTERNET");
            typicodeService.obtenerPeliculas(apikey,idpeliculas).enqueue(new Callback<Peliculas>() {

                @Override
                public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                    Log.d("serialno","PETICIÓN HECHA");
                    if(response.isSuccessful()){
                        Log.d("serialno","Entrando a Call");
                        try {
                            Peliculas film = response.body();
                            assert film != null;
                            if(film.getResponse().equals("True")){
                                Log.d("serialno","Título:" + film.getTitle());
                                Log.d("serialno", "Response: " + film.getResponse());
                                intent.putExtra("peli", (Serializable) film);
                                startActivity(intent);
                            }else {
                                showToast("No se ha encontrado la película");
                            }
                        } catch (Exception e) {
                            /*showToast("Error: " + e.getMessage());*/
                            Log.d("serialno","Error: " +e.getMessage());
                        }
                    }else {
                        showToast("Ha ocurrido un error :(");
                    }
                }

                @Override
                public void onFailure(Call<Peliculas> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        }

    }


    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true;
                }
            }
        }

        return false;
    }/* ayuda IA*/

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    /*ayuda IA*/

}
