package com.example.excelandroid;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import model.Inbox;

public class Adpter_imagenes extends RecyclerView.Adapter<Adpter_imagenes.ViewHolder> {

    ArrayList<Imagenes_reunion> listdatos0;

    Context context;
    private Context ctx;

    public Adpter_imagenes(Context c, ArrayList<Imagenes_reunion> listdatos0) {
        context=c;
        this.listdatos0 = listdatos0;

    }

    @NonNull
    @Override
    public Adpter_imagenes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_imagenes,null ,false);

        return new Adpter_imagenes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.imagen_reunion.setImageResource(listdatos0.get(position).getImagen());

        Imagenes_reunion posit = listdatos0.get(position);




    }




    @Override
    public int getItemCount() {
        return listdatos0.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen_reunion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen_reunion=(ImageView) itemView.findViewById(R.id.imagenes_reunion);


        }

    }










}
