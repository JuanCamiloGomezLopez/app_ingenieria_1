package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.excelandroid.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Registrar_usuario extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    Button registrar1;
    Window window;
    EditText email,name1,password,password_repeat;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="",camilo2="";
   // String ip="192.168.102.61";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        final String ip = getIntent().getExtras().getString("ipwifi");

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        rq= Volley.newRequestQueue(this);
        registrar1= findViewById(R.id.registrar_usuario);

        name1=findViewById(R.id.reg_name);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        password_repeat=findViewById(R.id.reg_repeat_password);

        registrar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().isEmpty()|password_repeat.getText().toString().isEmpty()|name1.getText().toString().isEmpty()|email.getText().toString().isEmpty()){
                    Toast.makeText(Registrar_usuario.this, "Debe llenar todos los datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailinput=email.getText().toString();

                if (!emailinput.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){

                    registrarusuario("http://"+ip+"/login/registrar_usuarios.php");

                } else {
                    Toast.makeText(getApplication(), "El email es invalido", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void limpiarcajas() {

        name1.setText("");
        email.setText("");
        password.setText("");
        password_repeat.setText("");
    }


    private void registrarusuario(String URL) {

        final int id100 = (int) (Math.random() * 1000 + 10);
        final String id10 = String.valueOf(id100);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getApplicationContext(), "Bienvenido: "+name1.getText().toString()+" ya puedes iniciar seccion.", Toast.LENGTH_SHORT).show();
                    limpiarcajas();

                    onBackPressed();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrar_usuario.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "No se pudo registrar el usuario : "+error.toString()+name1.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            })
                {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id", id10);
                    parametros.put("name", name1.getText().toString());
                    parametros.put("user",email.getText().toString());
                    parametros.put("pwd", password.getText().toString());
                    parametros.put("area", password_repeat.getText().toString());
                    parametros.put("color", "500");

                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
