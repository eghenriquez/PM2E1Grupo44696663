package com.example.pm2e1grupo44696663;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pm2e1grupo44696663.RestApi.MetodosRestApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ActivityRegistrarC extends AppCompatActivity {

    private Camera camera;
    Button btntomarv,btnguardar,btnCsalvados,btnregresa1;
    EditText txtnombre,txttelefono,txtlatitud,txtlongitud;
    VideoView Videoclip;
    Uri videoUri;
    boolean retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_c);

        txtnombre=(EditText) findViewById(R.id.txtnombre);
        txttelefono=(EditText) findViewById(R.id.txttelefono);
        txtlatitud=(EditText) findViewById(R.id.txtlatitud);
        txtlongitud=(EditText) findViewById(R.id.txtlongitud);
        btntomarv=(Button) findViewById(R.id.btntomarv);
        btnguardar=(Button) findViewById(R.id.btnguardar);
        btnCsalvados=(Button) findViewById(R.id.btnCsalvados);
        Videoclip = findViewById(R.id.Videoclip);
        btnregresa1=(Button) findViewById(R.id.btnregresa1);

        txtlatitud.setEnabled(false);
        txtlongitud.setEnabled(false);


        btntomarv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisosCamera();
            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
                aggContacto();
            }
        });


        btnCsalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para llamar a la actividad de visualizar lista*/
                Intent intentCsalvados = new Intent(getApplicationContext(), Activitylista.class);
                startActivity(intentCsalvados);
            }
        });

        btnregresa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para regresar a la actividad principal*/
                Intent intentregresa1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentregresa1);
            }
        });

    }

    private void permisosCamera() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 101);

        } else {
            capturarVideo();
        }
    }

    private void capturarVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 102);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && resultCode == RESULT_OK) {
            videoUri = data.getData();

            // Configura el VideoView para reproducir el video capturado
            Videoclip.setVideoURI(videoUri);

            // Inicia la reproducción del video
            Videoclip.start();
        }
    }

    public boolean validar(){
        retorno= true;

        String nomb= txtnombre.getText().toString();
        String tele= txttelefono.getText().toString();
        String latd= txtlatitud.getText().toString();
        String lgtd= txtlongitud.getText().toString();

        if(nomb.isEmpty()){
            txtnombre.setError("DEBE INGRESAR EL NOMBRE");
            retorno = false;
        }
        if(tele.isEmpty()){
            txttelefono.setError("DEBE INGRESAR EL NUMERO TELEFONICO");
            retorno = false;
        }
        if(latd.isEmpty() && lgtd.isEmpty()){

            AlertDialog.Builder builder= new AlertDialog.Builder(ActivityRegistrarC.this);
            builder.setMessage("GPS no está activo");
            builder.setTitle("Ubicación");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //permisos();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            retorno = false;
        }

        return retorno;
    }

    private void aggContacto() {

        String url = MetodosRestApi.ApiCrearPostUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en Response", "onResponse: " +  error.getMessage().toString() );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre",txtnombre.getText().toString());
                parametros.put("telefono",txttelefono.getText().toString());
                parametros.put("latitud",txtlatitud.getText().toString());
                parametros.put("longitud",txtlongitud.getText().toString());
               // parametros.put("video",getString(Videoclip));
                return parametros;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

        if(retorno = false){
            Intent intent = new Intent(ActivityRegistrarC.this, ActivityRegistrarC.class);
            startActivity(intent);
            finish();
        }
    }
}