package com.example.pm2e1grupo44696663;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pm2e1grupo44696663.RestApi.MetodosRestApi;
import com.example.pm2e1grupo44696663.Tablas.ListaTablas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activitylista extends AppCompatActivity {

    EditText txtbusqueda;
    Button btneliminar,btnactualizarC,btnregresa2;

    ListView listLista;
    ArrayList<ListaTablas> ListLista;
    ArrayList <String> arrayContactos;
    Intent intent;
    //ListaTablas contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        mandarsolicitud();


        txtbusqueda=(EditText) findViewById(R.id.txtbusqueda);
        btneliminar=(Button) findViewById(R.id.btneliminar);
        btnactualizarC=(Button) findViewById(R.id.btnactualizarC);
        btnregresa2=(Button) findViewById(R.id.btnregresa2);
        listLista=(ListView) findViewById(R.id.listLista);
        intent = new Intent(getApplicationContext(), ActivityActualizar.class);


        btnactualizarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        btnregresa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crear intent para regresar a la actividad principal*/
                Intent intentregresa2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentregresa2);
            }
        });
    }


    private void mandarsolicitud(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MetodosRestApi.ApiListaGetUrl;
        ListLista = new ArrayList<ListaTablas>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject contactoObject = new JSONObject();
                    for (int i = 0; i < response.length(); i++) {
                        contactoObject = response.getJSONObject(i);

                        ListaTablas contact = new ListaTablas(Integer.valueOf(contactoObject.getInt("id")),
                                contactoObject.getString("nombre"),
                                String.valueOf(contactoObject.getInt("telefono")),
                                String.valueOf(contactoObject.getDouble("latitud")),
                                String.valueOf(contactoObject.getDouble("longitud")),
                                contactoObject.getString("video"));
                        ListLista.add(contact);
                        arrayContactos.add(contact.getNombre());
                        byte[] video = Base64.decode(contact.getVideo().getBytes(), Base64.DEFAULT);
                    }
                } catch (JSONException ex) {
                    System.out.println("Error" + ex.getMessage());
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en Response", "onResponse: " +  error.getMessage().toString() );
            }
        });
        // Add the request to the RequestQueue.
        queue.add(arrayRequest);
    }

}