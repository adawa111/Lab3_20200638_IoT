package com.example.lab3_20200638;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Primos extends AppCompatActivity {





    List<Numeros> listanumeros = new ArrayList<>();
    TextView numpri;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primos);
        numpri = findViewById(R.id.numpri);

        showToast("Contador de Primos");


        listanumeros = (List<Numeros>) getIntent().getSerializableExtra("listanumeros");
        UUID uuid = (UUID) getIntent().getSerializableExtra("uuid");

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(uuid)
                .observe(this,workInfo -> {
                    if(workInfo!= null){
                        Data progress = workInfo.getProgress();
                        int contador = progress.getInt("primo",0);
                        Log.d("serialno1","progresosososos: "+contador);
                        numpri.setText(String.valueOf(contador));
                    }else {
                        Log.d("serialno1","work info == null");
                    }
                });



    }

    public void buscador(){
        EditText buscar = findViewById(R.id.editTextText2);
        numpri.setText(listanumeros.get(Integer.parseInt(String.valueOf(buscar.getText()))).getNumber());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
