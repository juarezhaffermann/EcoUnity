package com.example.ecounity.activity.Util;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecounity.R;
import com.example.ecounity.activity.model.Evento;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CadastrarEventoActivity extends AppCompatActivity {
    private EditText etNome, etData, etHorario, etLocal;
    private Button btnCadastrar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);

        etNome = findViewById(R.id.et_nome);
        etData = findViewById(R.id.et_data);
        etHorario = findViewById(R.id.et_horario);
        etLocal = findViewById(R.id.et_local);
        btnCadastrar = findViewById(R.id.btn_cadastrar);

        // Aplicando as máscaras
        etData.addTextChangedListener(new MaskedWatcher("##/##/####", etData));
        etHorario.addTextChangedListener(new MaskedWatcher("##:##", etHorario));

        db = FirebaseFirestore.getInstance();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                String data = etData.getText().toString();
                String horario = etHorario.getText().toString();
                String local = etLocal.getText().toString();

                if (isValidDate(data) && isValidTime(horario)) {
                    Evento evento = new Evento(nome, data, horario, local);
                    db.collection("eventos").add(evento)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(CadastrarEventoActivity.this, "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                    etNome.setText("");
                                    etData.setText("");
                                    etHorario.setText("");
                                    etLocal.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CadastrarEventoActivity.this, "Erro ao cadastrar evento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(CadastrarEventoActivity.this, "Data ou horário inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setLenient(false);
        try {
            timeFormat.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
