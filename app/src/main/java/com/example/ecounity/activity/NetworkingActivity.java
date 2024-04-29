package com.example.ecounity.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecounity.R;
import com.example.ecounity.activity.adapter.UsuarioAdapter;
import com.example.ecounity.activity.model.Usuario;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NetworkingActivity extends AppCompatActivity {

    private RecyclerView userList;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);

        userList = findViewById(R.id.user_list);
        usuarios = new ArrayList<>();

        usuarioAdapter = new UsuarioAdapter(usuarios);
        userList.setLayoutManager(new LinearLayoutManager(this));
        userList.setAdapter(usuarioAdapter);

        try {
            carregarUsuariosFirestore();
        } catch (Exception e) {
            // Tratar erro de carregamento de dados
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar usuários: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Carregar dados dos usuários do Cloud Firestore
    private void carregarUsuariosFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");
        usuariosRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Tratar erro de carregamento de dados
                    Toast.makeText(NetworkingActivity.this, "Erro ao carregar usuários: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                usuarios.clear();

                for (QueryDocumentSnapshot snapshot : snapshots) {
                    Usuario usuario = snapshot.toObject(Usuario.class);
                    usuarios.add(usuario);
                }

                usuarioAdapter.notifyDataSetChanged();
            }
        });
    }
}
