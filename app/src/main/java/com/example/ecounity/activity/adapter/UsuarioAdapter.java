package com.example.ecounity.activity.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecounity.R;
import com.example.ecounity.activity.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;

    public UsuarioAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UsuarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);

        // Carregar foto do perfil (se disponível)
        if (usuario.getFotoPerfilUrl() != null && !usuario.getFotoPerfilUrl().isEmpty()) {
            // Aqui você pode usar uma biblioteca como Glide ou Picasso para carregar a imagem
            // Exemplo com Glide:
            Glide.with(holder.itemView.getContext())
                    .load(usuario.getFotoPerfilUrl())
                    .into(holder.fotoPerfilImageView);
        }

        holder.nomeTextView.setText(usuario.getNome());

        // Indicar status de conexão (online/offline)
        if (usuario.isOnline()) {
            holder.statusConexaoTextView.setText("Online");
            holder.statusConexaoTextView.setTextColor(Color.GREEN);
        } else {
            holder.statusConexaoTextView.setText("Offline");
            holder.statusConexaoTextView.setTextColor(Color.RED);
        }
    }

    // Não esqueça de adicionar as referências para fotoPerfilImageView e statusConexaoTextView no seu ViewHolder:
    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        ImageView fotoPerfilImageView; // Adicione esta linha
        TextView statusConexaoTextView; // Adicione esta linha

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.user_name);
            fotoPerfilImageView = itemView.findViewById(R.id.user_profile_image); // Adicione esta linha
            statusConexaoTextView = itemView.findViewById(R.id.user_status_connection); // Adicione esta linha
        }
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}