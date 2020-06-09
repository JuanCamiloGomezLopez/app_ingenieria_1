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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>implements Filterable, View.OnClickListener {

    ArrayList<blog> listdatos;
    ArrayList<blog> listdatos5;
    private View.OnClickListener listener;
    Context context;

    public Adapter(Context c, ArrayList<blog> listdatos) {
        context=c;
        this.listdatos = listdatos;
        this.listdatos5=new ArrayList<>(listdatos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.myviewholder,null ,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.cantidad.setText(listdatos.get(position).getCantidad());
    holder.descripccion.setText(listdatos.get(position).getDescripccion());
    holder.fecha.setText(listdatos.get(position).getFecha());
    holder.dibujo.setText(listdatos.get(position).getDibujo());
    holder.foto.setImageResource(listdatos.get(position).getFoto());
    blog posit = listdatos.get(position);
    }

    @Override
    public int getItemCount() {
        return listdatos.size();
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
            ArrayList<blog> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(listdatos5);
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (blog item : listdatos5) {



                    if (item.getDescripccion().toLowerCase().contains(filterPattern)) {
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
            listdatos.clear();
            listdatos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cantidad,descripccion,fecha,dibujo;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cantidad=(TextView)itemView.findViewById(R.id.text_sap);
            descripccion=(TextView) itemView.findViewById(R.id.text_descripccion);
            fecha=(TextView) itemView.findViewById(R.id.text_cantidad);
            dibujo=(TextView) itemView.findViewById(R.id.text_unidad);
            foto= (ImageView)itemView.findViewById(R.id.imageView);
        }

    }
}

