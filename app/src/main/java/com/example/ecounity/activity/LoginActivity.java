package com.example.ecounity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils; // for checking empty strings
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail = null, campoSenha = null;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarComponentes();
        auth = ConfiguracaoBD.Firebaseautenticacao();
    }

    public void validarAutenticacao(View view) {
        String email = campoEmail.getText().toString().trim(); // Remove leading/trailing whitespaces
        String senha = campoSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
            return; // Exit the method if email is empty
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        logar(usuario);
    }

    private void logar(Usuario usuario) {
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            abrirHome();
                        } else {
                            String excessao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                excessao = "Usuário não está cadastrado";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excessao = "E-mail ou senha incorretos";
                            } catch (Exception e) {
                                excessao = "Erro ao logar o usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, excessao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void abrirHome() {
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erro: Usuário não autenticado.", Toast.LENGTH_SHORT).show();
        }
    }


    public void cadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Remove this line: FirebaseUser usuarioAuth = auth.getCurrentUser();
        // Remove this line: if (usuarioAuth!= null) {
        // Remove this line: abrirHome();
    }

    private void inicializarComponentes() {
        campoEmail = findViewById(R.id.editTextEmailLogin);
        campoSenha = findViewById(R.id.editTextSenhaLogin);
        Button botaoAcessar = findViewById(R.id.buttonAcessar); // Assuming button has this id
    }
}
