package com.example.excelandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_inventario extends RecyclerView.Adapter<Adapter_inventario.ViewHolder>implements Filterable, View.OnClickListener{
    Context context;
    ArrayList<Main_inventario.inventario> datos;
    ArrayList<Main_inventario.inventario> listdatos4;
    private View.OnClickListener listener;

    public Adapter_inventario (Context c,ArrayList<Main_inventario.inventario> datos) {
        context=c;
        this.datos = datos;
        this.listdatos4=new ArrayList<>(datos);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_inventario,null ,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.codigo.setText(datos.get(position).getCodigo());
        holder.material.setText(datos.get(position).getMaterial());
        holder.cantidad.setText(datos.get(position).getCantidad());
        holder.unidad.setText(datos.get(position).getUnidad());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Main_inventario.inventario> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(listdatos4);
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Main_inventario.inventario item : listdatos4) {



                    if (item.getMaterial().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            datos.clear();
            datos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };











    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigo,material,cantidad,unidad;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo=(TextView)itemView.findViewById(R.id.text_sap);
            material=(TextView) itemView.findViewById(R.id.text_descripccion);
            cantidad=(TextView) itemView.findViewById(R.id.text_cantidad);
            unidad=(TextView) itemView.findViewById(R.id.text_unidad);

        }
    }
}

