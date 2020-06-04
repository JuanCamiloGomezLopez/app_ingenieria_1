package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.github.barteksc.pdfviewer.PDFView;

public class Mostrar_pdf extends AppCompatActivity {

    PDFView visor;
    String recuperar1;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pdf);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        final String recuperar1 = getIntent().getExtras().getString("dato");
        visor=(PDFView)findViewById(R.id.mostrarpdf);
        visor.fromAsset(recuperar1+".pdf").load();
    }
}
