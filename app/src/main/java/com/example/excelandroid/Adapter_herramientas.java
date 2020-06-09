package com.example.excelandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter_herramientas extends RecyclerView.Adapter<Adapter_herramientas.ViewHolder> {

    ArrayList<herramientas> listdatos0;

    Context context;


    public Adapter_herramientas(Context c, ArrayList<herramientas> listdatos0) {
        context=c;
        this.listdatos0 = listdatos0;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_herramientas,null ,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.descripccion_herramienta.setText(listdatos0.get(position).getDescripccion_herramienta());
        holder.fecha_salida.setText(listdatos0.get(position).getFecha_salida());
        herramientas posit = listdatos0.get(position);
    }

    @Override
    public int getItemCount() {
        return listdatos0.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView descripccion_herramienta,fecha_salida;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descripccion_herramienta=(TextView)itemView.findViewById(R.id.text_descripccion_herramienta);
            fecha_salida=(TextView) itemView.findViewById(R.id.fecha_retiro);

        }

    }
}

