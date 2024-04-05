package com.example.ecounity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecounity.R;
import com.example.ecounity.activity.Util.ConfiguracaoBD;
import com.example.ecounity.activity.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    Usuario usuario;
    FirebaseAuth autenticacao;
    EditText campoNome, campoEmail, campoSenha;
    Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializar();
    }

    private void inicializar() {
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });
    }

    private void validarCampos() {

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    // Bloco de código caso todos os campos estejam preenchidos

                    usuario = new Usuario();

                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    cadastrarUsuario();
                } else {
                    // Mensagem de erro caso a senha esteja vazia
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Mensagem de erro caso o email esteja vazio
                Toast.makeText(this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mensagem de erro caso o nome esteja vazio
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
    }


    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoBD.Firebaseautenticacao();

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
                }else {
                    String excecao ="";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        excecao ="Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e) {
                        excecao="Esta conta já existe";
                    }catch (Exception e){
                        excecao= "Erro ao cadastrar usuário"+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
