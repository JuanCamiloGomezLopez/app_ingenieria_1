package com.example.excelandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notificaciones extends AppCompatActivity {

    Button enviarnotificacion,enaviartodos;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="";
    String ip="192.168.101.185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        enviarnotificacion = findViewById(R.id.button5);
        enaviartodos=findViewById(R.id.enviar);
        rq= Volley.newRequestQueue(this);

        String usuariocurrent="camilo1";

        buscartokencurrent("http://"+ip+"/login/buscar_tokens.php?usuario="+usuariocurrent+"");

        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Notificaciones.this, "suscrito al enviar a todos", Toast.LENGTH_SHORT).show();
            }
        });


        enaviartodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamaratopico();
            }
        });


        enviarnotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarespecifico();
            }
        });

    }

    private void buscartokencurrent(String URL1) {

        JsonArrayRequest jsonArrayRequest1=new JsonArrayRequest(URL1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        camilo1=(jsonObject.getString("token"));
                        //Toast.makeText(Notificaciones.this, camilo1, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                 Toast.makeText(getApplicationContext(), "error de conexion 52", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest1);
    }


    private void llamarespecifico(){

        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json=new JSONObject();

        try {
            String token=camilo1;
           // String token="dURIUp4dTcaI9xcfQnrvGX:APA91bEdMxWyFDHr_uGWFEao3t-lJXBCmMFXb89Y7Lxv7wePuKbylDeo6e9IeSAokI6XN_-jBUwOtz40FmYNcYTzTkZW8kFnXWHjDaynZIiE68hF45Nl5uBGVw2UXAOismkAyqRGv6gJ";
            json.put("to",token);
            JSONObject notificacoin = new JSONObject();
            notificacoin.put("titulo", "REUNION PLANEADA");
            notificacoin.put("detalle", "Pulse aqui para confirmar asistencia");

            json.put("data",notificacoin);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                @Override
                public Map<String, String> getHeaders(){

                    Map <String,String> header = new HashMap<>();

                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAupVSqvU:APA91bGrCW0R54HLb8MdxBk0moiRrKQpMToQwTRhU1geuJ9JX_gEuSSLCKpShYCEmI_tHCdr0Xt9_3a9KXCeCBTF1UjW8NTovObXN5Hvq-XtoaJgu_2gmYQ-d2UqA0tn8V9NmPYHuTb6");
                    return header;
                }
            };

            myrequest.add(request);

            }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void llamaratopico(){

        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json=new JSONObject();

        try {
           // String token=camilo1;
            // String token="dURIUp4dTcaI9xcfQnrvGX:APA91bEdMxWyFDHr_uGWFEao3t-lJXBCmMFXb89Y7Lxv7wePuKbylDeo6e9IeSAokI6XN_-jBUwOtz40FmYNcYTzTkZW8kFnXWHjDaynZIiE68hF45Nl5uBGVw2UXAOismkAyqRGv6gJ";
            json.put("to","/topics/"+"enviaratodos");

            JSONObject notificacoin = new JSONObject();
            notificacoin.put("titulo", "REUNION PLANEADA");
            notificacoin.put("detalle", "Pulse aqui para confirmar asistencia");

            json.put("data",notificacoin);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                @Override
                public Map<String, String> getHeaders(){

                    Map <String,String> header = new HashMap<>();

                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAupVSqvU:APA91bGrCW0R54HLb8MdxBk0moiRrKQpMToQwTRhU1geuJ9JX_gEuSSLCKpShYCEmI_tHCdr0Xt9_3a9KXCeCBTF1UjW8NTovObXN5Hvq-XtoaJgu_2gmYQ-d2UqA0tn8V9NmPYHuTb6");
                    return header;
                }
            };

            myrequest.add(request);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }
}
