package com.example.rpg_monster_app;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<Datos> listDatos;

    public AdapterDatos(ArrayList<Datos> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial,null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;
        RelativeLayout layout;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.idDato);
            layout = (RelativeLayout) itemView.findViewById(R.id.layoutDato);
        }

        public void asignarDatos(Datos datos) {
            dato.setText(datos.getValor());
            Log.e("Datos",datos.getTAG());
            if(datos.getTAG().equals("Rendir")){
                layout.setBackgroundColor(Color.parseColor("#B05EF5"));
                dato.setTextColor(Color.parseColor("#853D90"));
            }else if(datos.getTAG().equals("Atacar")){
                layout.setBackgroundColor(Color.parseColor("#62DAE4"));
                dato.setTextColor(Color.parseColor("#3A7F85"));
            }else if(datos.getTAG().equals("Cura")){
                layout.setBackgroundColor(Color.parseColor("#AEEE6F"));
                dato.setTextColor(Color.parseColor("#5F853A"));
            }else if(datos.getTAG().equals("Monstruo")){
                layout.setBackgroundColor(Color.parseColor("#E17564"));
                dato.setTextColor(Color.parseColor("#894339"));
            }

        }
    }
}
