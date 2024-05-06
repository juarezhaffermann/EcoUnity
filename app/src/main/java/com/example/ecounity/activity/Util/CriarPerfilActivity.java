package com.example.ecounity.activity.Util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecounity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CriarPerfilActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    private static final String TAG = "CriarPerfilActivity";

    private ImageView fotoPerfilImageView;
    private EditText nomeEditText;

    private EditText dataEditText;
    private EditText sexoEditText;
    private EditText cidadeEditText;
    private EditText estadoEditText;
    private EditText bioEditText;
    private Button salvarButton;

    private Uri fotoPerfilUri;
    private String fotoPerfilUrl;

    private FirebaseFirestore db;
    private StorageReference storageRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_perfil_usuario);

        fotoPerfilImageView = findViewById(R.id.fotoPerfilImageView);
        nomeEditText = findViewById(R.id.nomeEditText);
        salvarButton = findViewById(R.id.salvarButton);
        dataEditText = findViewById(R.id.editTextDate);
        sexoEditText = findViewById(R.id.sexo);
        cidadeEditText = findViewById(R.id.cidade);
        estadoEditText = findViewById(R.id.estado);
        bioEditText = findViewById(R.id.bio);

        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        fotoPerfilImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPerfil();
            }
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            fotoPerfilUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fotoPerfilUri);
                fotoPerfilImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void salvarPerfil() {
        String nome = nomeEditText.getText().toString();

        if (nome.isEmpty() || fotoPerfilUri == null) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        salvarFotoPerfilNoStorage();
    }

    private void salvarFotoPerfilNoStorage() {
        final String nomeArquivo = UUID.randomUUID().toString();
        StorageReference fotoRef = storageRef.child("fotos_perfil/" + nomeArquivo);

        fotoRef.putFile(fotoPerfilUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fotoPerfilUrl = taskSnapshot.getMetadata().getReference().toString();
                        salvarDadosDoUsuario(fotoPerfilUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erro ao salvar foto de perfil", e);
                        Toast.makeText(CriarPerfilActivity.this, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void salvarDadosDoUsuario(String fotoPerfilUrl) {
        String uid = auth.getCurrentUser().getUid();
        String email = auth.getCurrentUser().getEmail();
        String nome = nomeEditText.getText().toString();
        String data = dataEditText.getText().toString();
        String sexo = sexoEditText.getText().toString();
        String cidade = cidadeEditText.getText().toString();
        String estado = estadoEditText.getText().toString();
        String bio = bioEditText.getText().toString();

        Map<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("fotoPerfil", fotoPerfilUrl);
        dadosUsuario.put("nome", nome);
        dadosUsuario.put("data", data);
        dadosUsuario.put("sexo", sexo);
        dadosUsuario.put("cidade", cidade);
        dadosUsuario.put("estado", estado);
        dadosUsuario.put("bio", bio);
        dadosUsuario.put("email", email);
        dadosUsuario.put("statusConexao", false);
        dadosUsuario.put("uid", uid);

        CollectionReference usuariosCollection = db.collection("Usuarios");

        usuariosCollection.add(dadosUsuario)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Perfil salvo com sucesso!");
                            Toast.makeText(CriarPerfilActivity.this, "Perfil salvo com sucesso!", Toast.LENGTH_SHORT).show();
                            finish(); // Finaliza a activity após salvar o perfil
                        } else {
                            Log.w(TAG, "Erro ao salvar perfil", task.getException());
                            Toast.makeText(CriarPerfilActivity.this, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
