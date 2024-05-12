package com.example.ecounity.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ecounity.R;
import com.example.ecounity.activity.Util.CadastrarEventoActivity;
import com.example.ecounity.activity.Util.ConfiguracaoBD;
import com.example.ecounity.activity.Util.CriarPerfilActivity;
import com.example.ecounity.activity.Util.CriarPerfilNegocioActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PainelActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel);

        auth = ConfiguracaoBD.Firebaseautenticacao();



        setupCards();
    }

    private void setupCards() {
        CardView consultCard = findViewById(R.id.cad_perfil_card);
        CardView myProjects = findViewById(R.id.my_projects_card);
        CardView cadNegocio = findViewById(R.id.my_bussines_card);
        CardView myEvents = findViewById(R.id.edit_event_card);



        setCardListener(consultCard, CriarPerfilActivity.class);
        setCardListener(myProjects,ProjetosActivity.class);
        setCardListener(cadNegocio, CriarPerfilNegocioActivity.class);
        setCardListener(myEvents, CadastrarEventoActivity.class);


    }

    private void setCardListener(CardView cardView, final Class<?> activityClass) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PainelActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }
}