package com.example.excelandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Codigo_barras extends AppCompatActivity {

    Button colaborador,ingresar_herramienta,eliminar_herramienta;
    TextView leer,usuario,cargo,codigo;

    ArrayList<herramientas> listdatos0;
    RecyclerView recycler;
    Adapter_herramientas adapter1;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    Window window;
    String descripccion="";
    String barras,barras1;
    String codigocurren;
    String sfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_barras);

        Toast.makeText(this, "Para empezar scanea un usuario", Toast.LENGTH_SHORT).show();

        colaborador= findViewById(R.id.scanear_colaborador);
        ingresar_herramienta= findViewById(R.id.ingresar_herramienta);
        eliminar_herramienta= findViewById(R.id.eliminar_herramienta);
        usuario = findViewById(R.id.text_usuario);
        cargo = findViewById(R.id.text_cargo);
        codigo = findViewById(R.id.text_codigo);



        final String ip = getIntent().getExtras().getString("ipwifi");

        final int opcion;
        rq= Volley.newRequestQueue(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        listdatos0 = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.recycler_herramientas);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        colaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

      ingresar_herramienta.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

               if (usuario.getText().toString().equals("XXX")){
                   Toast.makeText(Codigo_barras.this, "Debe tene un usuario aquien agregarle la herramienta", Toast.LENGTH_SHORT).show();
                   return;
               } else {

                   AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Codigo_barras.this);
                   mydialog1.setTitle("Ingrese descripccion de la herramienta");

                   final EditText descrip_herra = new EditText(Codigo_barras.this);
                   descrip_herra.setInputType(InputType.TYPE_CLASS_TEXT);
                   mydialog1.setView(descrip_herra);

                   mydialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                     descripccion = descrip_herra.getText().toString();
                          // Toast.makeText(Codigo_barras.this, descripccion, Toast.LENGTH_SHORT).show();
                           scanearingreso();
                       }
                   });

                   mydialog1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                       }
                   });

                   mydialog1.show();
               }
          }
      });

      eliminar_herramienta.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (usuario.getText().toString().equals("XXX")){
                  Toast.makeText(Codigo_barras.this, "Debe tene un usuario aquien eliminarle la herramienta", Toast.LENGTH_SHORT).show();
              } else{
                  scanearelimnar();
              }
          }
      });

        listdatos0 = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.recycler_herramientas);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager1);


    } // termina oncreate

    private void eliminarherramienta(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 Toast.makeText(Codigo_barras.this, "exito!! La herramienta se eliminó correctamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Codigo_barras.this,Main_principal.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Codigo_barras.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codigo_barras", barras1);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void scanearingreso() {
        IntentIntegrator intent1= new IntentIntegrator(this);
        intent1.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent1.setPrompt("Escaneando herramienta");
        intent1.setCameraId(0);
        intent1.setOrientationLocked(false);
        intent1.setBeepEnabled(false);
        intent1.setCaptureActivity(Capture_activity_portrait.class);
        intent1.setBarcodeImageEnabled(false);
        startActivityForResult(intent1.createScanIntent(),1);
    }

    private void scanearelimnar() {
        IntentIntegrator intent1= new IntentIntegrator(this);
        intent1.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent1.setPrompt("Escaneando herramienta");
        intent1.setCameraId(0);
        intent1.setOrientationLocked(false);
        intent1.setBeepEnabled(false);
        intent1.setCaptureActivity(Capture_activity_portrait.class);
        intent1.setBarcodeImageEnabled(false);
        startActivityForResult(intent1.createScanIntent(),2);
    }

    private void buscarusuarios (String URL){
        final String ip = getIntent().getExtras().getString("ipwifi");

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        usuario.setText((jsonObject.getString("colaborador")));
                        codigo.setText((jsonObject.getString("codigo")));
                        cargo.setText((jsonObject.getString("cargo")));
                        listdatos0.add(new herramientas((jsonObject.getString("herramienta")),(jsonObject.getString("fecha"))));
                        adapter1 = new Adapter_herramientas(Codigo_barras.this,listdatos0);
                        recycler.setAdapter(adapter1);

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
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

// empieza scaneo usuario *********************************************************
    private void escanear() {
        IntentIntegrator intent= new IntentIntegrator(Codigo_barras.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("Escaneando colaborador");
        intent.setCameraId(0);
        intent.setOrientationLocked(false);
        intent.setBeepEnabled(false);
        intent.setCaptureActivity(Capture_activity_portrait.class);
        intent.setBarcodeImageEnabled(false);
        startActivityForResult(intent.createScanIntent(),0);
       // intent.initiateScan();
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        final String ip = getIntent().getExtras().getString("ipwifi");

            if (requestCode == 0) {
                IntentResult result0 =  IntentIntegrator . parseActivityResult ( IntentIntegrator . REQUEST_CODE , resultCode, data);
                if (result0.getContents()!=null){
                    codigocurren=result0.getContents();
                    buscarusuarios("http://" + ip + "/login/usuarios_herramientas.php?codigo=" + result0.getContents() + "");
                } else {
                    Toast.makeText(this, "Se cancelo el scaneo del colaborador", Toast.LENGTH_SHORT).show();
                }
            }
            if (requestCode == 1) {
                IntentResult result1 =  IntentIntegrator . parseActivityResult ( IntentIntegrator . REQUEST_CODE , resultCode, data);
                if (result1.getContents()!=null) {
                    barras=result1.getContents();
                    registrarherramienta("http://"+ip+"/login/registrar_herramientas.php");
                } else {
                    Toast.makeText(this, "Se cancelo el scaneo de la herramienta", Toast.LENGTH_SHORT).show();
                }
            }
        if (requestCode == 2) {
            IntentResult result2 =  IntentIntegrator . parseActivityResult ( IntentIntegrator . REQUEST_CODE , resultCode, data);
            if (result2.getContents()!=null) {
                barras1=result2.getContents();

                buscarherramientaparaeliminar("http://" + ip + "/login/buscar_herramienta.php?codigo_barras=" + barras1 + "");


            } else {
                Toast.makeText(this, "Se cancelo el scaneo de la herramienta", Toast.LENGTH_SHORT).show();
            }
        }
        if (result!=null) {
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void buscarherramientaparaeliminar(String URL) {


        final String ip = getIntent().getExtras().getString("ipwifi");

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        if(jsonObject.getString("codigo_barras").equals(barras1)){

                            eliminarherramienta("http://" + ip + "/login/eliminar_herramientas.php?codigo_barras=" + barras1 + "");
                        }


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "El colaborador no tiene asiganada esta herramienta", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);









    }
    // termina scaneo usuario ****************************************************************************


    private void registrarherramienta(String URL) {
        final String ip = getIntent().getExtras().getString("ipwifi");

        //Date date = new Date();
       // @SuppressLint("SimpleDateFormat") SimpleDateFormat fecha = new SimpleDateFormat("d' de ' MMM 'de ' yyyy");
       // final String sfecha = fecha.format(date);

        sfecha= DateFormat.getDateTimeInstance().format(new Date());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Codigo_barras.this, "exito!! se cargo correctamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Codigo_barras.this,Main_principal.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Codigo_barras.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();

                    parametros.put("colaborador",usuario.getText().toString());
                    parametros.put("codigo",codigo.getText().toString());
                    parametros.put("cargo", cargo.getText().toString());
                    parametros.put("herramienta",descripccion);
                    parametros.put("fecha", sfecha);
                    parametros.put("codigo_barras", barras);
                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }
}
