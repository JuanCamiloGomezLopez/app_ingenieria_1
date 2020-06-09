package com.example.excelandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Menu_planos extends AppCompatActivity {

    Window window;
    ImageView cma,ctr,alm,tdc,tac,tcn,pal,pcb,tas,gen,gen_gen;
    Button nuevoplano;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="",camilo2="",area,user,password,contraseña1,pass;
    // String ip="192.168.102.61";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_planos);

        final String ip = getIntent().getExtras().getString("ipwifi");

        SharedPreferences sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        final String user= sharedPreferences.getString("user","No existe la información");
        String email= sharedPreferences.getString("email","No existe la información");

        //Toast.makeText(this, user+email, Toast.LENGTH_SHORT).show();

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        cma= findViewById(R.id.imageView1);
        ctr= findViewById(R.id.imageView2);
        alm= findViewById(R.id.imageView3);
        tdc= findViewById(R.id.imageView4);
        tac= findViewById(R.id.imageView5);
        gen= findViewById(R.id.imageView6);
        gen_gen= findViewById(R.id.imageView7);
        pal= findViewById(R.id.imageView8);
        pcb= findViewById(R.id.imageView9);

        rq= Volley.newRequestQueue(this);

        cma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","cma");
                startActivity(intent);
            }
        });
        ctr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","ctr");
                startActivity(intent);
            }
        });
        alm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","alm");
                startActivity(intent);
            }
        });
        tdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","tdc");
                startActivity(intent);
            }
        });
        tac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","tac");
                startActivity(intent);
            }
        });
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","gen");
                startActivity(intent);
            }
        });
        gen_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","gen-gen");
                startActivity(intent);
            }
        });
        pal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","palm");
                startActivity(intent);
            }
        });
        pcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_planos.this,CMA.class);
                intent.putExtra("ipwifi",ip);
                intent.putExtra("frente","pcb");
                startActivity(intent);
            }
        });







        nuevoplano=(Button) findViewById(R.id.boton_insertar_plano);

        nuevoplano.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

              // verificamos si tiene permisos


                verificar_permisos("http://"+ip+"/login/usuarios_permitidos.php?name="+user+"");

            }
        });

    }

    private void verificar_permisos(String URL1) {


        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final String ip = getIntent().getExtras().getString("ipwifi");

                SharedPreferences sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                final String user= sharedPreferences.getString("user","No existe la información");


                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        camilo1=(jsonObject.getString("name"));
                        area=(jsonObject.getString("area"));

                        if(area.equals("ingenieria")){
                            AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Menu_planos.this);
                            mydialog1.setTitle("Bienvenido " + camilo1 + " \nIngrese su Contraseña");

                            final EditText contraseña1 = new EditText(Menu_planos.this);
                            contraseña1.setInputType(InputType.TYPE_CLASS_TEXT);
                            mydialog1.setView(contraseña1);

                            mydialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pass = contraseña1.getText().toString();

                                    verificamoscontraseña("http://"+ip+"/login/usuarios_permitidos.php?name="+user+"");


                                }
                            });

                            mydialog1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            mydialog1.show();

                        }else {
                            AlertDialog.Builder mydialog = new AlertDialog.Builder(Menu_planos.this);
                            mydialog.setTitle("Usted no esta autorizado para realizar esta accion");

                            mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();

                                }
                            });

                            mydialog.show();
                        }


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder mydialog = new AlertDialog.Builder(Menu_planos.this);
                mydialog.setTitle("Usted no esta registrado en la base de datos");

                mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                mydialog.show();

                // Toast.makeText(getApplicationContext(), "error de conexion plano 1", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }

    private void verificamoscontraseña(String URL1) {

        final String ip = getIntent().getExtras().getString("ipwifi");

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        camilo1=(jsonObject.getString("name"));
                        password=(jsonObject.getString("pwd"));

                       if(password.equals(pass)){

                            Intent intent = new Intent(Menu_planos.this,Plano_Nuevo.class);
                            intent.putExtra("ipwifi",ip);
                            startActivity(intent);

                        }else {
                            Toast.makeText(Menu_planos.this, "Lo sentimos "+ camilo1+" Password incorrecto!!!", Toast.LENGTH_SHORT).show();
                        }


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
