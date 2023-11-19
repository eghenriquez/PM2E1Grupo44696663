package com.example.pm2e1grupo44696663;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnsalvarc,btnlista,btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Asignacion de variables a botones del layout */
        btnsalvarc=(Button) findViewById(R.id.btnsalvarc);
        btnlista=(Button) findViewById(R.id.btnlista);
        btnsalir=(Button) findViewById(R.id.btnsalir);

        btnsalvarc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*crear intent para llamar a la actividad salvar contacto*/
                Intent intentsalvar = new Intent(getApplicationContext(), ActivityRegistrarC.class);
                startActivity(intentsalvar);
            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para llamar a la actividad de visualizar lista*/
                Intent intentlista = new Intent(getApplicationContext(), Activitylista.class);
                startActivity(intentlista);
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para llamar a la actividad de salir del programa*/
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}