package com.example.ecounity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecounity.R;
import com.example.ecounity.activity.model.Projeto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class CriarProjetoActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextDescricao, editTextMetas;
    private Button buttonCriarProjeto;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_projeto);

        // Inicializar Firebase
        storageReference = FirebaseStorage.getInstance().getReference("projetos");

        // Referenciar os elementos do layout
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextMetas = findViewById(R.id.editTextMetas);
        buttonCriarProjeto = findViewById(R.id.buttonCriarProjeto);

        // Definir o listener de clique para o botão
        buttonCriarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarProjeto();
            }
        });
    }

    private void criarProjeto() {
        // Obter os valores dos campos
        String titulo = editTextTitulo.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String metas = editTextMetas.getText().toString().trim();

        // Verificar se os campos estão preenchidos
        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(metas)) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar um ID único para o projeto (pode ser um UUID gerado aleatoriamente)
        String idProjeto = UUID.randomUUID().toString();

        // Obter o ID do criador do projeto (usuário atualmente autenticado)
        // Esta parte do código depende da implementação de autenticação com Firebase Authentication
        String criadorId = "currentUserID"; // replace with actual user ID

        // Criar objeto de projeto
        Projeto projeto = new Projeto(idProjeto, titulo, descricao, metas, criadorId);

        // Obter referência do Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criar um novo documento na coleção "projetos" com o conteúdo do projeto
        db.collection("projetos").document(idProjeto)
                .set(projeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Exibir mensagem de sucesso
                        Toast.makeText(CriarProjetoActivity.this, "Projeto criado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Retornar à tela anterior
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Exibir mensagem de erro
                        Toast.makeText(CriarProjetoActivity.this, "Erro ao criar projeto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

