package com.example.ecounity.activity.adapter;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

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
    public TextView descricao_produtoTextView;
    public TextView precoTextView;
    public ViewAnimator viewAnimator;

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
        descricao_produtoTextView = itemView.findViewById(R.id.txtDescricaoProduto);
        precoTextView = itemView.findViewById(R.id.txtPreco);
        viewAnimator = itemView.findViewById(R.id.ImagensCarrosel);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(Negocios negocio) {
        nomeTextView.setText(negocio.getNome());
        if (negocio.getLogotipo() != null && !negocio.getLogotipo().isEmpty()) {
            Glide.with(itemView.getContext())
                    .load(negocio.getLogotipo())
                    .into(logotipoImageView);
        }
        if (negocio.getImagens() != null && !negocio.getImagens().isEmpty()) {
            for (int i = 0; i < negocio.getImagens().size(); i++) {
                ImageView imageView = (ImageView) viewAnimator.getChildAt(i);
                Glide.with(itemView.getContext())
                        .load(negocio.getImagens().get(i))
                        .into(imageView);
            }
            descricaoTextView.setText(negocio.getDescricao());
            siteTextView.setText(negocio.getSite());
            emailTextView.setText(negocio.getEmail());
            phoneTextView.setText(negocio.getTelefone());
            nome_produtoTextView.setText(negocio.getProduto());
            descricao_produtoTextView.setText(negocio.getDescricao_produto());
            String precoString = Double.toString(negocio.getPreco());
            precoTextView.setText(precoString);
        }
    }
}
