package com.example.ecounity.activity.adapter;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecounity.R;
import com.example.ecounity.activity.model.Negocios;

public class NegociosViewHolder extends RecyclerView.ViewHolder {

    public TextView nomeTextView;
    public ImageView logotipoImageView;
    public TextView descricaoTextView;
    public TextView siteTextView;
    public TextView emailTextView;
    public TextView phoneTextView;
    public TextView produtosTextView;
    public TextView nome_produtoTextView;

    public NegociosViewHolder(@NonNull View itemView) {
        super(itemView);

        nomeTextView = itemView.findViewById(R.id.txtTituloVitrine);
        logotipoImageView = itemView.findViewById(R.id.imageViewLogotipo);
        descricaoTextView = itemView.findViewById(R.id.txtDescricaoNegocio);
        siteTextView = itemView.findViewById(R.id.txtSite);
        emailTextView = itemView.findViewById(R.id.txtEmail);
        phoneTextView = itemView.findViewById(R.id.txtPhone);
        produtosTextView = itemView.findViewById(R.id.txtProdutos);
        nome_produtoTextView = itemView.findViewById(R.id.txtNomeProduto);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(Negocios negocio) {
        nomeTextView.setText(negocio.getNome());
        if (negocio.getLogotipo() != null && !negocio.getLogotipo().isEmpty()) {
            Glide.with(itemView.getContext())
                    .load(negocio.getLogotipo())
                    .into(logotipoImageView);
        }
        descricaoTextView.setText(negocio.getDescricao());
        siteTextView.setText(negocio.getSite());
        emailTextView.setText(negocio.getEmail());
        phoneTextView.setText(negocio.getTelefone());
        produtosTextView.setText(negocio.getProdutosServicos());

    }
}
