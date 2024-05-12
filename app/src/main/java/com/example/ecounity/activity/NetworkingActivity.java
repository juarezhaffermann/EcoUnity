package com.example.ecounity.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.Util.WhatsAppApi;
import com.example.ecounity.activity.adapter.UsuarioAdapter;
import com.example.ecounity.activity.model.Usuario;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NetworkingActivity extends AppCompatActivity {

    private RecyclerView userList;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);
        context = this;

        userList = findViewById(R.id.user_list);
        usuarios = new ArrayList<>();

        usuarioAdapter = new UsuarioAdapter(usuarios, new UsuarioAdapter.OnSendButtonClickListener() {
            @Override
            public void onSendButtonClick(String phoneNumber) {
                EditText messageInput = findViewById(R.id.message_input);
                String message = messageInput.getText().toString();
                try {
                    WhatsAppApi.sendWhatsAppMessage(NetworkingActivity.this, phoneNumber, message);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, NetworkingActivity.this);

        userList.setLayoutManager(new LinearLayoutManager(this));
        userList.setAdapter(usuarioAdapter);

        try {
            carregarUsuariosFirestore();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar usuários: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    // Carregar dados dos usuários do Cloud Firestore
    private void carregarUsuariosFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");
        ListenerRegistration listenerRegistration = usuariosRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
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