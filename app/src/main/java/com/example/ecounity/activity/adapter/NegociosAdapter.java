package com.example.ecounity.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecounity.R;
import com.example.ecounity.activity.model.Negocios;

import java.util.ArrayList;
import java.util.List;

public class NegociosAdapter extends RecyclerView.Adapter<NegociosAdapter.NegociosViewHolder> {

    private List<Negocios> listaNegocios;

    public NegociosAdapter() {
        this.listaNegocios = new ArrayList<>();
    }

    public void setListaNegocios(List<Negocios> listaNegocios) {
        this.listaNegocios = listaNegocios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NegociosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vitrine, parent, false);
        return new NegociosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NegociosViewHolder holder, int position) {
        Negocios negocio = listaNegocios.get(position);
        holder.nomeTextView.setText(negocio.getNome());
        if (negocio.getLogotipo() != null && !negocio.getLogotipo().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(negocio.getLogotipo())
                    .into(holder.logotipoImageView);
        } else {

            holder.logotipoImageView.setImageResource(R.drawable.placeholder_logotipo);
        }
        holder.descricaoTextView.setText(negocio.getDescricao());
        holder.siteTextView.setText(negocio.getSite());
        holder.emailTextView.setText(negocio.getEmail());
        holder.phoneTextView.setText(negocio.getTelefone());
        holder.nome_produtoTextView.setText(negocio.getProduto());
        holder.descricao_produtoTextView.setText(negocio.getDescricao_produto());

        // Convert the Double price to String
        String precoString = Double.toString(negocio.getPreco());
        holder.precoTextView.setText(precoString);
    }

    @Override
    public int getItemCount() {
        return listaNegocios.size();
    }

    public static class NegociosViewHolder extends RecyclerView.ViewHolder {

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
    }
}
