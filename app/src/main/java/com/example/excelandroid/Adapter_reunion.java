package com.example.excelandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_reunion extends RecyclerView.Adapter<Adapter_reunion.ViewHolder>implements Filterable, View.OnClickListener {
    Context context;
    ArrayList<Resumen_reuniones.blog2> listdatos;
    ArrayList<Resumen_reuniones.blog2> listdatos4;



    public View.OnClickListener listener;

    public Adapter_reunion(Context c, ArrayList<Resumen_reuniones.blog2> listdatos) {
        context=c;
        this.listdatos = listdatos;
        this.listdatos4=new ArrayList<>(listdatos);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_resumen_reuniones,null ,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tema.setText(listdatos.get(position).getTema());
        holder.fecha.setText(listdatos.get(position).getFecha());
        holder.lugar.setText(listdatos.get(position).getLugar());

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
            ArrayList<Resumen_reuniones.blog2> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(listdatos4);
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Resumen_reuniones.blog2 item : listdatos4) {



                    if (item.getTema().toLowerCase().contains(filterPattern)) {
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
        TextView tema,fecha,lugar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tema=(TextView) itemView.findViewById(R.id.text_descripccion);
            fecha=(TextView) itemView.findViewById(R.id.text_cantidad);
            lugar=(TextView) itemView.findViewById(R.id.text_unidad);

        }
    }
}