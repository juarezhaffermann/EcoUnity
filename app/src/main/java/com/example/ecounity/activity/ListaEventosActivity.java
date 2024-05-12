package com.example.ecounity.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.adapter.EventoAdapter;
import com.example.ecounity.activity.model.Evento;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaEventosActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private EventoAdapter eventoAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data from Firebase
        db.collection("eventos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Evento> eventos = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Evento evento = document.toObject(Evento.class);
                    eventos.add(evento);
                }
                eventoAdapter = new EventoAdapter(eventos, ListaEventosActivity.this);
                recyclerView.setAdapter(eventoAdapter);
            } else {
                Log.d("ListaEventosActivity", "Error getting documents: ", task.getException());
            }
        });

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Reset the list to the original events when query is empty
                    eventoAdapter.filterList("");
                } else {
                    filterList(newText);
                }
                return true;
            }
        });
    }

    private void filterList(String query) {
        eventoAdapter.filterList(query);
    }
}
