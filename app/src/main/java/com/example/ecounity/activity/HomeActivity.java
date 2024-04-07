package com.example.ecounity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ecounity.R;
import com.example.ecounity.activity.Util.ConfiguracaoBD;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConfiguracaoBD.Firebaseautenticacao();

        // Inicializar variáveis e configurar listeners para os cards
        setupCards();
    }

    private void setupCards() {
        CardView consultCard = findViewById(R.id.consult_card);
        CardView projectsCard = findViewById(R.id.projects_card);
        CardView businessCard = findViewById(R.id.bussines_card);
        CardView eventCard = findViewById(R.id.event_card);
        CardView networkingCard = findViewById(R.id.networking_card);
        CardView adminCard = findViewById(R.id.admin_card);

        setCardListener(consultCard, R.layout.activity_consulta);
        setCardListener(projectsCard, R.layout.activity_projetos);
        setCardListener(businessCard, R.layout.activity_vitrine);
        setCardListener(eventCard, R.layout.activity_eventos);
        setCardListener(networkingCard, R.layout.activity_networking);
        setCardListener(adminCard, R.layout.activity_painel);
    }

    private void setCardListener(CardView cardView, final int layoutId) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(layoutId);
            }
        });
    }

    public void logout(View view) {
        try {
            auth.signOut();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao fazer logout. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }
}
