package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main_Imagenes_reunion extends AppCompatActivity {


    ArrayList<Imagenes_reunion> listdatos0;
    RecyclerView recycler;
    Adpter_imagenes adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__imagenes_reunion);


        listdatos0 = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.Recycler_imagenes);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager1);

        listdatos0.add(new Imagenes_reunion(R.drawable.android));

        adapter1 = new Adpter_imagenes(Main_Imagenes_reunion.this,listdatos0);

        recycler.setAdapter(adapter1);

    }


}




