package com.example.lab3_20200638;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Pelis extends AppCompatActivity {


    private TextView attr1;

    private EditText attr1get;
    private EditText attr2get;
    private EditText attr3get;
    private EditText attr4get;
    private EditText attr5get;

    private TextView namefilm;

    private TextView textplot;
    private TextView imdratget;

    private TextView finalscoreget;


    TypicodeService typicodeService;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pelis);

        showToast("Información sobre la Película");

       /* attr1 = findViewById(R.id.attr1);*/

        // Obtener el texto enviado desde MainActivity
        /*String searchText = getIntent().getStringExtra("searchText");
        String apikey= getIntent().getStringExtra("apikey");*/

        // Aquí puedes hacer la petición a la API con el texto obtenido y mostrar la información
        // Por ahora, mostraremos el texto ingresado en un TextView
        /*attr1.setText("Texto ingresado: " + searchText);*/
        Peliculas movie = (Peliculas) getIntent().getSerializableExtra("peli");
        TextView tomatoesget2;

        Log.d("serialno","TITULO:  "+movie.getTitle());
        attr1get = findViewById(R.id.attr1get);
        Log.d("serialno","ATTR1GET");
        attr2get = findViewById(R.id.attr2get);
        Log.d("serialno","ATTR2GET");
        attr3get = findViewById(R.id.attr3get);
        Log.d("serialno","ATTR3GET");
        attr4get = findViewById(R.id.attr4get);
        Log.d("serialno","ATTR4GET");
        attr5get = findViewById(R.id.attr5get);
        Log.d("serialno","ATTR5GET");
        namefilm = findViewById(R.id.namefilm);
        Log.d("serialno","NAMEFILM");
        textplot = findViewById(R.id.textplot);
        Log.d("serialno","TEXTPLOT");
        imdratget = findViewById(R.id.imdratget);
        Log.d("serialno","IMDRATGET");
        TextView tomatoesget3 = findViewById(R.id.tomatoesget2);
        Log.d("serialno","TOMATOESGET");
        finalscoreget = findViewById(R.id.finalscoreget);
        Log.d("serialno","FINALSCOREGET");

        attr1get.setText(movie.getDirector());
        attr2get.setText(movie.getActors());
        attr3get.setText(movie.getReleased());
        attr4get.setText(movie.getGenre());
        attr5get.setText(movie.getWriter());

        namefilm.setText(movie.getTitle());
        textplot.setText(movie.getPlot());
        List<Ratings> listaratings = movie.getRatings();
        imdratget.setText(listaratings.get(0).getValue());
        tomatoesget3.setText(listaratings.get(1).getValue());
        finalscoreget.setText(listaratings.get(2).getValue());


    }


    public void iraMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Button regresar;
    private CheckBox checkBox;
    public void enabledBack(View view){
        regresar = findViewById(R.id.regresar);
        checkBox = findViewById(R.id.checkBox);
        if (checkBox.isChecked()){
            regresar.setEnabled(true);
        }else {
            regresar.setEnabled(false);
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

}
