package com.example.ecounity.activity.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.model.Negocios;

public class NegociosViewHolder extends RecyclerView.ViewHolder {

    public TextView nomeTextView;
    public TextView descricaoTextView;
    public ImageView logoImageView;

    public NegociosViewHolder(@NonNull View itemView) {
        super(itemView);

        nomeTextView = itemView.findViewById(R.id.txtTituloVitrine);
        descricaoTextView = itemView.findViewById(R.id.txtDescricaoNegocio);
        logoImageView = itemView.findViewById(R.id.imageView2);
    }

    // You can add methods to the ViewHolder to simplify the onBindViewHolder method
    public void bind(Negocios negocio) {
        nomeTextView.setText(negocio.getNome());
        descricaoTextView.setText(negocio.getDescricao());
        // You can add code here to load the logo image using a library like Glide or Picasso
    }
}

