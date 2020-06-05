package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vista_pdf extends AppCompatActivity  {
    ContentValues val = new ContentValues();
    PDFView visor;
    EditText resumenreunion;
    ListView list,list_reuniones;
    String arrobs[], arrcodigo[],camilo[],camilo_reuniones[];
    TextView titulo_registrar_reunion,fecha1;
    SQLiteDatabase db;
    Button registrar_obs,reunion;
    private Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="",camilo2="";
   // String ip="192.168.102.61";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color


        setContentView(R.layout.activity_vista_pdf);
        list=findViewById(R.id.list_view);
        list_reuniones=findViewById(R.id.list_view_reuniones);
        registrar_obs = findViewById(R.id.boton_obs);
        reunion = findViewById(R.id.boton_reun);
        resumenreunion = findViewById(R.id.resumen_reunion);
        titulo_registrar_reunion=findViewById(R.id.archivo_planos);
        fecha1=findViewById(R.id.text_cantidad);
        rq= Volley.newRequestQueue(this);


        final String recuperar = Objects.requireNonNull(getIntent().getExtras()).getString("dato");
        final String fecha = getIntent().getExtras().getString("pos");
        final String numero_plano = getIntent().getExtras().getString("pos1");
        final String ip = getIntent().getExtras().getString("ipwifi");

         // primero  buscamos las observaciones que se hayan registrado e el plano actual

        buscarobservaciones("http://"+ip+"/login/buscar_observaciones.php?codigo="+recuperar+"");



        // ahora buscamos las reuniones que se hayan registrado


        buscarreuniones("http://"+ip+"/login/buscar_reuniones.php?plano_1="+recuperar+"");

        buscarreuniones("http://"+ip+"/login/buscar_reuniones_plano_2.php?plano_2="+recuperar+"");

        // registramos observaciones undiendo el boton

        registrar_obs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            registrarobservaciones("http://"+ip+"/login/registrar_observacion.php");


            }
        });

        // boton para ir a activity a registrar reunion

        reunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vista_pdf.this, Registrar_reunion.class);
                intent.putExtra("ipwifi",ip);
                // intent.putExtra("dato", recuperar);
                startActivity(intent);
            }
        });

        // boton para mostrar las observaciones en la nueva actividad

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String recuperar = Objects.requireNonNull(getIntent().getExtras()).getString("dato");

                // aqui tenemos que subtraer los caracteres del codigo de la observacion para poderla buscar en la otra activity ********************

                String extraer1= (list.getItemAtPosition(position).toString());
                String subextraer1 = extraer1.substring(0,4);
              //  Toast.makeText(Vista_pdf.this, subextraer1, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Vista_pdf.this, Mostrar_observaciones.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("identificador2",subextraer1);
                intent.putExtra("plano1",recuperar);
                startActivity(intent);
            }
        });


        list_reuniones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // aqui tenemos que subtraer los caracteres del codigo de la observacion para poderla buscar en la otra activity ********************
                String extraer1= (list_reuniones.getItemAtPosition(position).toString());
                String subextraer1 = extraer1.substring(0,4);
               // Toast.makeText(Vista_pdf.this, subextraer1, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Vista_pdf.this, Mostrar_reuniones.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("identificador1",subextraer1);
                intent.putExtra("plano1",recuperar);
                startActivity(intent);
            }
        });










        // recuperamos la posicion del recycler y el codigo del plano para llamar al pdf

        visor = (PDFView) findViewById(R.id.pdfView1);
        visor.fromAsset(recuperar + ".pdf").load();

        // ampliamos el pdf enviandolo a una sola activity
        visor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vista_pdf.this, Mostrar_pdf.class);
                intent.putExtra("dato", recuperar);
                startActivity(intent);
            }
        });

        titulo_registrar_reunion.setText(numero_plano);
        fecha1.setText(fecha);



    }// finaliza on create

    // mertodo para buscar reuniones en plano 1

    private void buscarreuniones(String URL1) {

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                camilo=new String[response.length()];
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        camilo1=(jsonObject.getString("identificador"));
                        camilo2=(jsonObject.getString("resumen"));

                        camilo[i]=camilo1+" "+camilo2;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                ArrayAdapter<String> add=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,camilo);
                list_reuniones.setAdapter(add);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


               // Toast.makeText(getApplicationContext(), "error de conexion plano 1", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



    // metodo para registrar observaciones

    private void registrarobservaciones(String URL) {
        final String ip = getIntent().getExtras().getString("ipwifi");


        if (resumenreunion.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una observacion", Toast.LENGTH_SHORT).show();
        } else {

            final int id1 = (int) (Math.random() * 10000 + 1000);
            final String id = String.valueOf(id1);

            final int id100 = (int) (Math.random() * 1000000 + 1000);
            final String id10 = String.valueOf(id100);

            final String recuperar = Objects.requireNonNull(getIntent().getExtras()).getString("dato");

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    resumenreunion.setText("");
                    buscarobservaciones("http://"+ip+"/login/buscar_observaciones.php?codigo=" + recuperar + "");

                    Toast.makeText(Vista_pdf.this, "exito!! se cargo correctamente", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Vista_pdf.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id", id10);
                    parametros.put("identificador", id);
                    parametros.put("observacion",id+" USUARIO "+resumenreunion.getText().toString());
                    parametros.put("codigo", recuperar);
                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    // metodo para buscar observaciones y meter en el list view correspondiente

    private void buscarobservaciones (String URL){

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                arrobs=new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                       String camilo1=(jsonObject.getString("observacion"));

                        arrobs[i]=camilo1;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
              ArrayAdapter<String> add=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrobs);
             list.setAdapter(add);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(getApplicationContext(), "error de conexion observaciones", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
