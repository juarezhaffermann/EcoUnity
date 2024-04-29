package com.example.ecounity.activity;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.Util.CriarPerfilNegocioActivity;
import com.example.ecounity.activity.adapter.NegociosAdapter;
import com.example.ecounity.activity.model.Negocios;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VitrineActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNegocios;
    private Button buttonCriarPerfilNegocio;
    private FirebaseFirestore db;
    private NegociosAdapter adapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitrine);

        recyclerViewNegocios = findViewById(R.id.recyclerViewNegocios);
        buttonCriarPerfilNegocio = findViewById(R.id.buttonCriarPerfilNegocio);

        db = FirebaseFirestore.getInstance();

        // Configurar o RecyclerView
        recyclerViewNegocios.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NegociosAdapter();
        recyclerViewNegocios.setAdapter(adapter);

        // Carregar dados do Firestore
        db.collection("empresas_verdes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Negocios> listaNegocios = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Negocios negocio = document.toObject(Negocios.class);
                            listaNegocios.add(negocio);
                        }
                        adapter.setListaNegocios(listaNegocios);
                    } else {
                        Log.e(TAG, "Error getting documents: ", Objects.requireNonNull(task.getException()));
                        // Handle the exception here, e.g., show an error message to the user
                        Toast.makeText(VitrineActivity.this, "Erro ao carregar dados. Por favor, tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                    }
                });

        findViewById(R.id.buttonCriarPerfilNegocio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VitrineActivity.this, CriarPerfilNegocioActivity.class));
            }
        });
    }
}
