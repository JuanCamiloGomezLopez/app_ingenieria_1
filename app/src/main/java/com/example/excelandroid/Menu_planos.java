package com.example.excelandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.Toast;

public class Menu_planos extends AppCompatActivity {

    Window window;
    ImageView cma,ctr,alm,tdc,tac,tcn,pal,pcb,tas,gen,gen_gen;
    Button nuevoplano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_planos);

        final String ip = getIntent().getExtras().getString("ipwifi");

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

                String current_usuario="camilo1";
                String usuario1="camilo";
                String usuario2 ="german";

                AlertDialog.Builder mydialog = new AlertDialog.Builder(Menu_planos.this);
                mydialog.setTitle("Utsed no esta autorizado para realizar esta accion");

                mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                mydialog.show();

                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Menu_planos.this);
                mydialog1.setTitle("Bienvenido " + current_usuario + " \nIngrese su Contraseña");

                final EditText contraseña1 = new EditText(Menu_planos.this);
                contraseña1.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog1.setView(contraseña1);

                mydialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = contraseña1.getText().toString();


                        Toast.makeText(Menu_planos.this, "la contraseña es" + pass, Toast.LENGTH_SHORT).show();
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

    }
}
