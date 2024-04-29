package com.example.ecounity.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.model.Negocios;

import java.util.ArrayList;
import java.util.List;

public class NegociosAdapter extends RecyclerView.Adapter<NegociosAdapter.NegociosViewHolder> {

    private List<Negocios> negociosList;

    // Construtor que recebe a lista de negócios
    public NegociosAdapter() {
        this.negociosList = new ArrayList<>();
    }

    public void setListaNegocios(List<Negocios> novaLista) {
        this.negociosList = novaLista;
        notifyDataSetChanged();  // Notificar o RecyclerView que os dados foram alterados
    }

    @NonNull
    @Override
    public NegociosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vitrine, parent, false);
        return new NegociosViewHolder(view);
    }

    // Este método vincula os dados do objeto Negocios à ViewHolder
    @Override
    public void onBindViewHolder(@NonNull NegociosViewHolder holder, int position) {
        Negocios negocio = negociosList.get(position);
        holder.nomeTextView.setText(negocio.getNome());
        holder.descricaoTextView.setText(negocio.getDescricao());
        // Aqui você pode usar uma biblioteca como Glide ou Picasso para carregar e definir a imagem do logotipo

        // Aqui você pode definir um listener de clique para o item, se necessário
    }

    // Este método retorna o número total de itens na lista
    @Override
    public int getItemCount() {
        return negociosList.size();
    }

    // Este método atualiza os dados da lista e notifica o adapter sobre as alterações
    public void atualizarDados(List<Negocios> novaLista) {
        negociosList.clear();
        negociosList.addAll(novaLista);
        notifyDataSetChanged();
    }

    // ViewHolder para armazenar as views do layout item_vitrine
    public static class NegociosViewHolder extends RecyclerView.ViewHolder {

        public TextView nomeTextView;
        public TextView descricaoTextView;
        public ImageView logoImageView;

        public NegociosViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeTextView = itemView.findViewById(R.id.txtTituloVitrine);
            descricaoTextView = itemView.findViewById(R.id.txtDescricaoNegocio);
            logoImageView = itemView.findViewById(R.id.imageView2);
        }
    }
}