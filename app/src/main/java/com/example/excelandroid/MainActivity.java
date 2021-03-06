package com.example.excelandroid;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

 
public class MainActivity extends AppCompatActivity {

    ArrayList<blog> listdatos;
    RecyclerView recycler;
    Adapter adapter;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;

    Button nuevoplano;

    private Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String ip = getIntent().getExtras().getString("ipwifi");

        rq= Volley.newRequestQueue(this);

        nuevoplano=(Button) findViewById(R.id.boton_insertar_plano);

        nuevoplano.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String current_usuario="camilo1";
                String usuario1="camilo";
                String usuario2 ="german";

                AlertDialog.Builder mydialog = new AlertDialog.Builder(MainActivity.this);
                mydialog.setTitle("Utsed no esta autorizado para realizar esta accion");

               mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                mydialog.show();

                    AlertDialog.Builder mydialog1 = new AlertDialog.Builder(MainActivity.this);
                    mydialog1.setTitle("Bienvenido " + current_usuario + " \nIngrese su Contraseña");

                    final EditText contraseña1 = new EditText(MainActivity.this);
                    contraseña1.setInputType(InputType.TYPE_CLASS_TEXT);
                    mydialog1.setView(contraseña1);

                    mydialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String pass = contraseña1.getText().toString();

                            Toast.makeText(MainActivity.this, "la contraseña es" + pass, Toast.LENGTH_SHORT).show();
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
        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.text);

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

        listdatos = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        buscarobservaciones("http://"+ip+"/login/planos/planos_cma.php?frente=cma");

    } // oncreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    // termina oncreateoptionsmenu


    private void buscarobservaciones (String URL){

        final String ip = getIntent().getExtras().getString("ipwifi");

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        listdatos.add(new blog((jsonObject.getString("codigo")),(jsonObject.getString("nombre"))
                                ,(jsonObject.getString("fecha")),(jsonObject.getString("dibujante")),R.drawable.cencmaref235
                                ,(jsonObject.getString("codigo")),(jsonObject.getString("id")),(jsonObject.getString("frente"))));

                        adapter = new Adapter(MainActivity.this, listdatos);
                        recycler.setAdapter(adapter);



                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String archivo=listdatos.get(recycler.getChildAdapterPosition(v)).getPdf();
                                String no_plano=listdatos.get(recycler.getChildAdapterPosition(v)).getCantidad();
                                String plano_min=listdatos.get(recycler.getChildAdapterPosition(v)).getPdf();
                                String fecha=listdatos.get(recycler.getChildAdapterPosition(v)).getFecha();

                                Intent intent=new Intent(MainActivity.this,Vista_pdf.class);
                                intent.putExtra("dato",archivo);
                                intent.putExtra("pos",fecha);
                                intent.putExtra("pos1",no_plano);
                                intent.putExtra("plano_min1",plano_min);
                                intent.putExtra("ipwifi",ip);

                                startActivity(intent);
                            }
                        });

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                 Toast.makeText(getApplicationContext(), "error de conexion observaciones", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}// principal