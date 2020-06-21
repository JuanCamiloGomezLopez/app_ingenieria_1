package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Mostrar_reuniones extends AppCompatActivity {

    TextView t_plano_1, t_plano_2, t_plano_3, t_plano_4, t_plano_5, t_asist_1, t_asist_2, t_asist_3, t_asist_4, t_asist_5, obs, fec, hor,titulo;
    private Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="";
    // String ip="192.168.102.61";
    ImageView imagen1,imagen2,imagen3,imagen4;

    String rutaimagen="",rutanombre="";
    String urli ="http://192.168.1.56/";
    String urlcompleta="";

    String camilo[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_reuniones);

        final String ip = getIntent().getExtras().getString("ipwifi");

        // cambiar color a barra inferior del cel
        this.window = getWindow();
        String primary = "#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        t_plano_1 = findViewById(R.id.tv_plano_1);
        t_plano_2 = findViewById(R.id.tv_plano_2);
        t_plano_3 = findViewById(R.id.tv_plano_3);

        t_asist_1 = findViewById(R.id.tv_asistentes_1);
        t_asist_2 = findViewById(R.id.tv_asistentes_2);
        t_asist_3 = findViewById(R.id.tv_asistentes_3);
        t_asist_4 = findViewById(R.id.tv_asistentes_4);
        t_asist_5 = findViewById(R.id.tv_asistentes_5);
        obs = findViewById(R.id.tv_resumen);
        fec = findViewById(R.id.tv_fecha);
        hor = findViewById(R.id.tv_hora);
        titulo = findViewById(R.id.titulo_mostrar_reunion);

        rq= Volley.newRequestQueue(this);

        imagen1=findViewById(R.id.imageView14);
        imagen2=findViewById(R.id.imageView15);
        imagen3=findViewById(R.id.imageView16);
        imagen4=findViewById(R.id.imageView17);

        final String ident = getIntent().getExtras().getString("identificador1");


        buscarimagen1("http://192.168.1.56/login/buscar_imagen_reunion.php?image_name="+ident+1+"");
        buscarimagen1("http://192.168.1.56/login/buscar_imagen_reunion.php?image_name="+ident+2+"");
        buscarimagen1("http://192.168.1.56/login/buscar_imagen_reunion.php?image_name="+ident+3+"");
        buscarimagen1("http://192.168.1.56/login/buscar_imagen_reunion.php?image_name="+ident+4+"");

        buscarreuniones("http://"+ip+"/login/buscar_reuniones_identificador.php?identificador="+ident+"");


        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mostrar_reuniones.this, com.example.excelandroid.imagen1.class);

                intent.putExtra("identificador1",ident);
                startActivity(intent);

            }
        });



    }
    private void buscarreuniones(String URL) {

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        t_plano_1.setText(jsonObject.getString("plano_1"));
                        t_plano_2.setText(jsonObject.getString("plano_2"));
                        t_plano_3.setText(jsonObject.getString("plano_3"));
                       // t_plano_4.setText(jsonObject.getString("plano_4"));
                       // t_plano_5.setText(jsonObject.getString("plano_5"));
                        t_asist_1.setText(jsonObject.getString("asistente_1"));
                        t_asist_2.setText(jsonObject.getString("asistente_2"));
                        t_asist_3.setText(jsonObject.getString("asistente_3"));
                        t_asist_4.setText(jsonObject.getString("asistente_4"));
                        t_asist_5.setText(jsonObject.getString("asistente_5"));
                        obs.setText(jsonObject.getString("resumen"));
                        titulo.setText(jsonObject.getString("fecha"));
                        fec.setText(jsonObject.getString("lugar"));
                        hor.setText(jsonObject.getString("hora"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



    private void buscarimagen1(String URL1) {
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;



                camilo=new String[response.length()];

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);

                        rutaimagen=(jsonObject.getString("image_path"));
                        rutanombre=(jsonObject.getString("image_name"));

                        String subextraer1 = rutanombre.substring(4,5);

                        camilo[i]=subextraer1;


                        //Toast.makeText(Mostrar_reuniones.this, subextraer1, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Mostrar_reuniones.this, Arrays.toString(camilo), Toast.LENGTH_SHORT).show();

                        urli = "http://192.168.1.56/";
                        urlcompleta=urli+rutaimagen;




                        if (subextraer1.equals("1")){
                            Picasso.get().load(urlcompleta).into(imagen1);
                        }
                        if (subextraer1.equals("2")){
                            Picasso.get().load(urlcompleta).into(imagen2);
                        }
                        if (subextraer1.equals("3")){
                            Picasso.get().load(urlcompleta).into(imagen3);
                        }
                        if (subextraer1.equals("4")){
                            Picasso.get().load(urlcompleta).into(imagen4);
                        }



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getApplicationContext(), "error de conexion plano 1", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}