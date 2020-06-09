package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.excelandroid.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_login extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Window window;

    Button ingresar1;
    TextView pasar_regstro,email,password;
    RequestQueue rq;
    JsonRequest jrq;
    private ProgressBar barraprogreso;
    private ProgressDialog progressDialog;
    String ip="192.168.103.29";
    String nombre, email_usuario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));

        //FragmentManager fm= getSupportFragmentManager();
        //fm.beginTransaction().add(R.id.cointraint_activity_login,new sesion_fragment()).commit();


       // View vista = inflater.inflate(R.layout.fragment_sesion_fragment,container,false);
        ingresar1=findViewById(R.id.ingresar);
        pasar_regstro=findViewById(R.id.textView6);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        rq= Volley.newRequestQueue(this);

        barraprogreso=new ProgressBar(this);
        progressDialog = new ProgressDialog(this);

        ingresar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_login.this, Main_principal.class);
                startActivity(intent);

                if(email.getText().toString().equals("") | password.getText().toString().equals("")){
                    Toast.makeText(Activity_login.this, "Debe ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {

                   // iniciarseccion();
                }

            }
        });

        pasar_regstro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_login.this,Registrar_usuario.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(Activity_login.this, "No se ha enconctrado el usuario: ", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.dismiss();
        User usuario = new User();
        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObjectson=null;
        try {
            assert jsonArray != null;
            jsonObjectson=jsonArray.getJSONObject(0);
            usuario.setUser(jsonObjectson.optString("user"));
            usuario.setPwd(jsonObjectson.optString("pwd"));
            usuario.setName(jsonObjectson.optString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        nombre = usuario.getName();
        email_usuario=usuario.getUser();
        Toast.makeText(Activity_login.this, "Bienvenido " + nombre + " !!!", Toast.LENGTH_SHORT).show();


        guardarpreferencias();



        Intent intent = new Intent(Activity_login.this, Main_principal.class);
        intent.putExtra("email",usuario.getUser());
        intent.putExtra("nombre",usuario.getName());
        startActivity(intent);


    }

    private void iniciarseccion() {

        progressDialog.setMessage("Ingresando");
        progressDialog.show();
        String url="http://"+ip+"/login/sesion.php?user="+email.getText().toString()+
                "&pwd="+password.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    private void  guardarpreferencias(){

        SharedPreferences sharedPreferences = getSharedPreferences("credenciales",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("user", nombre);
        editor.putString("email",email_usuario);

        editor.commit();


    }

}
