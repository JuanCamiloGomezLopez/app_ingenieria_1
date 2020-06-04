package com.example.excelandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Main_inventario extends AppCompatActivity {

    Window window;
    ArrayList<inventario> datos;
    RecyclerView recycler;
    Adapter_inventario adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventario);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // getSupportActionBar().setTitle("RESUMEN DE REUNIONES");
        getSupportActionBar().setCustomView(R.layout.texte);

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        datos = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.recycler_inventario);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        llenardatos();

        adapter = new Adapter_inventario(Main_inventario.this, datos);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        recycler.setAdapter(adapter);

    }
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



    private void llenardatos() {

        // ingresando a excel
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("stock.xls");

            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);
            String xx = "";
            String yy = "";
            String ww = "";
            String vv = "";

            for (int i = 0; i < s.getRows(); i++) {

                for (int c = 0; c < s.getColumns(); c++) {

                    Cell z = s.getCell(c, i);

                    if (c == 0) {
                        xx = z.getContents();
                    }
                    if (c == 1) {
                        yy = z.getContents();

                    }
                    if (c == 2) {
                        ww = z.getContents();

                    }
                    if ((c == 3)) {
                        vv = z.getContents();
                    }

                }

                datos.add(new inventario(xx, yy, vv, ww));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        // finaliza ingreso a excel











    }


    public class inventario{

      public String codigo,material,cantidad,unidad;

        public inventario(String codigo, String material, String cantidad, String unidad) {
            this.codigo = codigo;
            this.material = material;
            this.cantidad = cantidad;
            this.unidad = unidad;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getUnidad() {
            return unidad;
        }

        public void setUnidad(String unidad) {
            this.unidad = unidad;
        }
    }


}
