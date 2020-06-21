package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
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

public class imagen1 extends AppCompatActivity {

    ImageView imagenview1;
    private Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String rutaimagen="",rutanombre="";
    String urli ="http://192.168.1.56/";
    String urlcompleta="";

    String camilo[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen1);

        // cambiar color a barra inferior del cel
        this.window = getWindow();
        String primary = "#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));

        imagenview1=findViewById(R.id.imageView1);

        rq= Volley.newRequestQueue(this);

        final String ident = getIntent().getExtras().getString("identificador1");

        buscarimagen1("http://192.168.1.56/login/buscar_imagen_reunion.php?image_name="+ident+1+"");

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
                        urli = "http://192.168.1.56/";
                        urlcompleta=urli+rutaimagen;
                            Picasso.get().load(urlcompleta).into(imagenview1);
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
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
