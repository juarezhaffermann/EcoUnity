package com.example.ecounity.activity;

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
        findViewById(R.id.logout).setOnClickListener(this::logout);

        // Inicializar variáveis e configurar listeners para os cards
        setupCards();
    }

    private void setupCards() {
        CardView consultCard = findViewById(R.id.consulta_card);
        CardView projectsCard = findViewById(R.id.projects_card);
        CardView businessCard = findViewById(R.id.bussines_card);
        CardView eventCard = findViewById(R.id.event_card);
        CardView networkingCard = findViewById(R.id.networking_card);
        CardView adminCard = findViewById(R.id.admin_card);

        setCardListener(consultCard, ConsultaActivity.class);
        setCardListener(projectsCard, ProjetosActivity.class);
        setCardListener(businessCard, VitrineActivity.class);
        setCardListener(eventCard, EventosActivity.class);
        setCardListener(networkingCard, NetworkingActivity.class);
        setCardListener(adminCard, PainelActivity.class);
    }

    private void setCardListener(CardView cardView, final Class<?> activityClass) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }

    public void logout(View view) {
        try {
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish(); // This line will ensure that the user cannot press the back button to return to the HomeActivity
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao fazer logout. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }
}
