package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Plano_Nuevo extends AppCompatActivity {

    EditText codigo,titulo,rev,dibujante;
    Button registrar_plano;
    Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;

    String sfecha,subextraer="sdaflyg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano__nuevo);

        Toast.makeText(this, "El codigo debe estar escrito en minusculas", Toast.LENGTH_SHORT).show();

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        codigo=findViewById(R.id.codigo_plano);
        titulo=findViewById(R.id.nombre_plano);
        dibujante=findViewById(R.id.dibujante_plano);
        rev=findViewById(R.id.revision_plano);
        registrar_plano=findViewById(R.id.boton_registrar_plano);

        rq= Volley.newRequestQueue(this);
        final String ip = getIntent().getExtras().getString("ipwifi");


        registrar_plano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarplano("http://"+ip+"/login/registrar_plano.php");
            }
        });


    }

    private void registrarplano(String URL) {

            final String ip = getIntent().getExtras().getString("ipwifi");
            sfecha= DateFormat.getDateTimeInstance().format(new Date());

            subextraer=codigo.getText().toString().substring(4,7);



            if (codigo.getText().toString().isEmpty() | titulo.getText().toString().isEmpty() | rev.getText().toString().isEmpty() | dibujante.getText().toString().isEmpty() ) {
                Toast.makeText(getApplicationContext(), "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
            } else {

                 StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        limpiartextos();

                        Toast.makeText(Plano_Nuevo.this, "exito!! el plano se registro correctamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Plano_Nuevo.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();

                        parametros.put("codigo", codigo.getText().toString());
                        parametros.put("revision", rev.getText().toString());
                        parametros.put("nombre", titulo.getText().toString());
                        parametros.put("fecha", sfecha);
                        parametros.put("dibujante", dibujante.getText().toString());
                        parametros.put("frente", subextraer);
                        parametros.put("ruta_imagen", codigo.getText().toString());

                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }

    }

    private void limpiartextos() {

        codigo.setText("");
        rev.setText("");
        titulo.setText("");
        dibujante.setText("");
    }
}
