package com.example.pm2e1grupo44696663;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityActualizar extends AppCompatActivity {

    Button btnactVideo,btnactcontacto,btnregresa6,btnregresa5;
    EditText txtnombreact,txttelefonoact,txtlatitudact,txtlongitudact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        txtnombreact=(EditText) findViewById(R.id.txtnombreact);
        txttelefonoact=(EditText) findViewById(R.id.txttelefonoact);
        txtlatitudact=(EditText) findViewById(R.id.txtlatitudact);
        txtlongitudact=(EditText) findViewById(R.id.txtlongitudact);
        btnactVideo=(Button) findViewById(R.id.btnactVideo);
        btnactcontacto=(Button) findViewById(R.id.btnactcontacto);
        btnregresa5=(Button) findViewById(R.id.btnregresa5);
        btnregresa6=(Button) findViewById(R.id.btnregresa6);



        btnregresa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para llamar a la actividad de visualizar lista*/
                Intent intentregresa5 = new Intent(getApplicationContext(), Activitylista.class);
                startActivity(intentregresa5);
            }
        });
        btnregresa6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para regresar a la actividad principal*/
                Intent intentregresa6 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentregresa6);
            }
        });
    }
}