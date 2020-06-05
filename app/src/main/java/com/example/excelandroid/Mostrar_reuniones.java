package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mostrar_reuniones extends AppCompatActivity {

    TextView t_plano_1, t_plano_2, t_plano_3, t_plano_4, t_plano_5, t_asist_1, t_asist_2, t_asist_3, t_asist_4, t_asist_5, obs, fec, hor,titulo;
    private Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="";
   // String ip="192.168.102.61";
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
        t_plano_4 = findViewById(R.id.tv_plano_4);
        t_plano_5 = findViewById(R.id.tv_plano_5);
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



        final String ident = getIntent().getExtras().getString("identificador1");
         Toast.makeText(this, ident, Toast.LENGTH_SHORT).show();

        buscarreuniones("http://"+ip+"/login/buscar_reuniones_identificador.php?identificador="+ident+"");

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
                        t_plano_4.setText(jsonObject.getString("plano_4"));
                        t_plano_5.setText(jsonObject.getString("plano_5"));
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
}