package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registrar_reunion extends AppCompatActivity {

    Spinner asistente_1,asistente_2,asistente_3,asistente_4,asistente_5;
    EditText plano_1,plano_2,plano_3,plano_4,plano_5;
    EditText textasistente_1,textasistente_2,textasistente_3,textasistente_4,textasistente_5;
    EditText resumen,lugar_reunion,hora_reunion;
    Button registrar_reunion;
    String arrbuscar[];
    Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
   // String ip="192.168.102.61";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_reunion);

        final String ip = getIntent().getExtras().getString("ipwifi");

       // int aleatorio= (int) Math.floor(Math.random()*(10-2)+2);

        //Toast.makeText(Registrar_reunion.this, aleatorio, Toast.LENGTH_SHORT).show();

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        registrar_reunion=findViewById(R.id.boton_reun);

        asistente_1=(Spinner)findViewById(R.id.spinner_asistente_1);
        asistente_2=(Spinner)findViewById(R.id.spinner_asistente_2);
        asistente_3=(Spinner)findViewById(R.id.spinner_asistente_3);
        asistente_4=(Spinner)findViewById(R.id.spinner_asistente_4);
        asistente_5=(Spinner)findViewById(R.id.spinner_asistente_5);

        String[] opciones = {"EMPRESA","CONSORCIO CCC", "INTERVENTORIA", "ASESORIA", "EPM","TERCERO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        asistente_1.setAdapter(adapter);
        asistente_2.setAdapter(adapter);
        asistente_3.setAdapter(adapter);
        asistente_4.setAdapter(adapter);
        asistente_5.setAdapter(adapter);

        plano_1=findViewById(R.id.plano_1);
        plano_2=findViewById(R.id.plano_2);
        plano_3=findViewById(R.id.plano_3);
        plano_4=findViewById(R.id.plano_4);
        plano_5=findViewById(R.id.plano_5);

        textasistente_1=findViewById(R.id.asistente_1);
        textasistente_2=findViewById(R.id.asistente_2);
        textasistente_3=findViewById(R.id.asistente_3);
        textasistente_4=findViewById(R.id.asistente_4);
        textasistente_5=findViewById(R.id.asistente_5);

        resumen=findViewById(R.id.resumen_reunion);
        hora_reunion=findViewById(R.id.hora_reunion);
        lugar_reunion=findViewById(R.id.tema_reunion);

        rq= Volley.newRequestQueue(this);

        registrar_reunion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          registrarreunion("http://"+ip+"/login/registrar_reuniones.php");
            }




        });

    }

    private void registrarreunion(String URL) {

        if (plano_1.getText().toString().isEmpty()&plano_2.getText().toString().isEmpty()&plano_3.getText().toString().isEmpty()&plano_4.getText().toString().isEmpty()&plano_5.getText().toString().isEmpty()) {

            Toast.makeText(Registrar_reunion.this, "Debe ingresar almenos un codigo de plano", Toast.LENGTH_SHORT).show();
            return;
        } else{

            // BUSCAMOS NUMERO DE

            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fecha = new SimpleDateFormat("d' de ' MMM 'de ' yyyy");
            final String sfecha = fecha.format(date);

            final int id1 = (int) (Math.random() * 10000 + 1000);
            final String id = String.valueOf(id1);

            final int id2 = (int) (Math.random() * 100000 + 10000);
            final String id3 = String.valueOf(id2);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Registrar_reunion.this, "exito!! se cargo correctamente", Toast.LENGTH_SHORT).show();

                   onBackPressed();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrar_reunion.this, "Error de conexión con la base de datos"+error.toString(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id", id3);
                    parametros.put("identificador", id);
                    parametros.put("plano_1",plano_1.getText().toString());
                    parametros.put("plano_2",plano_2.getText().toString());
                    parametros.put("plano_3",plano_3.getText().toString());
                    parametros.put("plano_4",plano_4.getText().toString());
                    parametros.put("plano_5",plano_5.getText().toString());
                    parametros.put("asistente_1",textasistente_1.getText().toString());
                    parametros.put("asistente_2",textasistente_2.getText().toString());
                    parametros.put("asistente_3",textasistente_3.getText().toString());
                    parametros.put("asistente_4",textasistente_4.getText().toString());
                    parametros.put("asistente_5",textasistente_5.getText().toString());
                    parametros.put("resumen",resumen.getText().toString());
                    parametros.put("fecha",sfecha);
                    parametros.put("lugar",lugar_reunion.getText().toString());
                    parametros.put("hora",hora_reunion.getText().toString());

                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }

    }
}

