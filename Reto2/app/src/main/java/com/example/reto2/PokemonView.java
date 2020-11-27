package com.example.reto2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private TextView nombre;
    private ImageView foto;

    public PokemonView(ConstraintLayout root) {
        super(root);
        this.root=root;
        nombre = root.findViewById(R.id.nombreTV);
        foto = root.findViewById(R.id.fotoIV);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getNombre() {
        return nombre;
    }

    public ImageView getFoto() {
        return foto;
    }
}
