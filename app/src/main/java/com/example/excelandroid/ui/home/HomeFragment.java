package com.example.excelandroid.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.excelandroid.MainActivity;
import com.example.excelandroid.Main_inventario;
import com.example.excelandroid.Notificaciones;
import com.example.excelandroid.Planear_reunion;
import com.example.excelandroid.R;
import com.example.excelandroid.Registrar_reunion;
import com.example.excelandroid.Resumen_reuniones;

public class HomeFragment extends Fragment {



     private TextView bienvenido;
     String d;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);






        return root;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        bienvenido=view.findViewById(R.id.textView4);

        if (getArguments() != null) {
            String d= getArguments().getString("nombre","sadfasdf");
            Toast.makeText(getContext(), d, Toast.LENGTH_SHORT).show();

        }

        ImageView lista_planos=(ImageView)view.findViewById(R.id.imageView11);
        ImageView planear_reunion=(ImageView)view.findViewById(R.id.imageView8);
        ImageView resumen_reunion=(ImageView)view.findViewById(R.id.imageView5);
        ImageView mostrar_inventario=(ImageView)view.findViewById(R.id.imageView7);
        ImageView notificaciones=(ImageView)view.findViewById(R.id.imageView3);


        lista_planos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

       planear_reunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(), Planear_reunion.class);
                startActivity(intent);
            }
        });

        resumen_reunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(), Resumen_reuniones.class);
                startActivity(intent);
            }
        });

        mostrar_inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(), Main_inventario.class);
                startActivity(intent);
            }
        });

        notificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(), Notificaciones.class);
                startActivity(intent);
            }
        });
    }
}
