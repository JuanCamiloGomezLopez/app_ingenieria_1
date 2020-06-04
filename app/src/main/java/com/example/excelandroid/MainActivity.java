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
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class MainActivity extends AppCompatActivity {

    ArrayList<blog> listdatos;
    RecyclerView recycler;
    Adapter adapter;



    private Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        llenardatos();

        adapter = new Adapter(MainActivity.this, listdatos);



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

                ;
                startActivity(intent);
            }
        });

        recycler.setAdapter(adapter);


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



    private void llenardatos() {


        // ingresando a excel
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("cami.xls");

            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);
            String xx = "";
            String yy = "";
            String ww = "";
            String vv = "";
            String ff = "";
            String kk = "";
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

                    if ((c == 4)) {
                        ff = z.getContents();
                    }
                    if ((c == 5)) {
                        kk = z.getContents();

                    }


                }


                listdatos.add(new blog(xx, yy, ww, vv, R.drawable.cencmaref235,ff,kk));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        // finaliza ingreso a excel


    }


    // clase para crear constructor de array

    public class blog {

        public String cantidad, descripccion, fecha, dibujo,pdf,posicion;
        public int foto;


            public blog(String cantidad, String descripccion, String fecha, String dibujo, int foto,String pdf, String posicion) {
            this.cantidad = cantidad;
            this.descripccion = descripccion;
            this.fecha = fecha;
            this.dibujo = dibujo;
            this.foto = foto;
            this.pdf=pdf;
            this.posicion=posicion;

        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getDescripccion() {
            return descripccion;
        }

        public void setDescripccion(String descripccion) {
            this.descripccion = descripccion;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getDibujo() {
            return dibujo;
        }

        public void setDibujo(String dibujo) {
            this.dibujo = dibujo;
        }

        public int getFoto() {
            return foto;
        }

        public void setFoto(int foto) {
            this.foto = foto;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }

        public String getPosicion() {
            return posicion;
        }

        public void setPosicion(String posicion) {
            this.posicion = posicion;
        }
    }


}// principal