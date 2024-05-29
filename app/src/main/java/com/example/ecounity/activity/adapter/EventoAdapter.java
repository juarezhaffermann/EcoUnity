package com.example.ecounity.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecounity.R;
import com.example.ecounity.activity.model.Evento;
import java.util.ArrayList;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {
    private List<Evento> eventos;
    private List<Evento> originalEventos;
    private Context context;

    public EventoAdapter(List<Evento> eventos, Context context) {
        this.eventos = eventos != null ? eventos : new ArrayList<>();
        this.context = context;
        this.originalEventos = new ArrayList<>(this.eventos);
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.nome.setText(evento.getNome());
        holder.data.setText(evento.getData());
        holder.horario.setText(evento.getHorario());
        holder.local.setText(evento.getLocal());

        // Adiciona o clique no item para abrir o Google Maps
        holder.itemView.setOnClickListener(v -> {
            String address = evento.getLocal();
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public void filterList(String query) {
        List<Evento> filteredList = new ArrayList<>();
        for (Evento evento : originalEventos) {
            if (evento.getNome().toLowerCase().contains(query.toLowerCase())
                    || evento.getData().toLowerCase().contains(query.toLowerCase())
                    || evento.getHorario().toLowerCase().contains(query.toLowerCase())
                    || evento.getLocal().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(evento);
            }
        }

        eventos.clear();
        eventos.addAll(filteredList);
        notifyDataSetChanged();
    }

    public List<Evento> getOriginalEventos() {
        return originalEventos;
    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {
        public TextView nome;
        public TextView data;
        public TextView horario;
        public TextView local;

        public EventoViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.tv_nome);
            data = itemView.findViewById(R.id.tv_data);
            horario = itemView.findViewById(R.id.tv_horario);
            local = itemView.findViewById(R.id.tv_local);
        }
    }
}

