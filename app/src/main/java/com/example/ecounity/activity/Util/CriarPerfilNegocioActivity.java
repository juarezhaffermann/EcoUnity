package com.example.ecounity.activity.Util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecounity.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CriarPerfilNegocioActivity extends AppCompatActivity {
    private static final String TAG = "CriarPerfilNegocioActivity";
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nomeNegocio, descricaoNegocio, siteNegocio, emailNegocio, telefoneNegocio, enderecoNegocio;
    private ImageView fotoPerfil;
    private List<String> imagensUrls = new ArrayList<>();
    private List<Uri> imagensUris = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Uri imagemUri;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_perfil_negocio);

        nomeNegocio = ((TextInputLayout) findViewById(R.id.nomeNegocioVerde)).getEditText();
        descricaoNegocio = ((TextInputLayout) findViewById(R.id.descricaoNegocioVerde)).getEditText();
        siteNegocio = ((TextInputLayout) findViewById(R.id.siteNegocioVerde)).getEditText();
        emailNegocio = findViewById(R.id.editTextTextEmailAddress2);
        telefoneNegocio = findViewById(R.id.editTextPhone);
        enderecoNegocio = ((TextInputLayout) findViewById(R.id.enderecoNegocioVerde)).getEditText();
        fotoPerfil = findViewById(R.id.fotoPerfilNegocio);

        ImageView imageView1 = findViewById(R.id.imageView1);
        imageView1.setOnClickListener(v -> selecionarImagem());

        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(v -> selecionarImagem());

        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(v -> selecionarImagem());

        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(v -> selecionarImagem());

        ImageView imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(v -> selecionarImagem());


        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        fotoPerfil.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            } catch (Exception e) {
                Log.e(TAG, "Erro ao selecionar imagem", e);
                Toast.makeText(this, "Erro ao selecionar imagem", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.salvarButton).setOnClickListener(v -> {
            try {
                salvarPerfil();
            } catch (Exception e) {
                Log.e(TAG, "Erro ao salvar perfil", e);
                Toast.makeText(this, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imagemUri = data.getData();
            fotoPerfil.setImageURI(imagemUri);
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagensUris.add(uri);
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                imagensUris.add(uri);
            }
        }
    }

    @SuppressLint("LongLogTag")
    private void salvarPerfil() {
        try {
            String nome = nomeNegocio.getText().toString();
            String descricao = descricaoNegocio.getText().toString();
            String site = siteNegocio.getText().toString();
            String email = emailNegocio.getText().toString();
            String telefone = telefoneNegocio.getText().toString();
            String endereco = enderecoNegocio.getText().toString();

            if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(endereco)) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> dadosPerfil = new HashMap<>();
            dadosPerfil.put("nome", nome);
            dadosPerfil.put("descricao", descricao);
            dadosPerfil.put("site", site);
            dadosPerfil.put("email", email);
            dadosPerfil.put("telefone", telefone);
            dadosPerfil.put("endereco", endereco);
            dadosPerfil.put("logotipo", "");
            dadosPerfil.put("imagens", imagensUrls);
            dadosPerfil.put("avaliacao_media", 0.0);
            dadosPerfil.put("numero_avaliacoes", 0);

            db.collection("empresas_verdes")
                    .add(dadosPerfil)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String documentId = task.getResult().getId();
                            Log.d(TAG, "Perfil salvo com sucesso: " + documentId);

                            if (imagensUrls.size() > 0) {
                                salvarImagemLogo(documentId);
                            }

                            for (Uri uri : imagensUris) {
                                salvarImagemNegocio(documentId, uri);
                            }

                            finish();
                        } else {
                            Log.w(TAG, "Erro ao salvar perfil", task.getException());
                            Toast.makeText(CriarPerfilNegocioActivity.this, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Erro ao salvar dados do perfil", e);
            Toast.makeText(this, "Erro ao salvar dados do perfil", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("LongLogTag")
    private void salvarImagemLogo(String documentId) {
        try {
            if (imagemUri != null) {
                final StorageReference fileReference = storage.getReference().child("logotipos/" + documentId);

                fileReference.putFile(imagemUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        db.collection("empresas_verdes").document(documentId)
                                .update("logotipo", downloadUri.toString());
                    } else {
                        // Handle failures
                        // ...
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao salvar imagem do logotipo", e);
            Toast.makeText(this, "Erro ao salvar imagem do logotipo", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("LongLogTag")
    private void selecionarImagem() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao selecionar imagem", e);
            Toast.makeText(this, "Erro ao selecionar imagem", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarImagemNegocio(String documentId, Uri imagemUri) {
        if (imagemUri != null) {
            final StorageReference fileReference = storage.getReference().child("imagens_negocio/" + documentId + "/" + UUID.randomUUID().toString());

            fileReference.putFile(imagemUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imagensUrls.add(downloadUri.toString());
                    db.collection("empresas_verdes").document(documentId)
                            .update("imagens", imagensUrls);
                } else {
                    // Handle failures
                    // ...
                }
            });
        }
    }
}
