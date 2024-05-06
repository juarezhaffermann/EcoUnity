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


        storageReference = FirebaseStorage.getInstance().getReference("projetos");


        RecyclerView recyclerViewProjetos = findViewById(R.id.recyclerViewProjetos);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProjetos.setLayoutManager(layoutManager);


        listaProjetos = new ArrayList<>();


        projetosAdapter = new ProjetosAdapter(this, listaProjetos);
        recyclerViewProjetos.setAdapter(projetosAdapter);


        carregarProjetos();


        Button buttonAdicionarProjeto = findViewById(R.id.buttonAdicionarProjeto);
        buttonAdicionarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjetosActivity.this, CriarProjetoActivity.class);
                startActivity(intent);
            }
        });


        listarProjetosDoBancoDeDados();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void carregarProjetos() {

        listaProjetos.clear();


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("projetos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Projeto projeto = document.toObject(Projeto.class);
                            listaProjetos.add(projeto);
                        }


                        projetosAdapter.notifyDataSetChanged();
                    } else {

                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }




    private void listarProjetosDoBancoDeDados() {

    }

}