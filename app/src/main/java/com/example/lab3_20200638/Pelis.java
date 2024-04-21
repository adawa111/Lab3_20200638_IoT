package com.example.lab3_20200638;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pelis extends AppCompatActivity {


    private TextView attr1;

    private EditText attr1get;
    private EditText attr2get;
    private EditText attr3get;
    private EditText attr4get;
    private EditText attr5get;

    ApiService typicodeService;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pelis);

        showToast("Información sobre la Película");

       /* attr1 = findViewById(R.id.attr1);*/

        // Obtener el texto enviado desde MainActivity
        String searchText = getIntent().getStringExtra("searchText");
        String apikey= getIntent().getStringExtra("apikey");

        // Aquí puedes hacer la petición a la API con el texto obtenido y mostrar la información
        // Por ahora, mostraremos el texto ingresado en un TextView
        /*attr1.setText("Texto ingresado: " + searchText);*/



    }


    public void iraMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    public void pedir(String searchText, String apikey){
        typicodeService = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);

        typicodeService.obtenerPeliculas(searchText, apikey).enqueue(new Callback<Peliculas>() {
            @Override
            public void onResponse(@NonNull Call<Peliculas> call, @NonNull Response<Peliculas> response) {
                if (response.isSuccessful()) {
                    Peliculas peli = response.body();

                    if (peli != null) {
                        // Aquí puedes asignar los valores a tus EditText o TextView
                        attr1get.setText(peli.getTitle()); // Suponiendo que Peliculas tiene un método getTitle()
                    } else {
                        showToast("No se encontró la película");
                    }
                } else {
                    showToast("Error al obtener datos");
                }
            }


            @Override
            public void onFailure(@NonNull Call<Peliculas> call, Throwable t) {
                showToast("Error: " + t.getLocalizedMessage());
            }
        });
    }
}
