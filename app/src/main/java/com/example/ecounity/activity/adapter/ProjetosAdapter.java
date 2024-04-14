package com.example.ecounity.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.model.Projeto;

import java.util.List;

public class ProjetosAdapter extends RecyclerView.Adapter<ProjetosAdapter.ViewHolder> {

    private Context context;
    private List<Projeto> listaProjetos;

    public ProjetosAdapter(Context context, List<Projeto> listaProjetos) {
        this.context = context;
        this.listaProjetos = listaProjetos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_projeto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Projeto projeto = listaProjetos.get(position);
        holder.txtTitulo.setText(projeto.getTitulo());
        holder.txtDescricao.setText(projeto.getDescricao());
        holder.txtMetas.setText(projeto.getMetas());
    }

    @Override
    public int getItemCount() {
        return listaProjetos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo;
        TextView txtDescricao;
        TextView txtMetas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            txtMetas = itemView.findViewById(R.id.txtMetas);
        }
    }
}