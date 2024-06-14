package com.example.ecounity.activity.adapter;

import android.content.Context;
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
    private OnSendButtonClickListener onSendButtonClickListener;
    private Context context;

    public UsuarioAdapter(List<Usuario> usuarios, OnSendButtonClickListener onSendButtonClickListener, Context context) {
        this.usuarios = usuarios;
        this.onSendButtonClickListener = onSendButtonClickListener;
        this.context = context;
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

        // Carregar a imagem de usuário usando Glide
        Glide.with(holder.itemView.getContext())
                .load(usuario.getFotoPerfil())
                .placeholder(R.drawable.loading)
                .error(R.drawable.nao_ha_fotos)
                .into(holder.fotoPerfilImageView);

        holder.nomeTextView.setText(usuario.getNome());
        holder.bioTextView.setText(usuario.getBio());
        holder.dataTextView.setText(usuario.getData());
        holder.emailTextView.setText(usuario.getEmail());
        holder.sexoTextView.setText(usuario.getSexo());
        holder.estadoTextView.setText(usuario.getEstado());
        holder.cidadeTextView.setText(usuario.getCidade());

        if (usuario.isOnline()) {
            holder.statusConexaoTextView.setText("Online");
            holder.statusConexaoTextView.setTextColor(Color.GREEN);
        } else {
            holder.statusConexaoTextView.setText("Offline");
            holder.statusConexaoTextView.setTextColor(Color.RED);
        }

        holder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = usuario.getWhatsapp();
                onSendButtonClickListener.onSendButtonClick(phoneNumber);
            }
        });
    }


    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        public View sendButton;
        TextView nomeTextView;
        ImageView fotoPerfilImageView;
        TextView bioTextView;
        TextView dataTextView;
        TextView cidadeTextView;
        TextView estadoTextView;
        TextView sexoTextView;
        TextView emailTextView;
        TextView statusConexaoTextView;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.user_name);
            fotoPerfilImageView = itemView.findViewById(R.id.user_profile_image);
            bioTextView = itemView.findViewById(R.id.txtBio);
            dataTextView = itemView.findViewById(R.id.txtData);
            cidadeTextView = itemView.findViewById(R.id.txtCidade);
            estadoTextView = itemView.findViewById(R.id.txtEstado);
            sexoTextView = itemView.findViewById(R.id.txtSexo);
            emailTextView = itemView.findViewById(R.id.txtEmailPerfil);
            statusConexaoTextView = itemView.findViewById(R.id.user_status_connection);
            sendButton = itemView.findViewById(R.id.send_button);
        }
    }

    public interface OnSendButtonClickListener {
        void onSendButtonClick(String phoneNumber);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
