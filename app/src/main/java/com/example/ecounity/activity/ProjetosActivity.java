package com.example.ecounity.activity;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.adapter.ProjetosAdapter;
import com.example.ecounity.activity.model.Projeto;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProjetosActivity extends AppCompatActivity {

    private List<Projeto> listaProjetos;
    private ProjetosAdapter projetosAdapter;

    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projetos);

        // Inicializar Firebase
        storageReference = FirebaseStorage.getInstance().getReference("projetos");

        // Referenciar RecyclerView
        RecyclerView recyclerViewProjetos = findViewById(R.id.recyclerViewProjetos);

        // Configurar layout manager para RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProjetos.setLayoutManager(layoutManager);

        // Inicializar lista de projetos
        listaProjetos = new ArrayList<>();

        // Inicializar adapter e configurar RecyclerView
        projetosAdapter = new ProjetosAdapter(this, listaProjetos);
        recyclerViewProjetos.setAdapter(projetosAdapter);

        // Carregar lista de projetos do Cloud Storage
        carregarProjetos();

        // Adicionar escuta para o botão
        Button buttonAdicionarProjeto = findViewById(R.id.buttonAdicionarProjeto);
        buttonAdicionarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjetosActivity.this, CriarProjetoActivity.class);
                startActivity(intent);
            }
        });

        // Listar projetos do banco de dados na activity_projetos.xml
        listarProjetosDoBancoDeDados();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void carregarProjetos() {
        // Limpar lista de projetos antes de carregar novamente
        listaProjetos.clear();

        // Obter referência do Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consulta ao Firestore para obter os projetos
        db.collection("projetos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obter projeto e adicionar à lista
                            Projeto projeto = document.toObject(Projeto.class);
                            listaProjetos.add(projeto);
                        }

                        // Notificar o adapter sobre as mudanças nos dados
                        projetosAdapter.notifyDataSetChanged();
                    } else {
                        // Em caso de erro na consulta
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }




    private void listarProjetosDoBancoDeDados() {
        // Substitua este método pelo seu próprio método para buscar os projetos do banco de dados
    }

}