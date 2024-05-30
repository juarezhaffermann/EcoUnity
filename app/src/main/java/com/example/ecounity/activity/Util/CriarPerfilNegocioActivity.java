package com.example.ecounity.activity.Util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecounity.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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
    private EditText nomeNegocio, descricaoNegocio, siteNegocio, emailNegocio, telefoneNegocio, enderecoNegocio, produto, descricaoProduto, precoProduto;
    private ImageView fotoPerfil;
    private List<String> imagensUrls = new ArrayList<>();
    private List<Uri> imagensUris = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Uri logotipoUri;
    private boolean isLogotipo = false;

    private final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::handleActivityResult
    );

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_perfil_negocio);

        nomeNegocio = ((TextInputLayout) findViewById(R.id.nomeNegocioVerde)).getEditText();
        descricaoNegocio = ((TextInputLayout) findViewById(R.id.descricaoNegocioVerde)).getEditText();
        siteNegocio = ((TextInputLayout) findViewById(R.id.siteNegocioVerde)).getEditText();
        emailNegocio = ((TextInputLayout) findViewById(R.id.editTextTextEmailAddress2)).getEditText();
        telefoneNegocio = ((TextInputLayout) findViewById(R.id.editTextPhone)).getEditText();
        enderecoNegocio = ((TextInputLayout) findViewById(R.id.enderecoNegocioVerde)).getEditText();
        produto = ((TextInputLayout) findViewById(R.id.produto)).getEditText();
        descricaoProduto = ((TextInputLayout) findViewById(R.id.descricaoProduto)).getEditText();
        precoProduto = ((TextInputLayout) findViewById(R.id.precoProduto)).getEditText();
        fotoPerfil = findViewById(R.id.fotoPerfilNegocio);

        View.OnClickListener imageClickListener = v -> {
            isLogotipo = false; // Indicate that this is not for the logotipo
            mGetContent.launch(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true));
        };

        ImageView imageView1 = findViewById(R.id.imageView1);
        imageView1.setOnClickListener(imageClickListener);

        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(imageClickListener);

        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(imageClickListener);

        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(imageClickListener);

        ImageView imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(imageClickListener);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        fotoPerfil.setOnClickListener(v -> {
            try {
                isLogotipo = true; // Indicate that this is for the logotipo
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                mGetContent.launch(intent);
            } catch (Exception e) {
                Log.e(TAG, "Erro ao selecionar imagem", e);
                Toast.makeText(this, "Erro ao selecionar imagem", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.salvarButton).setOnClickListener(v -> {
            try {
                uploadLogoAndSaveProfile();
            } catch (Exception e) {
                Log.e(TAG, "Erro ao salvar perfil", e);
                Toast.makeText(this, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Intent data = result.getData();
            if (isLogotipo) {
                logotipoUri = data.getData();
                fotoPerfil.setImageURI(logotipoUri);
            } else {
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
    }

    private void uploadLogoAndSaveProfile() {
        String nome = nomeNegocio.getText().toString();
        String descricao = descricaoNegocio.getText().toString();
        String site = siteNegocio.getText().toString();
        String email = emailNegocio.getText().toString();
        String telefone = telefoneNegocio.getText().toString();
        String endereco = enderecoNegocio.getText().toString();
        String produtoValor = produto.getText().toString();
        String descricaoProdutoValor = descricaoProduto.getText().toString();
        String precoProdutoValor = precoProduto.getText().toString();

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
        dadosPerfil.put("produto", produtoValor);
        dadosPerfil.put("descricao_produto", descricaoProdutoValor);
        dadosPerfil.put("preco", Double.parseDouble(precoProdutoValor));

        if (logotipoUri != null) {
            String fileName = UUID.randomUUID().toString();
            final StorageReference ref = storage.getReference().child("logos/" + fileName);
            UploadTask uploadTask = ref.putFile(logotipoUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        dadosPerfil.put("logotipo", downloadUri.toString());
                        salvarPerfil(dadosPerfil);
                    } else {
                        Log.e(TAG, "Erro ao fazer upload do logotipo", task.getException());
                        Toast.makeText(CriarPerfilNegocioActivity.this, "Erro ao fazer upload do logotipo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            salvarPerfil(dadosPerfil);
        }
    }

    @SuppressLint("LongLogTag")
    private void salvarPerfil(Map<String, Object> dadosPerfil) {
        db.collection("empresas_verdes")
                .add(dadosPerfil)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String documentId = task.getResult().getId();
                        Log.d(TAG, "Perfil salvo com sucesso: " + documentId);

                        for (Uri uri : imagensUris) {
                            salvarImagemNegocio(documentId, uri);
                        }

                        finish();
                    } else {
                        Log.w(TAG, "Erro ao salvar perfil", task.getException());
                        Toast.makeText(CriarPerfilNegocioActivity.this, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("LongLogTag")
    private void salvarImagemNegocio(String documentId, Uri uri) {
        String fileName = UUID.randomUUID().toString();
        final StorageReference ref = storage.getReference().child("imagens/" + fileName);
        UploadTask uploadTask = ref.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                imagensUrls.add(downloadUri.toString());
                db.collection("empresas_verdes").document(documentId)
                        .update("imagens", imagensUrls)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Imagem adicionada com sucesso ao perfil"))
                        .addOnFailureListener(e -> Log.w(TAG, "Erro ao adicionar imagem ao perfil", e));
            } else {
                Log.e(TAG, "Erro ao fazer upload da imagem", task.getException());
                Toast.makeText(CriarPerfilNegocioActivity.this, "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
